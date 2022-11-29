package ru.example.mindi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.mindi.model.Book;
import ru.example.mindi.model.Person;
import ru.example.mindi.repository.BookRepository;
import ru.example.mindi.repository.PersonRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Optional<Book> getBook(String name, String author) {
        return bookRepository.findBookByNameAndAuthor(name, author);
    }

    public Optional<Person> getBookOwner(int id) {
        Integer personId = getBook(id).getPersonId();
        if (Objects.isNull(personId)) return Optional.empty();
        else return personRepository.findById(getBook(id).getPersonId());
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updateBook) {
        updateBook.setId(id);
        bookRepository.save(updateBook);
    }

    @Transactional
    public void setPerson(int id, int personId) {
        Book book = getBook(id);
        book.setPersonId(personId);
        book.setTakenDate(new Date());
        bookRepository.save(book);
    }

    @Transactional
    public void removePerson(int id) {
        Book book = getBook(id);
        book.setPersonId(null);
        book.setTakenDate(null);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.delete(getBook(id));
    }
}
