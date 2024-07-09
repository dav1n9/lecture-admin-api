package com.example.lecturerestapi.service;

import com.example.lecturerestapi.dto.TeacherRequest;
import com.example.lecturerestapi.dto.TeacherResponse;
import com.example.lecturerestapi.entity.Admin;
import com.example.lecturerestapi.entity.AdminRole;
import com.example.lecturerestapi.entity.Teacher;
import com.example.lecturerestapi.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherResponse save(TeacherRequest request, Admin admin) {
        if (admin.getRole() == AdminRole.MANAGER || admin.getRole() == AdminRole.STAFF)
            return new TeacherResponse(teacherRepository.save(request.toEntity()));
        throw new IllegalArgumentException("관리자만 강사 등록이 가능합니다.");
    }

    @Transactional
    public TeacherResponse update(Long teacherId, TeacherRequest request, Admin admin) {
        if (admin.getRole() == AdminRole.MANAGER) {
            Teacher teacher = findTeacherById(teacherId);
            teacher.update(request);
            return new TeacherResponse(teacher);
        }
        throw new IllegalArgumentException("MANAGER 만 강사 수정이 가능합니다.");
    }

    public TeacherResponse findById(Long teacherId, Admin admin) {
        if (admin.getRole() == AdminRole.MANAGER || admin.getRole() == AdminRole.STAFF) {
            Teacher teacher = findTeacherById(teacherId);
            return new TeacherResponse(teacher);
        }
        throw new IllegalArgumentException("관리자만 강사 조회가 가능합니다.");
    }

    public Long delete(Long teacherId, Admin admin) {
        if (admin.getRole() == AdminRole.MANAGER) {
            Teacher teacher = findTeacherById(teacherId);
            teacherRepository.deleteById(teacher.getId());
            return teacher.getId();
        }
        throw new IllegalArgumentException("MANAGER 만 강사 삭제가 가능합니다.");
    }

    public Teacher findTeacherById(Long teacherId) {
        return teacherRepository.findById(teacherId).orElseThrow(NullPointerException::new);
    }
}
