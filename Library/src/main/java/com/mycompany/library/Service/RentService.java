/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.library.Service;

import com.mycompany.library.Entity.Book;
import com.mycompany.library.Entity.Rent;
import com.mycompany.library.repository.BookRepository;
import com.mycompany.library.repository.RentRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RentService {

    @Value("${library.rent-limit}")
    private int lendingLimit;

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private BookRepository bookRepository;

    public void rentBook(Long memberId, Long bookId) {
        // Check if the lending limit is reached
        int currentLendingCount = rentRepository.countByMemberId(memberId);
        if (currentLendingCount >= lendingLimit) {
            throw new RuntimeException("پیغام خطا: تعداد امانت‌های کتاب به حداکثر مجاز رسیده است.");
        }


        // Check if the book is available for rent
        BookAvailabilityCalculator bookservice = new BookAvailabilityCalculator();
       if (!bookservice.isAvailableForRent(bookId)) {
            throw new RuntimeException("پیغام خطا: کتاب موردنظر در حال حاضر قابل امانت‌دادن نمی‌باشد.");
        }
        // Rent the book to the member
        Rent rent = new Rent();
        rent.setMemberId(memberId);
        rent.setBookId(bookId);
        rentRepository.save(rent);
    }

    public void returnBook(Long memberId, Long bookId) {

        Optional<Rent> optionalRent = rentRepository.findByMemberIdAndBookId(memberId, bookId);
        if (!optionalRent.isPresent()) {
            throw new RuntimeException("پیغام خطا: امانت کتاب موردنظر یافت نشد یا این کتاب قبلاً برگشت داده شده است.");
        }

        Rent rent = optionalRent.get();
        rentRepository.delete(rent);

    }

}
