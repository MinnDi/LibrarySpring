package ru.example.mindi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.example.mindi.dao.PersonDao;
import ru.example.mindi.model.Person;

@Component
public class PersonValidator implements Validator {
    private static PersonDao personDao;

    @Autowired
    public PersonValidator(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (personDao.getPerson(person.getName()).isPresent() &&
                personDao.getPerson(person.getName()).get().getId()!=person.getId()){
            errors.rejectValue("name", "", "This name already exists");
        }
    }
}
