package com.example.lecturerestapi.lecture;

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
    public ResponseEntity<LectureResponse> createLecture(@RequestBody LectureRequest request) {
        return ResponseEntity.ok()
                .body(lectureFacade.save(request));
    }

    @PatchMapping("/{lectureId}")
    public ResponseEntity<LectureResponse> updateLecture(@PathVariable Long lectureId, @RequestBody LectureRequest request) {
        return ResponseEntity.ok()
                .body(lectureFacade.update(lectureId, request));
    }

    @GetMapping("/{lectureId}")
    public ResponseEntity<LectureResponse> findLecture(@PathVariable Long lectureId) {
        return ResponseEntity.ok()
                .body(lectureFacade.findById(lectureId));
    }

    @GetMapping
    public ResponseEntity<List<LectureResponse>> findLecturesByCategory(@RequestParam Category category) {
        return ResponseEntity.ok()
                .body(lectureFacade.findByCategory(category));
    }

    @DeleteMapping("/{lectureId}")
    public ResponseEntity<Long> deleteLecture(@PathVariable Long lectureId) {
        return ResponseEntity.ok()
                .body(lectureFacade.delete(lectureId));
    }
}