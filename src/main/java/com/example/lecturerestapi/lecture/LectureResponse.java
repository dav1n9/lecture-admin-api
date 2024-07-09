package com.example.lecturerestapi.lecture;

import com.example.lecturerestapi.teacher.Teacher;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LectureResponse {
    private final Long id;
    private final String title;
    private final int price;
    private final String description;
    private final Category category;
    private final LocalDateTime createdAt;
    private final TeacherDTO teacher;

    public LectureResponse(Lecture lecture) {
        this.id = lecture.getId();
        this.title = lecture.getTitle();
        this.price = lecture.getPrice();
        this.description = lecture.getDescription();
        this.category = lecture.getCategory();
        this.createdAt = lecture.getCreatedAt();
        this.teacher = new TeacherDTO(lecture.getTeacher());
    }

    @Getter
    private static class TeacherDTO {
        private final Long id;
        private final String name;
        private final int career;
        private final String company;
        private final String phoneNumber;
        private final String biography;
        private final LocalDateTime createdAt;

        public TeacherDTO(Teacher teacher) {
            this.id = teacher.getId();
            this.name = teacher.getName();
            this.career = teacher.getCareer();
            this.company = teacher.getCompany();
            this.phoneNumber = teacher.getPhoneNumber();
            this.biography = teacher.getBiography();
            this.createdAt = teacher.getCreatedAt();
        }
    }
}