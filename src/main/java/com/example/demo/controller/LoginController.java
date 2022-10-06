package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @RequestMapping(path = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public String displayLoginPage(@RequestParam(required = false) String error,
                                   @RequestParam(required = false) String logout, Model model,
                                   @RequestParam(required = false) String register) {
        String errorMessage = null;
        if (error != null) errorMessage = "Username or Password is incorrect !!";
        if (logout != null) errorMessage = "You have been successfully logged out !!";
        if (register != null) errorMessage = "You have bean successfully registered";
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

    @GetMapping(path = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }
}
