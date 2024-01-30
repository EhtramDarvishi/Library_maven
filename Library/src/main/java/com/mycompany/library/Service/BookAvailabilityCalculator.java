/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.library.Service;

import com.mycompany.library.Entity.Book;
import com.mycompany.library.repository.BookRepository;
import com.mycompany.library.repository.RentRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookAvailabilityCalculator {

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private BookRepository bookRepository;

    public boolean isAvailableForRent(Long bookId) {
        // Get the book
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("پیغام خطا: کتاب با شناسه موردنظر یافت نشد."));

        // Get the number of books currently on loan
        int booksOnLoanCount = rentRepository.countByBookId(bookId);

        // Compare the number of books on loan with the available quantity
        return booksOnLoanCount < book.getCount();
    }

    public boolean isAvailableForRent(String bookName) {
        // Find the book by name
        Optional<Book> optionalBook = bookRepository.findByName(bookName);

        // Check if the book exists
        if (!optionalBook.isPresent()) {
            throw new RuntimeException("پیغام خطا: کتاب با نام موردنظر یافت نشد.");
        }

        Book book = optionalBook.get();

        // Get the number of books currently on loan
        int booksOnLoanCount = rentRepository.countByBookId(book.getId());

        // Compare the number of books on loan with the available quantity
        return booksOnLoanCount < book.getQuantity();
    }
}
