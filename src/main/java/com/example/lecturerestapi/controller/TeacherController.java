package com.example.lecturerestapi.controller;

import com.example.lecturerestapi.dto.TeacherRequest;
import com.example.lecturerestapi.dto.TeacherResponse;
import com.example.lecturerestapi.entity.Admin;
import com.example.lecturerestapi.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping
    public ResponseEntity<TeacherResponse> createTeacher(@RequestBody TeacherRequest request, HttpServletRequest req) {
        return ResponseEntity.ok()
                .body(teacherService.save(request, (Admin) req.getAttribute("admin")));
    }

    @PatchMapping("/{teacherId}")
    public ResponseEntity<TeacherResponse> updateTeacher(@PathVariable Long teacherId,
                                                         @RequestBody TeacherRequest request,
                                                         HttpServletRequest req) {
        return ResponseEntity.ok()
                .body(teacherService.update(teacherId, request, (Admin) req.getAttribute("admin")));
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<TeacherResponse> findTeacher(@PathVariable Long teacherId, HttpServletRequest req) {
        return ResponseEntity.ok()
                .body(teacherService.findById(teacherId, (Admin) req.getAttribute("admin")));
    }
    @DeleteMapping("/{teacherId}")
    public ResponseEntity<Long> deleteTeacher(@PathVariable Long teacherId, HttpServletRequest req) {
        return ResponseEntity.ok()
                .body(teacherService.delete(teacherId, (Admin) req.getAttribute("admin")));
    }
}
