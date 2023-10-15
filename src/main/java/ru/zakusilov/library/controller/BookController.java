package ru.zakusilov.library.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.zakusilov.library.dao.BookDAO;
import ru.zakusilov.library.dao.PersonDAO;
import ru.zakusilov.library.entity.Book;
import ru.zakusilov.library.entity.Person;
import ru.zakusilov.library.util.BookValidator;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("books", bookDAO.getAll());
        return "books/getAllBooks";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") int id, @ModelAttribute("person") Person person, Model model) {
        model.addAttribute("book", bookDAO.findById(id));

        Optional<Person> bookOwner = bookDAO.findPersonByBookId(id);
        if (bookOwner.isPresent()) {
            model.addAttribute("bookOwner", bookOwner.get());
        } else {
            model.addAttribute("people", personDAO.getAll());
        }
        return "books/showBook";
    }

    @GetMapping("/addNew")
    public String addNew(@ModelAttribute("book") Book book) {
        return "books/addNewBook";
    }

    @PostMapping
    public String createNew(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/addNewBook";
        }
        bookDAO.createNew(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.findById(id));
        return "books/updateBook";
    }

    @PatchMapping("/{id}")
    public String updateById(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                             @PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/updateBook";
        }
        bookDAO.updateById(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/selectBook")
    public String selectBook(@ModelAttribute("person") Person selectedPerson, @PathVariable("id") int id) {
        bookDAO.selectBook(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/returnBook")
    public String returnBook(Model model, @PathVariable("id") int id) {
        bookDAO.returnBook(id);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteByID(@PathVariable("id") int id) {
        bookDAO.deleteByID(id);
        return "redirect:/books";
    }
}