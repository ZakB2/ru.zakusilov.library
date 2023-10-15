package ru.zakusilov.library.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Book {

    private int id;

    @NotEmpty(message = "Название книги не может быть пустым")
    @Pattern(regexp = "^[А-ЯA-Z][а-яa-zА-ЯA-Z\\s\\S]*$",
            message = "Название книги должно начинаться с заглавной буквы")
    private String title;

    @NotEmpty(message = "Ф.И.О автора не может быть пустым")
    @Size(min = 1, max = 100, message = "Ф.И.О автора должно содержать от 1 до 100 символов")
    @Pattern(regexp = "^[А-ЯЁA-Z][а-яёa-z-]+ [А-ЯЁA-Z][а-яёa-z-]+( [А-ЯЁA-Z][а-яёa-z-]+)?$",
            message = "Полное имя автора должно быть в следующем формате: Фамилия Имя Отчество (при наличии)")
    private String author;

    @Min(value = 1500, message = "Год публикации должен быть больше или равен 1500 году")
    private int yearOfPublication;
}