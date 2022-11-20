package ru.example.mindi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.example.mindi.dao.BookDao;
import ru.example.mindi.model.Book;

@Component
public class BookValidator implements Validator {
    private static BookDao bookDao;

    @Autowired
    public BookValidator(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if (bookDao.getBook(book.getName(), book.getAuthor()).isPresent() &&
                bookDao.getBook(book.getName(), book.getAuthor()).get().getId()!=book.getId()){
            errors.rejectValue("name", "", "This book already exists");
        }
    }
}
