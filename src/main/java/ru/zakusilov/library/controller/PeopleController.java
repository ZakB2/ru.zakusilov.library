package ru.zakusilov.library.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.zakusilov.library.dao.PersonDAO;
import ru.zakusilov.library.entity.Person;
import ru.zakusilov.library.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("people", personDAO.getAll());
        return "people/getAllPeople";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.findById(id));
        model.addAttribute("books", personDAO.findBooksByPersonId(id));
        return "people/showPerson";
    }

    @GetMapping("/addNew")
    public String addNew(@ModelAttribute("person") Person person) {
        return "people/addNewPerson";
    }

    @PostMapping
    public String createNew(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/addNewPerson";
        }
        personDAO.createNew(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.findById(id));
        return "people/updatePerson";
    }

    @PatchMapping("/{id}")
    public String updateById(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                             @PathVariable("id") int id) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "people/updatePerson";
        }
        personDAO.updateById(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") int id) {
        personDAO.deleteById(id);
        return "redirect:/people";
    }
}








