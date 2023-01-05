package ru.shishov.onlinelibrary.repositories;

import ru.shishov.onlinelibrary.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByBookName(String book);
    List<Book> findBookByBookNameContainingIgnoreCase(String name);
}
