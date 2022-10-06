package com.example.demo.controller;

import com.example.demo.model.Address;
import com.example.demo.model.Person;
import com.example.demo.model.Profile;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class ProfileController {

    private final PersonRepository personRepository;

    @Autowired
    public ProfileController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping(path = "/displayProfile")
    public ModelAndView displayProfile(HttpSession session) {
        Person person = (Person) session.getAttribute("loggedInPerson");
        Profile profile = new Profile();
        profile.setName(person.getName());
        profile.setMobileNumber(person.getMobileNumber());
        profile.setEmail(person.getEmail());
        if (person.getAddress() != null) {
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setAddress2(person.getAddress().getAddress2());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZipCode(person.getAddress().getZipCode());
        }
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("profile", profile);
        return modelAndView;
    }

    @PostMapping(value = "/updateProfile")
    public String updateProfile(@Valid @ModelAttribute("profile") Profile profile, Errors errors,
                                HttpSession session)
    {
        if(errors.hasErrors()){
            return "profile";
        }
        Person person = (Person) session.getAttribute("loggedInPerson");
        person.setName(profile.getName());
        person.setEmail(profile.getEmail());
        person.setMobileNumber(profile.getMobileNumber());
        if(person.getAddress() == null){
            person.setAddress(new Address());
        }
        person.getAddress().setAddress1(profile.getAddress1());
        person.getAddress().setAddress2(profile.getAddress2());
        person.getAddress().setCity(profile.getCity());
        person.getAddress().setState(profile.getState());
        person.getAddress().setZipCode(profile.getZipCode());
        Person savedPerson = personRepository.save(person);
        session.setAttribute("loggedInPerson", savedPerson);
        return "redirect:/displayProfile";
    }
}
