package ru.shishov.onlinelibrary.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @NotEmpty(message = "Name shouldn't be empty")
    @Pattern(regexp = "[A-z\\s]*", message = "Name should contain only Latin letters")
    @Column(name = "name")
    private String name;
    @NotNull(message = "Birth year shouldn't be empty")
    @Min(value = 1, message = "Birth year should be greater than 0")
    @Column(name = "birth_year")
    private int birthYear;
    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer person_id;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Book> books;

    public Person() {

    }

    public Person(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", person_id=" + person_id +
                '}';
    }
}
