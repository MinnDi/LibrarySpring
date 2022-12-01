package ru.example.mindi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.example.mindi.model.Book;
import ru.example.mindi.model.Person;
import ru.example.mindi.service.BookService;
import ru.example.mindi.service.PersonService;
import ru.example.mindi.util.BookValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final PersonService personService;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookService bookService, PersonService personService, BookValidator bookValidator) {
        this.bookService = bookService;
        this.personService = personService;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String getBooks(Model model, @RequestParam(value = "page", required = false) Integer page,
                           @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                           @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {
        if (page == null || booksPerPage == null || booksPerPage == 0) {
            model.addAttribute("books", bookService.getBooks(sortByYear));
        } else {
            model.addAttribute("books", bookService.getBooks(page, booksPerPage, sortByYear));
        }
        return "books/books";
    }

    @GetMapping("/{id}")
    public String getBook(Model model, @PathVariable("id") int id, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.getBook(id));
        if (bookService.getBookOwner(id) != null) {
            model.addAttribute("owner", bookService.getBookOwner(id));
        } else model.addAttribute("people", personService.getPeople());
        return "books/book";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) return "books/new";
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.getBook(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) return "books/edit";
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/add_person")
    public String addPerson(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        bookService.setPerson(id, person);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/delete_person")
    public String deletePerson(@PathVariable("id") int id) {
        bookService.removePerson(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search() {
        return "books/search";
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam("name") String name) {
        model.addAttribute("books", bookService.findByName(name));
        return "books/search";
    }


}
