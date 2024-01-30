package com.mycompany.library.repository;


import com.mycompany.library.Entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public Optional<Member> findByPhoneNumber(String phoneNumber);

    public List<Member> findByIdIn(List<Long> memberIds);
}
