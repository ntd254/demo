package com.example.demo.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

//@Data
//@EqualsAndHashCode(callSuper = false)
@Setter
@Getter
@Entity
@Table(name = "class")
public class EazyClass extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int classId;

    @NotBlank(message = "Name must not be blank")
    private String name;

    @OneToMany(mappedBy = "eazyClass", fetch = FetchType.LAZY,
            targetEntity = Person.class)
    private Set<Person> personSet;
}
