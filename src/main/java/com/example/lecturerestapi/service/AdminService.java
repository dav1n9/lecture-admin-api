package com.example.lecturerestapi.service;

import com.example.lecturerestapi.dto.AdminRequest;
import com.example.lecturerestapi.dto.AdminResponse;
import com.example.lecturerestapi.entity.Admin;
import com.example.lecturerestapi.entity.AdminRole;
import com.example.lecturerestapi.entity.Department;
import com.example.lecturerestapi.exception.ErrorType;
import com.example.lecturerestapi.jwt.JwtUtil;
import com.example.lecturerestapi.repository.AdminRepository;
import jakarta.servlet.http.HttpServletResponse;
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
    private final JwtUtil jwtUtil;

    public AdminResponse signup(@Valid AdminRequest request) {
        String email = request.getEmail();
        String password = passwordEncoder.encode(request.getPassword());

        // email 중복 확인
        Optional<Admin> checkEmail = adminRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException(ErrorType.DUPLICATE_EMAIL_ERROR.getMessage());
        }

        // 마케팅 부서는 MANAGER 권한을 부여 받을 수 없음
        if (request.getDepartment() == Department.MARKETING && request.getRole() == AdminRole.MANAGER) {
            throw new IllegalArgumentException(ErrorType.MARKETING_CANNOT_BE_MANAGER.getMessage());
        }

        // 사용자 등록
        return new AdminResponse(adminRepository.save(request.toEntity(password)));
    }

    public AdminResponse login(AdminRequest requestDto, HttpServletResponse res) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        // 사용자 확인
        Admin admin = adminRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException(ErrorType.NOT_FOUND_ADMIN.getMessage())
        );

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new IllegalArgumentException(ErrorType.PASSWORD_MISMATCH.getMessage());
        }

        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        String token = jwtUtil.createToken(admin.getEmail(), admin.getRole());
        jwtUtil.addJwtToCookie(token, res);

        return new AdminResponse(admin);
    }
}