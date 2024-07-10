package com.example.lecturerestapi.admin.dto;

import com.example.lecturerestapi.admin.entity.Admin;
import com.example.lecturerestapi.admin.entity.AdminRole;
import com.example.lecturerestapi.admin.entity.Department;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class AdminRequest {
    @Email
    private String email;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}")
    private String password;

    private Department department;

    private AdminRole role;

    public Admin toEntity(String password) {
        return Admin.builder()
                .email(email)
                .password(password)
                .department(department)
                .role(role)
                .build();
    }
}
