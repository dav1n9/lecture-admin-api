package com.example.lecturerestapi.dto;

import com.example.lecturerestapi.entity.Category;
import com.example.lecturerestapi.entity.Lecture;
import com.example.lecturerestapi.entity.Teacher;
import lombok.Getter;

@Getter
public class LectureRequest {
    private String title;
    private int price;
    private String description;
    private Category category;
    private Long teacherId;

    public Lecture toEntity(Teacher teacher) {
        return Lecture.builder()
                .title(title)
                .price(price)
                .description(description)
                .category(category)
                .teacher(teacher)
                .build();
    }
}
