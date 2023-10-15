package ru.zakusilov.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.zakusilov.library.entity.Book;
import ru.zakusilov.library.entity.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAll() {
        return jdbcTemplate.query("SELECT * FROM Person ORDER BY full_name",
                new BeanPropertyRowMapper<>(Person.class));
    }

    public Person findById(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id = ?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class)).
                stream().findAny().orElse(null);
    }

    public Optional<Person> findByFullName(String fullName) {
        return jdbcTemplate.query("SELECT * FROM person WHERE full_name = ?",
                        new Object[]{fullName},
                        new BeanPropertyRowMapper<>(Person.class)).
                stream().findAny();
    }

    public Optional<Person> findByYearOfBirth(int yearOfBirth) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE year_of_birth = ?",
                        new Object[]{yearOfBirth},
                        new BeanPropertyRowMapper<>(Person.class)).
                stream().findAny();
    }

    public List<Book> findBooksByPersonId(int id) {
        return jdbcTemplate.query("SELECT * " +
                        "FROM Book " +
                        "WHERE person_id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }

    public void createNew(Person person) {
        jdbcTemplate.update("INSERT INTO Person (full_name, year_of_birth) VALUES (?, ?)",
                person.getFullName(),
                person.getYearOfBirth());
    }

    public void updateById(int id, Person updatePerson) {
        jdbcTemplate.update("UPDATE Person SET full_name = ?, year_of_birth = ? WHERE id = ?",
                updatePerson.getFullName(),
                updatePerson.getYearOfBirth(),
                id);
    }

    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id = ?", id);
    }
}