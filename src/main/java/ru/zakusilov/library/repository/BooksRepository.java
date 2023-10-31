package ru.zakusilov.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zakusilov.library.entity.Book;

import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findByTitle(String title);

    Optional<Book> findByAuthor(String author);
}