package ru.zakusilov.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.zakusilov.library.dao.BookDAO;
import ru.zakusilov.library.entity.Book;

@Component
public class BookValidator implements Validator {

    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (bookDAO.findByTitle(book.getTitle()).isPresent()) {
            if (bookDAO.findByAuthor(book.getAuthor()).isPresent()) {
                errors.rejectValue("title", "", "Книга с таким названием уже существует");
                errors.rejectValue("author", "", "Книга с таким автором уже существует");
            }
        }
    }
}
