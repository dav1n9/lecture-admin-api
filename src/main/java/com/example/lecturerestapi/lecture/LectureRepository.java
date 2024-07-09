package com.example.lecturerestapi.lecture;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findByCategoryOrderByCreatedAtDesc(Category category);
}
