package org.example.login.service;

import org.example.login.domain.Course;
import org.example.login.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;

    public List<Course> findByMajor(Long majorId) {
        return courseRepository.findByMajor(majorId);
    }

    public Course findById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Failed: No Course Info"));
    }
}
