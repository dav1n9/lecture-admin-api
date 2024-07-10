package com.example.lecturerestapi.admin.dto;

import com.example.lecturerestapi.admin.entity.Admin;
import com.example.lecturerestapi.admin.entity.AdminRole;
import com.example.lecturerestapi.admin.entity.Department;
import lombok.Getter;

@Getter
public class AdminResponse {
    private final String email;
    private final Department department;
    private final AdminRole role;

    public AdminResponse(Admin admin) {
        this.email = admin.getEmail();
        this.department = admin.getDepartment();
        this.role = admin.getRole();
    }
}
