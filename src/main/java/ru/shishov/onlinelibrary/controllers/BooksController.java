package ru.shishov.onlinelibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shishov.onlinelibrary.models.Book;
import ru.shishov.onlinelibrary.models.Person;
import ru.shishov.onlinelibrary.services.BooksService;
import ru.shishov.onlinelibrary.services.PeopleService;
import ru.shishov.onlinelibrary.util.BooksValidator;

import javax.validation.Valid;


@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksValidator booksValidator;
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksValidator booksValidator, BooksService booksService, PeopleService peopleService) {
        this.booksValidator = booksValidator;
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String getBooks(Model model) {
        model.addAttribute("books", booksService.findAll());
        return "books/books";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book,
                          BindingResult bindingResult) {
        booksValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors())
            return "books/new";

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String editBook(@PathVariable("id") int id,
                           @ModelAttribute("book") Book book,
                           BindingResult bindingResult){
        booksValidator.validate(book, bindingResult);
        booksService.update(id, book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model,
                           @ModelAttribute("person")Person person) {
        model.addAttribute("book", booksService.findOne(id));

        Person bookOwner = booksService.getOwner(id);

        if(bookOwner != null) {
            model.addAttribute("owner", bookOwner.getName());
        } else {
            model.addAttribute("people", peopleService.findAll());
        }
        return "books/book";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int id,
                             @ModelAttribute("person") Person person) {
        booksService.update(id, person);                                             // Ёу тут баг
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        booksService.update(id);
        return "redirect:/books/{id}";
    }
}