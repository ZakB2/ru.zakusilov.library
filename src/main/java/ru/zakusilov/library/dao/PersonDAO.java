package ru.zakusilov.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.zakusilov.library.entity.Book;

import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> findBooksByPersonId(int id) {
        return jdbcTemplate.query("SELECT * " +
                        "FROM Book " +
                        "WHERE person_id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }
}