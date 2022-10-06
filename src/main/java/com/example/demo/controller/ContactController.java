package com.example.demo.controller;

import com.example.demo.model.Contact;
import com.example.demo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(path = "/contact")
    public String displayContactPage(Model model) {
        model.addAttribute("contact", new Contact());
        return "contact";
    }

//    @RequestMapping(path = "/saveMsg", method = RequestMethod.POST)
//    public ModelAndView saveMessage(@RequestParam String name, @RequestParam String mobileNum,
//                                    @RequestParam String email, @RequestParam String subject, @RequestParam String message) {
//        System.out.println(name);
//        System.out.println(mobileNum);
//        System.out.println(email);
//        System.out.println(subject);
//        System.out.println(message);
//        return new ModelAndView("redirect:contact");
//    }

    @RequestMapping(path = "/saveMsg", method = RequestMethod.POST)
    public String saveMessage(@Valid @ModelAttribute(name = "contact") Contact contact, Errors errors) {
        if (errors.hasErrors()) {
            System.out.println("Contact form fail due to : " + errors);
            return "contact";
        }
        contactService.saveMessageDetails(contact);
        return "redirect:/contact";
    }

    @RequestMapping(path = "/displayMessages")
    public ModelAndView displayMessages(Model model) {
        List<Contact> contactMsgs = contactService.findMsgsWithOpenStatus();
        ModelAndView modelAndView = new ModelAndView("messages");
        modelAndView.addObject("contactMsgs", contactMsgs);
        return modelAndView;
    }

    @GetMapping(path = "/closeMsg")
    public String closeMsg(@RequestParam int id /*, Authentication authentication*/) {
        contactService.updateMsgStatus(id /*, authentication.getName()*/);
        return "redirect:/displayMessages";
    }
}
