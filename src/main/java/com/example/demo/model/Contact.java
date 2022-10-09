package com.example.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "contact_msg")
public class Contact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private int contactId;

    @NotBlank(message = "Name must not be blank")
//    @Size(min = 3,message = "Name must be at least 3 characters long")
    private String name;

    @NotBlank(message = "Mobile number must not be blank")
    @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be 10 digits")
    private String mobileNum;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Subject must not be blank")
//    @Size(min = 5, message = "Subject must be at least 5 characters long")
    private String subject;

    @NotBlank(message = "Message must not be blank")
//    @Size(min = 10, message = "Message must be at least 10 characters long")
    private String message;

    private String status;
}
