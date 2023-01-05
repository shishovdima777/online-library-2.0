package ru.shishov.onlinelibrary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shishov.onlinelibrary.models.Book;
import ru.shishov.onlinelibrary.models.Person;
import ru.shishov.onlinelibrary.repositories.BooksRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }
    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    public Book findOne(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    public Optional<Book> findByBookName(String book_name) {
        return booksRepository.findByBookName(book_name);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setBook_id(id);
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Person bookOwner) {
        findOne(id).setOwner(bookOwner);
    }

    @Transactional
    public void update(int id) {
        findOne(id).setOwner(null);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public Person getOwner(int id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.map(Book::getOwner).orElse(null);
    }
}
