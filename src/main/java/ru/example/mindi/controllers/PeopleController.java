package ru.example.mindi.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.example.mindi.model.Person;
import ru.example.mindi.service.PersonService;
import ru.example.mindi.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonService personService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonService personService, PersonValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String getPeople(Model model){
        model.addAttribute("people", personService.getPeople());
        return "people/people";
    }

    @GetMapping("/{id}")
    public String getPerson(Model model, @PathVariable("id") int id){
        model.addAttribute("person", personService.getPerson(id));
        model.addAttribute("books", personService.getBooks(id));
        return "people/person";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors()) return "people/new";
        personService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personService.getPerson(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) {
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors()) return  "people/edit";
        personService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        System.out.println("КОнтроллер удаление до выхова сервиса");
        personService.delete(id);
        System.out.println("КОнтроллер удаление после выхова сервиса");
        return "redirect:/people";
    }
}