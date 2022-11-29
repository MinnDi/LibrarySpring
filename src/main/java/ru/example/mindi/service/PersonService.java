package ru.example.mindi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.mindi.model.Book;
import ru.example.mindi.model.Person;
import ru.example.mindi.repository.BookRepository;
import ru.example.mindi.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;
    private final BookRepository bookRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, BookRepository bookRepository) {
        this.personRepository = personRepository;
        this.bookRepository = bookRepository;
    }

    public List<Person> getPeople() {
        return personRepository.findAll();
    }

    public Person getPerson(int id) {
        return personRepository.findById(id).orElse(null);
    }

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

    public List<Book> getBooks(int id) {
        return bookRepository.findBooksByPersonId(id);
    }
}
