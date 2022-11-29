package ru.example.mindi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.mindi.model.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByName(String name);
}
