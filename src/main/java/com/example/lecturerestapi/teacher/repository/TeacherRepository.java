package com.example.lecturerestapi.teacher.repository;

import com.example.lecturerestapi.teacher.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
