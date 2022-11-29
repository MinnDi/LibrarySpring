package ru.example.mindi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.example.mindi.model.Person;
import ru.example.mindi.service.PersonService;

@Component
public class PersonValidator implements Validator {
    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (personService.getPerson(person.getName()).isPresent() &&
                personService.getPerson(person.getName()).get().getId()!=person.getId()){
            errors.rejectValue("name", "", "This name already exists");
        }
    }
}
