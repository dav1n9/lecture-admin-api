package com.example.lecturerestapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType {
    NOT_FOUND_ADMIN(404, "존재하지 않는 관리자입니다."),
    NOT_FOUND_TEACHER(404, "존재하지 않는 강사입니다."),
    NOT_FOUND_LECTURE(404, "존재하지 않는 강의입니다."),
    ACCESS_MANAGER_ONLY(403, "해당 기능은 MANAGER 만 접근 가능합니다"),
    ACCESS_ADMIN_ONLY(403, "해당 기능은 관리자만 접근 가능합니다."),
    DUPLICATE_EMAIL_ERROR(400, "이미 존재하는 이메일입니다."),
    MARKETING_CANNOT_BE_MANAGER(400, "마케팅 부서는 MANAGER 권한을 부여 받을 수 없습니다."),
    PASSWORD_MISMATCH(401, "비밀번호가 일치하지 않습니다."),
    NOT_FOUND_TOKEN(404, "존재하지 않는 토큰입니다."),
    NOT_VALID_TOKEN(400, "잘못된 토큰입니다."),
    INVALID_JWT_SIGNATURE(401, "유효하지 않는 JWT 서명 입니다."),
    EXPIRED_JWT_TOKEN(401, "만료된 JWT token 입니다."),
    UNSUPPORTED_JWT_TOKEN(400, "지원되지 않는 JWT 토큰 입니다."),
    EMPTY_JWT_CLAIMS(400, "JWT claims is empty, 잘못된 JWT 토큰 입니다.");

    private final int code;
    private final String message;
}