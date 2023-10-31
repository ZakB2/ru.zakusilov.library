package ru.zakusilov.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.zakusilov.library.entity.Book;
import ru.zakusilov.library.entity.Person;

import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Book> findByTitle(String title) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE title = ?",
                        new Object[]{title},
                        new BeanPropertyRowMapper<>(Book.class)).
                stream().findAny();
    }

    public Optional<Book> findByAuthor(String author) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE author = ?",
                        new Object[]{author},
                        new BeanPropertyRowMapper<>(Book.class)).
                stream().findAny();
    }

    public Optional<Person> findPersonByBookId(int id) {
        return jdbcTemplate.query("SELECT * " +
                                "FROM Person " +
                                "JOIN Book " +
                                "ON Person.id = Book.person_id " +
                                "WHERE Book.id = ?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class)).
                stream().findAny();
    }

    public void selectBook(int id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE id = ?",
                selectedPerson.getId(),
                id);
    }

    public void returnBook(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id = NULL WHERE id = ?", id);
    }
}