package com.studentportal.webapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.studentportal.webapp.models.course;
import com.studentportal.webapp.models.student;



public interface courseRepo extends JpaRepository<course, Long>{
    
    @Query(value = "SELECT s.* FROM student s INNER JOIN register r ON  s.student_id = r.student_id INNER JOIN course c ON c.course_id = r.course_id WHERE c.course_id = ?1",nativeQuery = true)
    public List<student> getCourseStudents(@Param("course_id") Long course_id); 

    @Query(value = "SELECT c.* FROM course c  WHERE c.course_name = ?1",nativeQuery = true)
    public course getCourseByName(@Param("course_name") String course_name); 
}
