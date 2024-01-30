package com.mycompany.library.controller;

import com.mycompany.library.Entity.Book;
import com.mycompany.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

//    @PostMapping
//    public Book addBook(@RequestBody Book book) {
//        return bookRepository.save(book);
//    }
    @PostMapping("/books")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        // Validate request parameters
        if (book.getBookName() == null || book.getAuthor() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("پیغام خطا: عنوان و نویسنده کتاب الزامی هستند.");
        }

        // Check if the book already exists
        Optional<Book> existingBook = bookRepository.findByTitleAndAuthor(book.getBookName(), book.getAuthor());
        if (existingBook.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("پیغام خطا: کتاب با عنوان و نویسنده موردنظر از قبل وجود دارد.");
        }

        // Create a new Book object
        Book newBook = new Book();
        newBook.setBookName(book.getBookName());
        newBook.setAuthor(book.getAuthor());
        newBook.setCount(book.getCount());
        // Save the book to the database
        bookRepository.save(book);

        // Return a success response
        return ResponseEntity.status(HttpStatus.CREATED).body("پیغام: کتاب با موفقیت اضافه شد.");
    }

    @GetMapping("/searchBooks")
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author
    ) {
        // Search for books based on title and/or author
        List<Book> books;
        if (title != null && author != null) {
            books = bookRepository.findByTitleAndAuthorList(title, author);
        } else if (title != null) {
            books = bookRepository.findByTitle(title);
        } else if (author != null) {
            books = bookRepository.findByAuthor(author);
        } else {
            // If neither title nor author is provided, return all books
            books = bookRepository.findAll();
        }

        if (books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookRepository.findById(id).orElse(null);
    }
}
