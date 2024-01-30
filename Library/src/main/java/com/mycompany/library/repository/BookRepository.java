package com.mycompany.library.repository;

import com.mycompany.library.Entity.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    public Optional<Book> findByName(String bookName);

    public Optional<Book> findByTitleAndAuthor(String bookName, String author);
    public List<Book> findByTitleAndAuthorList(String bookName, String author);

    public List<Book> findByTitle(String title);

    public List<Book> findByAuthor(String author);
}
