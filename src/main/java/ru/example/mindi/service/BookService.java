package ru.example.mindi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.mindi.model.Book;
import ru.example.mindi.model.Person;
import ru.example.mindi.repository.BookRepository;
import ru.example.mindi.repository.PersonRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks(boolean sortByYear) {
        if (sortByYear) {
            return bookRepository.findAll(Sort.by("year"));
        } else {
            return bookRepository.findAll();
        }
    }

    public List<Book> getBooks(Integer page, Integer booksPerPage, boolean sortByYear) {
        if (sortByYear) {
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        } else {
            return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
        }
    }

    public List<Book> findByName(String name){
       return bookRepository.findBooksByNameStartingWith(name);
    }

    public Book getBook(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Optional<Book> getBook(String name, String author) {
        return bookRepository.findBookByNameAndAuthor(name, author);
    }

    public Person getBookOwner(int id) {
        return getBook(id).getOwner();
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
    public void setPerson(int id, Person person) {
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(person);
                    book.setTakenDate(new Date());
                }
        );
    }

    @Transactional
    public void removePerson(int id) {
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setTakenDate(null);
                });
    }

    @Transactional
    public void delete(int id) {
        bookRepository.delete(getBook(id));
    }
}
