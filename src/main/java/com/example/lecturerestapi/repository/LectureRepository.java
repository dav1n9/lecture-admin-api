package com.example.lecturerestapi.repository;

import com.example.lecturerestapi.constants.Category;
import com.example.lecturerestapi.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findByCategoryOrderByCreatedAtDesc(Category category);
}
