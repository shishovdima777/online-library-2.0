package ru.shishov.onlinelibrary.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shishov.onlinelibrary.models.Person;
import ru.shishov.onlinelibrary.services.BooksService;
import ru.shishov.onlinelibrary.services.PeopleService;
import ru.shishov.onlinelibrary.util.PeopleValidator;

import javax.validation.Valid;


@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleValidator peopleValidator;
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleValidator peopleValidator, PeopleService peopleService) {
        this.peopleValidator = peopleValidator;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String getPeople(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/people";
    }

    @GetMapping("/new")
    public String addPerson(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        peopleValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "people/new";

        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id ,@ModelAttribute() @Valid Person person,
                         BindingResult bindingResult) {
        peopleValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "people/edit";
        peopleService.update(person, id);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("books", peopleService.findAll(id));
        return "people/person";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
}
