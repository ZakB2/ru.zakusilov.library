package ru.zakusilov.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotEmpty(message = "Название книги не может быть пустым")
    @Pattern(regexp = "^[А-ЯA-Z][а-яa-zА-ЯA-Z\\s\\S]*$",
            message = "Название книги должно начинаться с заглавной буквы")
    private String title;

    @Column
    @NotEmpty(message = "Ф.И.О автора не может быть пустым")
    @Size(min = 1, max = 100, message = "Ф.И.О автора должно содержать от 1 до 100 символов")
    @Pattern(regexp = "^[А-ЯЁA-Z][а-яёa-z-]+ [А-ЯЁA-Z][а-яёa-z-]+( [А-ЯЁA-Z][а-яёa-z-]+)?$",
            message = "Полное имя автора должно быть в следующем формате: Фамилия Имя Отчество (при наличии)")
    private String author;

    @Column(name = "year_of_publication")
    @Min(value = 1500, message = "Год публикации должен быть больше или равен 1500 году")
    private int yearOfPublication;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "book_people_history", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "person")
    private List<String> peopleHistory = new ArrayList<>();

    public Book(String title, String author, int yearOfPublication) {
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
    }

    @Override
    public String toString() {
        return  title + ", " + author + ", " + yearOfPublication + " г.";
    }
}