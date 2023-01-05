package ru.shishov.onlinelibrary.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shishov.onlinelibrary.dao.PersonDAO;
import ru.shishov.onlinelibrary.models.Person;
import ru.shishov.onlinelibrary.services.PeopleService;

@Component
public class PeopleValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PeopleValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(peopleService.findByName(person.getName()).isPresent()) {
            errors.rejectValue("name", "", "This name is already taken.");
        }
    }
}