package com.example.lecturerestapi.controller;

import com.example.lecturerestapi.dto.AdminRequest;
import com.example.lecturerestapi.dto.AdminResponse;
import com.example.lecturerestapi.service.AdminService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/signup")
    public ResponseEntity<AdminResponse> signup(@RequestBody AdminRequest request) {
        return ResponseEntity.ok()
                .body(adminService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AdminResponse> login(@RequestBody AdminRequest request, HttpServletResponse res) {
        return ResponseEntity.ok()
                .body(adminService.login(request, res));
    }
}
