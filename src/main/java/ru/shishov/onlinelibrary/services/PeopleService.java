package ru.shishov.onlinelibrary.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shishov.onlinelibrary.models.Book;
import ru.shishov.onlinelibrary.models.Person;
import ru.shishov.onlinelibrary.repositories.PeopleRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public List<Book> findAll(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        if(person.isPresent())
            return person.get().getBooks();
        else
            return Collections.emptyList();
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public Optional<Person> findByName(String name) {
        Optional<Person> fondPerson = peopleRepository.findByName(name);
        return fondPerson.stream().findAny();
    }

    @Transactional
    public void update(Person person, int id) {
        person.setPerson_id(id);
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

}
