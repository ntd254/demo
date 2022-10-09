package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class DashboardController {

    private final PersonRepository personRepository;

    @Autowired
    public DashboardController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping(path = "/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session) {
        Person person = personRepository.findPersonByEmail(authentication.getName());
        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        if (person.getEazyClass() != null) model.addAttribute("enrolledClass", person.getEazyClass());
        session.setAttribute("loggedInPerson", person);
        return "dashboard";
    }
}
