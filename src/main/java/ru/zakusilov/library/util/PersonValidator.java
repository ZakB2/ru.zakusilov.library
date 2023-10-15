package ru.zakusilov.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.zakusilov.library.dao.PersonDAO;
import ru.zakusilov.library.entity.Person;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = ((Person) target);

        personDAO.findByFullName(person.getFullName());

        if (personDAO.findByFullName(person.getFullName()).isPresent()) {
            if (personDAO.findByYearOfBirth(person.getYearOfBirth()).isPresent()) {
                errors.rejectValue("fullName", "", "Пользователь с таким именем уже существует");
                errors.rejectValue("yearOfBirth", "", "Пользователь с таким годом рождения уже существует");
            }
        }
    }
}