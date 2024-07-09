package com.example.lecturerestapi.service;

import com.example.lecturerestapi.dto.TeacherRequest;
import com.example.lecturerestapi.dto.TeacherResponse;
import com.example.lecturerestapi.entity.Teacher;
import com.example.lecturerestapi.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherResponse save(TeacherRequest request) {
        return new TeacherResponse(teacherRepository.save(request.toEntity()));
    }

    @Transactional
    public TeacherResponse update(Long teacherId, TeacherRequest request) {
        Teacher teacher = findTeacherById(teacherId);
        teacher.update(request);
        return new TeacherResponse(teacher);
    }

    public TeacherResponse findById(Long teacherId) {
        Teacher teacher = findTeacherById(teacherId);
        return new TeacherResponse(teacher);
    }

    public Long delete(Long teacherId) {
        Teacher teacher = findTeacherById(teacherId);
        teacherRepository.deleteById(teacher.getId());
        return teacher.getId();
    }

    public Teacher findTeacherById(Long teacherId) {
        return teacherRepository.findById(teacherId).orElseThrow(NullPointerException::new);
    }
}
