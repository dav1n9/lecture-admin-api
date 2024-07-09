package com.example.lecturerestapi.service;

import com.example.lecturerestapi.entity.Admin;
import com.example.lecturerestapi.entity.AdminRole;
import com.example.lecturerestapi.entity.Category;
import com.example.lecturerestapi.dto.LectureRequest;
import com.example.lecturerestapi.dto.LectureResponse;
import com.example.lecturerestapi.entity.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureFacade {
    private final LectureService lectureService;
    private final TeacherService teacherService;

    public LectureResponse save(LectureRequest request, Admin admin) {
        if (admin.getRole() == AdminRole.MANAGER || admin.getRole() == AdminRole.STAFF) {
            Teacher teacher = teacherService.findTeacherById(request.getTeacherId());
            return lectureService.save(request, teacher);
        }
        throw new IllegalArgumentException("관리자만 강사 등록이 가능합니다.");
    }

    public LectureResponse update(Long lectureId, LectureRequest request, Admin admin) {
        if (admin.getRole() == AdminRole.MANAGER) {
            return lectureService.update(lectureId, request);
        }
        throw new IllegalArgumentException("MANAGER 만 강사 수정이 가능합니다.");
    }

    public LectureResponse findById(Long lectureId, Admin admin) {
        if (admin.getRole() == AdminRole.MANAGER || admin.getRole() == AdminRole.STAFF) {
            return lectureService.findById(lectureId);
        }
        throw new IllegalArgumentException("관리자만 강사 조회가 가능합니다.");
    }

    public List<LectureResponse> findByCategory(Category category, Admin admin) {
        if (admin.getRole() == AdminRole.MANAGER || admin.getRole() == AdminRole.STAFF) {
            return lectureService.findByCategory(category);
        }
        throw new IllegalArgumentException("관리자만 강사 조회가 가능합니다.");
    }

    public Long delete(Long lectureId, Admin admin) {
        if (admin.getRole() == AdminRole.MANAGER) {
            return lectureService.delete(lectureId);
        }
        throw new IllegalArgumentException("MANAGER 만 강사 삭제가 가능합니다.");
    }
}
