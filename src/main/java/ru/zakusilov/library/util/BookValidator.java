package ru.zakusilov.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.zakusilov.library.entity.Book;
import ru.zakusilov.library.service.BooksService;

@Component
public class BookValidator implements Validator {

    private final BooksService booksService;

    @Autowired
    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if (booksService.findByTitle(book.getTitle()).isPresent() &&
                booksService.findByAuthor(book.getAuthor()).isPresent()) {
            errors.rejectValue("title", "", "Книга с таким названием уже существует");
            errors.rejectValue("author", "", "Книга с таким автором уже существует");
        }
    }
}