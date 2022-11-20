package ru.example.mindi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.example.mindi.dao.BookDao;
import ru.example.mindi.dao.PersonDao;
import ru.example.mindi.model.Book;
import ru.example.mindi.model.Person;
import ru.example.mindi.util.BookValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDao bookDao;
    private final PersonDao personDao;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookDao bookDao, PersonDao personDao, BookValidator bookValidator) {
        this.bookDao = bookDao;
        this.personDao = personDao;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String getBooks(Model model){
        model.addAttribute("books", bookDao.getBooks());
        return "books/books";
    }

    @GetMapping("/{id}")
    public String getBook(Model model, @PathVariable("id") int id, @ModelAttribute("person")Person person){
        model.addAttribute("book", bookDao.getBook(id));
        if (bookDao.getBookOwner(id).isPresent()){
            model.addAttribute("owner", bookDao.getBookOwner(id).get());
        } else model.addAttribute("people", personDao.getPeople());
        return "books/book";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book,bindingResult);
        if (bindingResult.hasErrors()) return "books/new";
        bookDao.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDao.getBook(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        bookValidator.validate(book,bindingResult);
        if (bindingResult.hasErrors()) return  "books/edit";
        bookDao.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDao.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/add_person")
    public String addPerson(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        bookDao.setPerson(id, person.getId());
        return "redirect:/books";
    }

    @PatchMapping("/{id}/delete_person")
    public String deletePerson(@PathVariable("id") int id){
        bookDao.removePerson(id);
        return "redirect:/books";
    }
}
