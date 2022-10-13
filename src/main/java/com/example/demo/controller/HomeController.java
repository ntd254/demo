package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(path = {"home", ""})
    public String displayHomePage(Model model) {
//        model.addAttribute("username", "dat nguyen");
        return "home";
    }

//    @RequestMapping(path = "courses")
//    public String displayCoursesPage() {
//        return "courses.html";
//    }

}
