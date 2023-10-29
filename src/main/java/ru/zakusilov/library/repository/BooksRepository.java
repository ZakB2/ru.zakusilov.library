package ru.zakusilov.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zakusilov.library.entity.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
}
