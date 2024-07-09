package com.example.lecturerestapi.service;

import com.example.lecturerestapi.dto.AdminRequest;
import com.example.lecturerestapi.dto.AdminResponse;
import com.example.lecturerestapi.entity.Admin;
import com.example.lecturerestapi.entity.AdminRole;
import com.example.lecturerestapi.entity.Department;
import com.example.lecturerestapi.repository.AdminRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;


@Service
@Validated
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminResponse signup(@Valid AdminRequest request) {
        String email = request.getEmail();
        String password = passwordEncoder.encode(request.getPassword());

        // email 중복 확인
        Optional<Admin> checkEmail = adminRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 마케팅 부서는 MANAGER 권한을 부여 받을 수 없음
        if (request.getDepartment() == Department.MARKETING && request.getRole() == AdminRole.MANAGER) {
            throw new IllegalArgumentException("마케팅 부서는 MANAGER 권한을 부여 받을 수 없습니다.");
        }

        // 사용자 등록
        return new AdminResponse(adminRepository.save(request.toEntity(password)));
    }
}