package com.example.demo.rest;

import com.example.demo.constants.DemoConstants;
import com.example.demo.model.Contact;
import com.example.demo.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@Controller
@RestController
@RequestMapping("/api/v1/contact")
public class ContactRestController {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactRestController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @GetMapping("/getMessageByStatus")
//    @ResponseBody
    public List<Contact> getMessageByStatus(@RequestParam String status) {
        return contactRepository.findContactsByStatus(status);
    }

    @GetMapping("/getAllMessage")
    public Iterable<Contact> getAllMessage() {
        return contactRepository.findAll();
    }

    @PostMapping("/saveMsg")
    public ResponseEntity<Contact> saveMsg(@RequestHeader("User-Agent") String userAgent,
                                    @Valid @RequestBody Contact contact) {
        System.out.println("User-Agent: " + userAgent);
        Contact savedContact = contactRepository.save(contact);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("server", "tomcat")
                .body(savedContact);
    }

    @DeleteMapping("/deleteMsg")
    public ResponseEntity<Contact> deleteMsg(RequestEntity<Contact> requestEntity) {
        HttpHeaders headers = requestEntity.getHeaders();
        headers.forEach((key, values) -> {
            System.out.println(key + ":" + values);
        });
        Contact contact = requestEntity.getBody();
        contactRepository.deleteById(contact.getContactId());
        return ResponseEntity.status(HttpStatus.OK).body(contact);
    }

    @PatchMapping("/closeMsg")
    public ResponseEntity<String> closeMsg(@RequestParam int id) {
        int rowMatch = contactRepository.updateStatusById(DemoConstants.CLOSE, id);
        System.out.println(rowMatch);
        if (rowMatch > 0) return ResponseEntity.status(HttpStatus.OK).body("Updated Successfully");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
    }
}
