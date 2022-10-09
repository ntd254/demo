package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
public class Courses extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseId;

    private String name;

    private String fees;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER)
    private Set<Person> personSet = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Courses courses = (Courses) o;

        return courseId == courses.courseId;
    }

    @Override
    public int hashCode() {
        return courseId;
    }
}
