package org.example.login.repository;

import org.example.login.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("select c from Course c where c.major.majorId = :majorId")
    List<Course> findByMajor(@Param("majorId") Long majorId);
}
