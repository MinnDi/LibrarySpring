package ru.example.mindi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.example.mindi.model.Book;
import ru.example.mindi.service.BookService;

@Component
public class BookValidator implements Validator {
    private final BookService bookService;

    @Autowired
    public BookValidator(BookService bookDao) {
        this.bookService = bookDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if (bookService.getBook(book.getName(), book.getAuthor()).isPresent() &&
                bookService.getBook(book.getName(), book.getAuthor()).get().getId()!=book.getId()){
            errors.rejectValue("name", "", "This book already exists");
        }
    }
}
