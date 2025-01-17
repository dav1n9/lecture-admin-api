package com.example.lecturerestapi.lecture.repository;

import com.example.lecturerestapi.lecture.entity.Category;
import com.example.lecturerestapi.lecture.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findByCategoryOrderByCreatedAtDesc(Category category);
}
