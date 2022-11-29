package ru.example.mindi.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.example.mindi.model.Book;
import ru.example.mindi.model.Person;

import java.util.List;
import java.util.Optional;

//@Component
public class PersonDao {
//    private JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public PersonDao(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<Person> getPeople() {
//        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
//    }
//
//    public Person getPerson(int id) {
//        return jdbcTemplate.query("select * from person where id = ?", new PersonMapper(), id)
//                .stream().findAny().orElse(null);
//    }
//
//    public Optional<Person> getPerson(String name) {
//        return jdbcTemplate.query("select * from person where name = ?", new PersonMapper(), name)
//                .stream().findAny();
//    }
//
//    public void save(Person person) {
//        jdbcTemplate.update("insert into person(name, year) values (?, ?)",
//                person.getName(), person.getYear());
//    }
//
//    public void update(int id, Person updatePerson) {
//        jdbcTemplate.update("update person set name = ?, year = ? where id = ?",
//                updatePerson.getName(), updatePerson.getYear(), id);
//    }
//
//    public void delete(int id) {
//        jdbcTemplate.update("delete from person where id = ?", id);
//    }
//
//    public List<Book> getBooks(int id) {
//        return jdbcTemplate.query("select * from book where person_id = ?", new BookMapper(), id);
//    }
}
