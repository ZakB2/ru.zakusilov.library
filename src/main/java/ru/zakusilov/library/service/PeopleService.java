package ru.zakusilov.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zakusilov.library.entity.Person;
import ru.zakusilov.library.repository.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findById(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    public Optional<Person> findByFullName(String fullName) {
        return peopleRepository.findByFullName(fullName);
    }

    public Optional<Person> findByYearOfBirth(int yearOfBirth) {
        return peopleRepository.findByYearOfBirth(yearOfBirth);
    }

    @Transactional
    public void save(Person newPerson) {
        peopleRepository.save(newPerson);
    }

    @Transactional
    public void updateById(int id, Person updatePerson) {
        updatePerson.setId(id);
        peopleRepository.save(updatePerson);
    }

    @Transactional
    public void deleteById(int id) {
        peopleRepository.deleteById(id);
    }
}