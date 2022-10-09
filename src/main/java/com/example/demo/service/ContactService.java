package com.example.demo.service;

import com.example.demo.constants.DemoConstants;
import com.example.demo.model.Contact;
import com.example.demo.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

@Service
//@RequestScope
//@SessionScope
//@ApplicationScope
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public boolean saveMessageDetails(Contact contact) {
        boolean isSaved = false;
        contact.setStatus(DemoConstants.OPEN);
        /*contact.setCreatedAt(LocalDateTime.now());
        contact.setCreatedBy(DemoConstants.ANONYMOUS); replace by jpa auditing  */
        Contact savedContact = contactRepository.save(contact);
        if (savedContact.getContactId() > 0) isSaved = true;
        return isSaved;
    }

    public List<Contact> findMsgsWithOpenStatus() {
        List<Contact> contactMsgs = contactRepository.findContactsByStatus(DemoConstants.OPEN);
        System.out.println(contactRepository.count(DemoConstants.OPEN));
        return contactMsgs;
    }

    public boolean updateMsgStatus(int contactId/*, String updatedBy*/) {
//        AtomicBoolean isUpdated = new AtomicBoolean(false);
//        Optional<Contact> contact = contactRepository.findById(contactId);
//        contact.ifPresent(contact1 -> {
//            contact1.setStatus(DemoConstants.CLOSE);
//            /*contact1.setUpdatedBy(updatedBy);
//            contact1.setUpdatedAt(LocalDateTime.now()); replace by jpa auditing*/
//            Contact updatedContact = contactRepository.save(contact1);
//            if (updatedContact.getUpdatedBy() != null) isUpdated.set(true);
//        });
//        return isUpdated.get();
        int rowAffected = contactRepository.updateStatusById(DemoConstants.CLOSE, contactId);
        return rowAffected > 0;
    }
}
