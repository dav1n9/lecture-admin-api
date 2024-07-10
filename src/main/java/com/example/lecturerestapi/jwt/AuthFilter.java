package com.example.lecturerestapi.jwt;

import com.example.lecturerestapi.dto.ErrorResponse;
import com.example.lecturerestapi.entity.Admin;
import com.example.lecturerestapi.exception.ErrorType;
import com.example.lecturerestapi.repository.AdminRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthFilter implements Filter {

    private final AdminRepository adminRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(url) && (url.startsWith("/admin"))) {
            chain.doFilter(request, response);
        } else {
            // 토큰 확인
            String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);

            if (StringUtils.hasText(tokenValue)) {
                String token = jwtUtil.substringToken(tokenValue);

                if (!jwtUtil.validateToken(token)) {
                    exceptionHandler((HttpServletResponse) response, ErrorType.NOT_VALID_TOKEN);
                    return;
                }

                Claims info = jwtUtil.getUserInfoFromToken(token);
                Optional<Admin> admin = adminRepository.findByEmail(info.getSubject());
                if(admin.isEmpty()) {
                    exceptionHandler((HttpServletResponse) response, ErrorType.NOT_FOUND_ADMIN);
                    return;
                }

                request.setAttribute("admin", admin.get());
                chain.doFilter(request, response); // 다음 Filter 로 이동
            } else {
                exceptionHandler((HttpServletResponse) response, ErrorType.NOT_FOUND_TOKEN);
            }
        }
    }

    public void exceptionHandler(HttpServletResponse response, ErrorType error) {
        response.setStatus(error.getCode());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ErrorResponse errorResponse = new ErrorResponse(error.getCode(), error.getMessage());
        try {
            response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}