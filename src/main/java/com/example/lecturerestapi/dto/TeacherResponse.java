package com.example.lecturerestapi.dto;

import com.example.lecturerestapi.entity.Teacher;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TeacherResponse {
    private final Long id;
    private final String name;
    private final int career;
    private final String company;
    private final String phoneNumber;
    private final String biography;
    private final LocalDateTime createdAt;

    public TeacherResponse(Teacher teacher) {
        this.id = teacher.getId();
        this.name = teacher.getName();
        this.career = teacher.getCareer();
        this.company = teacher.getCompany();
        this.phoneNumber = teacher.getPhoneNumber();
        this.biography = teacher.getBiography();
        this.createdAt = teacher.getCreatedAt();
    }
}