package com.example.demo.controller;

import com.example.demo.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentController {

    @GetMapping("displayCourses")
    public ModelAndView displayCourses(HttpSession httpSession) {
        Person person = (Person) httpSession.getAttribute("loggedInPerson");
        ModelAndView modelAndView = new ModelAndView("courses_enrolled");
        modelAndView.addObject("person", person);
        return modelAndView;
    }
}
