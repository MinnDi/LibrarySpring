package ru.example.mindi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.mindi.model.Book;
import ru.example.mindi.model.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findBookByNameAndAuthor(String name, String author);

    List<Book> findBooksByNameStartingWith(String name);
}
