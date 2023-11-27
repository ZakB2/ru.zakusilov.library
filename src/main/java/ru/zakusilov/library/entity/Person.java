package ru.zakusilov.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Person {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    @NotEmpty(message = "Полное имя пользователя не может быть пустым")
    @Size(min = 1, max = 100, message = "Полное имя пользователя должно содержать от 1 до 100 символов")
    @Pattern (regexp = "^[А-ЯЁA-Z][а-яёa-z-]+ [А-ЯЁA-Z][а-яёa-z-]+( [А-ЯЁA-Z][а-яёa-z-]+)?$",
            message = "Полное имя пользователя должно быть в следующем формате: Фамилия Имя Отчество (при наличии)")
    private String fullName;

    @Column(name = "year_of_birth")
    @Min(value = 1900, message = "Год рождения пользователя должен быть больше или равен 1900 году")
    @Max(value = 2010, message = "Год рождения пользователя должен быть меньше или равен 2010 году")
    private int yearOfBirth;

    @OneToMany(mappedBy = "person")
    private List<Book> books;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "person_books_history", joinColumns = @JoinColumn(name = "person_id"))
    @Column(name = "book")
    private List<String> booksHistory = new ArrayList<>();

    public Person(String fullName, int yearOfBirth) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    @Override
    public String toString() {
        return fullName + ", " + yearOfBirth + " г.р.";
    }
}