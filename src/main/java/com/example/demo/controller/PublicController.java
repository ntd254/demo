package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "public")
public class PublicController {

    private final PersonService personService;

    @Autowired
    public PublicController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping(path = "/register")
    public String displayRegisterPage(Model model) {
        model.addAttribute("person", new Person());
        return "register";
    }

    @PostMapping(path = "/createUser")
    public String createUser(@Valid @ModelAttribute Person person, Errors errors) {
        if (errors.hasErrors()) return "register";
        boolean isSaved = personService.createNewPerson(person);
        if (isSaved) return "redirect:/login?register=true";
        else return "register";
    }
}
