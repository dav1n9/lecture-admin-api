package com.example.lecturerestapi.lecture.service;

import com.example.lecturerestapi.admin.entity.Admin;
import com.example.lecturerestapi.lecture.dto.LectureRequest;
import com.example.lecturerestapi.lecture.dto.LectureResponse;
import com.example.lecturerestapi.lecture.entity.Category;
import com.example.lecturerestapi.teacher.service.TeacherService;
import com.example.lecturerestapi.teacher.entity.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureFacade {
    private final LectureService lectureService;
    private final TeacherService teacherService;

    public LectureResponse save(LectureRequest request, Admin admin) {
        teacherService.validateAdmin(admin);
        Teacher teacher = teacherService.findTeacherById(request.getTeacherId());
        return lectureService.save(request, teacher);
    }

    public LectureResponse update(Long lectureId, LectureRequest request, Admin admin) {
        teacherService.validateManager(admin);
        return lectureService.update(lectureId, request);
    }

    public LectureResponse findById(Long lectureId, Admin admin) {
        teacherService.validateAdmin(admin);
        return lectureService.findById(lectureId);
    }

    public List<LectureResponse> findByCategory(Category category, Admin admin) {
        teacherService.validateAdmin(admin);
        return lectureService.findByCategory(category);
    }

    public Long delete(Long lectureId, Admin admin) {
        teacherService.validateManager(admin);
        return lectureService.delete(lectureId);
    }
}
