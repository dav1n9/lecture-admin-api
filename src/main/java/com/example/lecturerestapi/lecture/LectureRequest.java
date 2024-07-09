package com.example.lecturerestapi.lecture;

import com.example.lecturerestapi.teacher.Teacher;
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
