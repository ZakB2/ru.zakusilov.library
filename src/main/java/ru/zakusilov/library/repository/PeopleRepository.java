package ru.zakusilov.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zakusilov.library.entity.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
