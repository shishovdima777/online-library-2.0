package ru.shishov.onlinelibrary.util;

import ru.shishov.onlinelibrary.models.Book;
import ru.shishov.onlinelibrary.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BooksValidator implements Validator {
    private final BooksService booksService;
    @Autowired
    public BooksValidator(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if(booksService.findByBookName(book.getBookName()).isPresent()) {
            errors.rejectValue("bookName", "", "This book is already exists.");
        }
    }
}
