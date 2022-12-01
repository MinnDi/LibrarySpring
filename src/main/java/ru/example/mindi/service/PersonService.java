package ru.example.mindi.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.mindi.model.Book;
import ru.example.mindi.model.Person;
import ru.example.mindi.repository.BookRepository;
import ru.example.mindi.repository.PersonRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final BookRepository bookRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, BookRepository bookRepository) {
        this.personRepository = personRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public List<Person> getPeople() {
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Person getPerson(int id) {
        return personRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Optional<Person> getPerson(String name) {
        return personRepository.findByName(name);
    }

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatePerson) {
        updatePerson.setId(id);
        personRepository.save(updatePerson);
    }

    @Transactional
    public void delete(int id) {
        System.out.println("Сервис удаление");
        personRepository.deleteById(id);
        System.out.println("Сервис удаление");
    }

    @Transactional(readOnly = true)
    public List<Book> getBooks(int id) {
        Optional<Person> person = personRepository.findById(id);

        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            person.get().getBooks().forEach(book -> {
                long diffInMillis = Math.abs(book.getTakenDate().getTime() - new Date().getTime());
                if (diffInMillis > 864000000)
                    book.setExpired(true);
            });

            return person.get().getBooks();
        }
        else {
            return Collections.emptyList();
        }
    }
}
