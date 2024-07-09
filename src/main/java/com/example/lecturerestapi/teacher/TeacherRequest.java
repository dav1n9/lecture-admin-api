package com.example.lecturerestapi.teacher;

import lombok.Getter;

@Getter
public class TeacherRequest {
    private String name;
    private int career;
    private String company;
    private String phoneNumber;
    private String biography;

    public Teacher toEntity() {
        return Teacher.builder()
                .name(name)
                .career(career)
                .company(company)
                .phoneNumber(phoneNumber)
                .biography(biography)
                .build();
    }
}
