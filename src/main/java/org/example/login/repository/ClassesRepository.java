package org.example.login.repository;

import org.example.login.domain.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassesRepository extends JpaRepository<Classes, Long> {

    @Query("select c from Classes c where c.course.courseId = :courseId")
    List<Classes> findByCourse(@Param("courseId") Long courseId);
}
