package com.example.demo.model;

import com.example.demo.annotation.FieldsValueMatch;
import com.example.demo.annotation.PasswordValidator;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@FieldsValueMatch.List({
        @FieldsValueMatch(field = "pwd",fieldMatch = "confirmPwd", message = "Password don't match!"),
        @FieldsValueMatch(field = "email", fieldMatch = "confirmEmail", message = "Email addresses don't match")
})
public class Person extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personId;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @NotBlank(message = "Mobile number must not be blank")
    @Pattern(regexp = "[0-9]{10}", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotBlank(message = "Confirm Email must not be blank")
    @Email(message = "Please provide a valid email")
    @Transient
    private String confirmEmail;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    @PasswordValidator
    private String pwd;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    @Transient
    private String confirmPwd;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = Roles.class)
    @JoinColumn(name = "role_id", referencedColumnName = "roleId", nullable = false)
    private Roles roles;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,targetEntity = Address.class)
    @JoinColumn(name = "address_id", referencedColumnName = "addressId", nullable = true)
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_id", referencedColumnName = "classId")
    private EazyClass eazyClass;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Courses.class)
    @JoinTable(name = "person_courses",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "personId"),
            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "courseId"))
    private Set<Courses> courses = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return personId == person.personId;
    }

    @Override
    public int hashCode() {
        return personId;
    }
}
