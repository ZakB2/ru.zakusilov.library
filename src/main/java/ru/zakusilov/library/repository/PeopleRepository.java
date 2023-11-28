package ru.zakusilov.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.zakusilov.library.entity.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    @Query("SELECT p FROM Person p ORDER BY p.id")
    List<Person> findAllOrderById();

    Optional<Person> findByFullName(String fullName);

    Optional<Person> findByYearOfBirth(int yearOfBirth);
}