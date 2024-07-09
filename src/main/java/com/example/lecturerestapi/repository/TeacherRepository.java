package com.example.lecturerestapi.repository;

import com.example.lecturerestapi.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
