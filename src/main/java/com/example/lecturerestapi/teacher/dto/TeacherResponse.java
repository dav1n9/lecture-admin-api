package com.example.lecturerestapi.teacher.dto;

import com.example.lecturerestapi.lecture.entity.Category;
import com.example.lecturerestapi.lecture.entity.Lecture;
import com.example.lecturerestapi.teacher.entity.Teacher;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class TeacherResponse {
    private final Long id;
    private final String name;
    private final int career;
    private final String company;
    private final String phoneNumber;
    private final String biography;
    private final LocalDateTime createdAt;
    private final List<LectureDTO> lectures;

    public TeacherResponse(Teacher teacher) {
        this.id = teacher.getId();
        this.name = teacher.getName();
        this.career = teacher.getCareer();
        this.company = teacher.getCompany();
        this.phoneNumber = teacher.getPhoneNumber();
        this.biography = teacher.getBiography();
        this.createdAt = teacher.getCreatedAt();
        this.lectures = teacher.getLectures().stream().map(LectureDTO::new).toList();
    }

    @Getter
    private static class LectureDTO {
        private final Long id;
        private final String title;
        private final int price;
        private final String description;
        private final Category category;
        private final LocalDateTime createdAt;

        public LectureDTO(Lecture lecture) {
            this.id = lecture.getId();
            this.title = lecture.getTitle();
            this.price = lecture.getPrice();
            this.description = lecture.getDescription();
            this.category = lecture.getCategory();
            this.createdAt = lecture.getCreatedAt();
        }
    }
}
