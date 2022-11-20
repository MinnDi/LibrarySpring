package ru.example.mindi.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.example.mindi.model.Book;
import ru.example.mindi.model.Person;


import java.util.List;
import java.util.Optional;

@Component
public class BookDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBooks() {
        return jdbcTemplate.query("select * from book", new BookMapper());
    }

    public Book getBook(int id) {
        return jdbcTemplate.query("select * from book where id = ?", new BookMapper(), id)
                .stream().findAny().orElse(null);
    }

    public Optional<Book> getBook(String name, String author) {
        return jdbcTemplate.query("select * from book where name = ? and author = ?", new BookMapper(), name, author)
                .stream().findAny();
    }

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.id " +
                "WHERE Book.id = ?", new PersonMapper(), id)
                .stream().findAny();
    }

    public void save(Book book) {
        jdbcTemplate.update("insert into book(name, author, year) values (?, ?, ?)",
                book.getName(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book updateBook) {
        jdbcTemplate.update("update book set name = ?, author = ?, year = ? where id = ?",
                updateBook.getName(), updateBook.getAuthor(), updateBook.getYear(), id);
    }

    public void setPerson(int id, int person_id){
        jdbcTemplate.update("update book set person_id = ? where id = ?",
                person_id, id);
    }

    public void removePerson(int id){
        jdbcTemplate.update("update book set person_id = null where id = ?", id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from book where id = ?", id);
    }
}

