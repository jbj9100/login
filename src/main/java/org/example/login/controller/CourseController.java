package org.example.login.controller;

import org.example.login.*;
import lombok.RequiredArgsConstructor;
import org.example.login.controller.dto.ClassSearch;
import org.example.login.domain.Course;
import org.example.login.domain.Major;
import org.example.login.service.CourseService;
import org.example.login.service.MajorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final MajorService majorService;

    @GetMapping("/courses")
    public String courseList(@ModelAttribute("classSearch") ClassSearch classSearch, Model model) {
        List<Course> courses = courseService.findByMajor(classSearch.getMajorId());
        List<Major> majors = majorService.findAll();

        model.addAttribute("courses", courses);
        model.addAttribute("majors", majors);

        return "courseList";
    }

    @GetMapping("/courses/{id}")
    public String classList(@PathVariable("id") Long courseId, Model model) {
        Course course = courseService.findById(courseId);

        model.addAttribute("classes", course.getClasses());

        return "classList";
    }
}
