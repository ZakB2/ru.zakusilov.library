package ru.zakusilov.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.zakusilov.library.entity.Person;
import ru.zakusilov.library.service.PeopleService;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = ((Person) target);
        if (peopleService.findByFullName(person.getFullName()).isPresent() &&
                peopleService.findByYearOfBirth(person.getYearOfBirth()).isPresent()) {
            errors.rejectValue("fullName", "", "Пользователь с таким именем уже существует");
            errors.rejectValue("yearOfBirth", "", "Пользователь с таким годом рождения уже существует");
        }
    }
}