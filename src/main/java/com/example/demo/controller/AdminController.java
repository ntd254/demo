package com.example.demo.controller;

import com.example.demo.model.Courses;
import com.example.demo.model.EazyClass;
import com.example.demo.model.Person;
import com.example.demo.repository.CoursesRepository;
import com.example.demo.repository.EazyClassRepository;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    private final EazyClassRepository eazyClassRepository;
    private final PersonRepository personRepository;
    private final CoursesRepository coursesRepository;

    @Autowired
    public AdminController(EazyClassRepository eazyClassRepository, PersonRepository personRepository, CoursesRepository coursesRepository) {
        this.eazyClassRepository = eazyClassRepository;
        this.personRepository = personRepository;
        this.coursesRepository = coursesRepository;
    }

    @GetMapping(path = "/displayClasses")
    public ModelAndView displayClasses() {
        ModelAndView modelAndView = new ModelAndView("classes");
        List<EazyClass> eazyClasses = eazyClassRepository.findAll();
        modelAndView.addObject("eazyClass", new EazyClass());
        modelAndView.addObject("eazyClasses", eazyClasses);
        return modelAndView;
    }

    @PostMapping(path = "/addNewClass")
    public String addNewClass(@ModelAttribute EazyClass eazyClass) {
        eazyClassRepository.save(eazyClass);
        return "redirect:/admin/displayClasses";
    }

    @GetMapping(path = "/deleteClass")
    public String deleteClass(@RequestParam int id) {
        Optional<EazyClass> eazyClass = eazyClassRepository.findById(id);
        for (Person person : eazyClass.get().getPersonSet()) {
            person.setEazyClass(null);
            personRepository.save(person);
        }
        eazyClassRepository.deleteById(id);
        return "redirect:/admin/displayClasses";
    }

    @GetMapping(path = "displayStudents")
    public ModelAndView displayStudents(@RequestParam int id,
                                        @RequestParam(required = false) String error, HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView("students");
        Optional<EazyClass> eazyClass = eazyClassRepository.findById(id);
        modelAndView.addObject("eazyClass", eazyClass.get());
        modelAndView.addObject("person", new Person());
        httpSession.setAttribute("eazyClass", eazyClass.get());
        if (error != null) {
            modelAndView.addObject("errorMessage", "Invalid Email");
        }
        return modelAndView;
    }

    @PostMapping(path = "/addStudent")
    public ModelAndView addStudent(@ModelAttribute Person person, HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();
        EazyClass eazyClass = (EazyClass) httpSession.getAttribute("eazyClass");
        Person personEntity = personRepository.findPersonByEmail(person.getEmail());
        if (personEntity == null) {
            modelAndView.setViewName("redirect:/admin/displayStudents?id=" + eazyClass.getClassId() + "&error=true");
            return modelAndView;
        }
        eazyClass.getPersonSet().add(personEntity);
        personEntity.setEazyClass(eazyClass);
        personRepository.save(personEntity);
        eazyClassRepository.save(eazyClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?id=" + eazyClass.getClassId());
        return modelAndView;
    }

    @GetMapping(path = "/deleteStudent")
    public String deleteStudent(@RequestParam int personId, HttpSession httpSession) {
        EazyClass eazyClass = (EazyClass) httpSession.getAttribute("eazyClass");
        Optional<Person> person = personRepository.findById(personId);
        eazyClass.getPersonSet().remove(person.get());
        person.get().setEazyClass(null);
        personRepository.save(person.get());
        EazyClass eazyClassSaved = eazyClassRepository.save(eazyClass);
        httpSession.setAttribute("eazyClass", eazyClassSaved);
        return "redirect:/admin/displayStudents?id=" + eazyClass.getClassId();
    }

    @GetMapping(path = "/displayCourses")
    public ModelAndView displayCourses() {
        List<Courses> courses = coursesRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("courses_secure");
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("course", new Courses());
        return modelAndView;
    }

    @PostMapping(path = "/addNewCourse")
    public ModelAndView addNewCourse(@ModelAttribute Courses course) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayCourses");
        coursesRepository.save(course);
        return modelAndView;
    }

    @GetMapping(path = "/viewStudents")
    public ModelAndView viewStudents(@RequestParam int id, @RequestParam(required = false) String error ,HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView("course_students");
        Courses courses = coursesRepository.findById(id).get();
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("person", new Person());
        httpSession.setAttribute("courses", courses);
        if (error != null) modelAndView.addObject("errorMessage", "Invalid email");
        return modelAndView;
    }

    @PostMapping(path = "/addStudentToCourse")
    public ModelAndView addStudentToCourse(@ModelAttribute Person person, HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();
        Courses courses = (Courses) httpSession.getAttribute("courses");
        Person personEntity = personRepository.findPersonByEmail(person.getEmail());
        if (personEntity == null) {
            modelAndView.setViewName("redirect:/admin/viewStudents?id=" + courses.getCourseId() + "&error=true");
            return modelAndView;
        }
        personEntity.getCourses().add(courses);
        courses.getPersonSet().add(personEntity);
        coursesRepository.save(courses);
        personRepository.save(personEntity);
        httpSession.setAttribute("courses", courses);
        modelAndView.setViewName("redirect:/admin/viewStudents?id=" + courses.getCourseId());
        return modelAndView;
    }

    @GetMapping("/deleteStudentFromCourse")
    public String deleteStudentFromCourse(@RequestParam int id, HttpSession httpSession) {
        Courses courses = (Courses) httpSession.getAttribute("courses");
        Person person = personRepository.findById(id).get();
        person.getCourses().remove(courses);
        courses.getPersonSet().remove(person);
        coursesRepository.save(courses);
        personRepository.save(person);
        httpSession.setAttribute("courses", courses);
        return "redirect:/admin/viewStudents?id="+courses.getCourseId();
    }
}