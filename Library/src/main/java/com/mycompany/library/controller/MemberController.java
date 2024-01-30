package com.mycompany.library.controller;

import com.mycompany.library.Entity.Member;
import com.mycompany.library.Entity.Rent;
import com.mycompany.library.repository.MemberRepository;
import com.mycompany.library.repository.RentRepository;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;
    
    private RentRepository rentRepository;

    @GetMapping
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

//    @PostMapping
//    public Member addMember(@RequestBody Member member) {
//        return memberRepository.save(member);
//    }
//
//    
    @PostMapping("/members")
    public ResponseEntity<String> addMember(@RequestBody Member memberRequest) {
        // Validate request parameters
        if (memberRequest.getUsername() == null || memberRequest.getPhoneNumber() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("پیغام خطا: نام و ایمیل عضو الزامی هستند.");
        }

        // Check if the member already exists
        Optional<Member> existingMember = memberRepository.findByPhoneNumber(memberRequest.getPhoneNumber());
        if (existingMember.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("پیغام خطا: عضو با ایمیل موردنظر از قبل وجود دارد.");
        }

        // Create a new Member object
        Member member = new Member();
        member.setUsername(memberRequest.getUsername());
        member.setPhoneNumber(memberRequest.getPhoneNumber());

        // Save the member to the database
        memberRepository.save(member);

        // Return a success response
        return ResponseEntity.status(HttpStatus.CREATED).body("پیغام: عضو با موفقیت اضافه شد.");
    }

    @GetMapping("/members/overdue")
    public ResponseEntity<List<Member>> getOverdueMembers() {
        // Calculate the date two weeks ago
        LocalDate twoWeeksAgo = LocalDate.now().minusWeeks(2);

        // Find rents more than two weeks ago
        List<Rent> overdueRents = rentRepository.findOverdueRents(twoWeeksAgo);

        if (overdueRents.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Extract member IDs from overdue rents
        List<Long> memberIds = overdueRents.stream()
                .map(Rent::getMemberId)
                .distinct()
                .collect(Collectors.toList());

        // Find members by their IDs
        List<Member> overdueMembers = memberRepository.findByIdIn(memberIds);

        if (overdueMembers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(overdueMembers);
    }


@GetMapping("/{id}")
public Member getMemberById(@PathVariable Long id) {
        return memberRepository.findById(id).orElse(null);
    }
}
