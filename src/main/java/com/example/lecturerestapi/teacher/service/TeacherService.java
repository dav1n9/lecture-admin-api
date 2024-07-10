package com.example.lecturerestapi.teacher.service;

import com.example.lecturerestapi.admin.entity.Admin;
import com.example.lecturerestapi.admin.entity.AdminRole;
import com.example.lecturerestapi.exception.ErrorType;
import com.example.lecturerestapi.teacher.dto.TeacherRequest;
import com.example.lecturerestapi.teacher.dto.TeacherResponse;
import com.example.lecturerestapi.teacher.entity.Teacher;
import com.example.lecturerestapi.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherResponse save(TeacherRequest request, Admin admin) {
        validateAdmin(admin);
        return new TeacherResponse(teacherRepository.save(request.toEntity()));
    }

    @Transactional
    public TeacherResponse update(Long teacherId, TeacherRequest request, Admin admin) {
        validateManager(admin);
        Teacher teacher = findTeacherById(teacherId);
        teacher.update(request);
        return new TeacherResponse(teacher);
    }

    public TeacherResponse findById(Long teacherId, Admin admin) {
        validateAdmin(admin);
        Teacher teacher = findTeacherById(teacherId);
        return new TeacherResponse(teacher);
    }

    public Long delete(Long teacherId, Admin admin) {
        validateManager(admin);
        Teacher teacher = findTeacherById(teacherId);
        teacherRepository.deleteById(teacher.getId());
        return teacher.getId();
    }

    public Teacher findTeacherById(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NullPointerException(ErrorType.NOT_FOUND_TEACHER.getMessage()));
    }

    public void validateAdmin(Admin admin) {
        if (admin.getRole() != AdminRole.MANAGER && admin.getRole() != AdminRole.STAFF)
            throw new IllegalArgumentException(ErrorType.ACCESS_ADMIN_ONLY.getMessage());
    }

    public void validateManager(Admin admin) {
        if (admin.getRole() != AdminRole.MANAGER)
            throw new IllegalArgumentException(ErrorType.ACCESS_MANAGER_ONLY.getMessage());
    }
}
