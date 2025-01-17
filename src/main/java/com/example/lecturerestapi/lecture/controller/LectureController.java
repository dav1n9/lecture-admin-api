package com.example.lecturerestapi.lecture.controller;

import com.example.lecturerestapi.admin.entity.Admin;
import com.example.lecturerestapi.lecture.dto.LectureRequest;
import com.example.lecturerestapi.lecture.dto.LectureResponse;
import com.example.lecturerestapi.lecture.entity.Category;
import com.example.lecturerestapi.lecture.service.LectureFacade;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lectures")
@RequiredArgsConstructor
public class LectureController {
    private final LectureFacade lectureFacade;

    @PostMapping
    public ResponseEntity<LectureResponse> createLecture(@RequestBody LectureRequest request, HttpServletRequest req) {
        return ResponseEntity.ok()
                .body(lectureFacade.save(request, (Admin) req.getAttribute("admin")));
    }

    @PatchMapping("/{lectureId}")
    public ResponseEntity<LectureResponse> updateLecture(@PathVariable Long lectureId,
                                                         @RequestBody LectureRequest request,
                                                         HttpServletRequest req) {
        return ResponseEntity.ok()
                .body(lectureFacade.update(lectureId, request, (Admin) req.getAttribute("admin")));
    }

    @GetMapping("/{lectureId}")
    public ResponseEntity<LectureResponse> findLecture(@PathVariable Long lectureId, HttpServletRequest req) {
        return ResponseEntity.ok()
                .body(lectureFacade.findById(lectureId, (Admin) req.getAttribute("admin")));
    }

    @GetMapping
    public ResponseEntity<List<LectureResponse>> findLecturesByCategory(@RequestParam Category category,
                                                                        HttpServletRequest req) {
        return ResponseEntity.ok()
                .body(lectureFacade.findByCategory(category, (Admin) req.getAttribute("admin")));
    }

    @DeleteMapping("/{lectureId}")
    public ResponseEntity<Long> deleteLecture(@PathVariable Long lectureId, HttpServletRequest req) {
        return ResponseEntity.ok()
                .body(lectureFacade.delete(lectureId, (Admin) req.getAttribute("admin")));
    }
}