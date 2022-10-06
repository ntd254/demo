package com.example.demo.service;

import com.example.demo.constants.DemoConstants;
import com.example.demo.model.Person;
import com.example.demo.model.Roles;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final RolesRepository  rolesRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean createNewPerson(Person person) {
        boolean isSaved = false;
        Roles role = rolesRepository.findRoleByRoleName(DemoConstants.STUDENT_ROLE);
        person.setRoles(role);
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        person = personRepository.save(person);
        if (person.getPersonId() > 0) isSaved = true;
        return isSaved;
    }
}
