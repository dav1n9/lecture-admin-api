package com.example.lecturerestapi.admin.service;

import com.example.lecturerestapi.admin.dto.AdminRequest;
import com.example.lecturerestapi.admin.dto.AdminResponse;
import com.example.lecturerestapi.admin.entity.Admin;
import com.example.lecturerestapi.admin.entity.AdminRole;
import com.example.lecturerestapi.admin.entity.Department;
import com.example.lecturerestapi.admin.repository.AdminRepository;
import com.example.lecturerestapi.exception.ErrorType;
import com.example.lecturerestapi.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * 관리자 회원가입 메소드.
     * 1. 동일한 email 로 가입할 수 없다.
     * 2. 마케팅 부서는 MANAGER 권한을 부여 받을 수 없다.
     * @param request 회원가입을 진행할 관리자 정보가 담긴 DTO
     * @return 회원가입이 완료된 관리자 정보를 담은 응답 DTO
     * @throws IllegalArgumentException 동일한 이메일이 이미 존재하거나 마케팅 부서가 MANAGER 권한을 요청한 경우
     */
    public AdminResponse signup(@Valid AdminRequest request) {
        if (adminRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException(ErrorType.DUPLICATE_EMAIL_ERROR.getMessage());
        }

        if (request.getDepartment() == Department.MARKETING && request.getRole() == AdminRole.MANAGER) {
            throw new IllegalArgumentException(ErrorType.MARKETING_CANNOT_BE_MANAGER.getMessage());
        }

        String password = passwordEncoder.encode(request.getPassword());
        return new AdminResponse(adminRepository.save(request.toEntity(password)));
    }

    /**
     * 관리자 로그인 메소드.
     * 관리자의 존재 여부와 비밀번호를 확인한 뒤,
     * JWT 를 생성하여 쿠키에 저장하고 Response 객체에 추가한다.
     * @param requestDto 관리자 로그인 요청 정보가 담긴 DTO
     * @param res HTTP 응답 객체
     * @return 로그인된 관리자의 정보를 담은 응답 DTO
     * @throws IllegalArgumentException 관리자 정보가 없거나 비밀번호가 일치하지 않는 경우
     */
    public AdminResponse login(AdminRequest requestDto, HttpServletResponse res) {
        Admin admin = adminRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new IllegalArgumentException(ErrorType.NOT_FOUND_ADMIN.getMessage()));

        if (!passwordEncoder.matches(requestDto.getPassword(), admin.getPassword())) {
            throw new IllegalArgumentException(ErrorType.PASSWORD_MISMATCH.getMessage());
        }

        String token = jwtUtil.createToken(admin.getEmail(), admin.getRole());
        jwtUtil.addJwtToCookie(token, res);

        return new AdminResponse(admin);
    }
}