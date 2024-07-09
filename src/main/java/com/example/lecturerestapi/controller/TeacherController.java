package com.example.lecturerestapi.controller;

import com.example.lecturerestapi.dto.TeacherRequest;
import com.example.lecturerestapi.dto.TeacherResponse;
import com.example.lecturerestapi.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping
    public ResponseEntity<TeacherResponse> createTeacher(@RequestBody TeacherRequest request) {
        return ResponseEntity.ok()
                .body(teacherService.save(request));
    }

    @PatchMapping("/{teacherId}")
    public ResponseEntity<TeacherResponse> updateTeacher(@PathVariable Long teacherId, @RequestBody TeacherRequest request) {
        return ResponseEntity.ok()
                .body(teacherService.update(teacherId, request));
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<TeacherResponse> findTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok()
                .body(teacherService.findById(teacherId));
    }
    @DeleteMapping("/{teacherId}")
    public ResponseEntity<Long> deleteTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok()
                .body(teacherService.delete(teacherId));
    }
}
