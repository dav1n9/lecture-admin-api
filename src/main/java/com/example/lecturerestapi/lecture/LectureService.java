package com.example.lecturerestapi.lecture;

import com.example.lecturerestapi.teacher.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;

    public LectureResponse save(LectureRequest request, Teacher teacher) {
        return new LectureResponse(lectureRepository.save(request.toEntity(teacher)));
    }

    @Transactional
    public LectureResponse update(Long lectureId, LectureRequest request) {
        Lecture lecture = findLectureById(lectureId);
        lecture.update(request);
        return new LectureResponse(lecture);
    }

    public LectureResponse findById(Long lectureId) {
        return new LectureResponse(findLectureById(lectureId));
    }

    public List<LectureResponse> findByCategory(Category category) {
        return lectureRepository.findByCategoryOrderByCreatedAtDesc(category)
                .stream().map(LectureResponse::new).toList();
    }

    public Long delete(Long lectureId) {
        Lecture lecture = findLectureById(lectureId);
        lectureRepository.deleteById(lecture.getId());
        return lecture.getId();
    }

    public Lecture findLectureById(Long lectureId) {
        return lectureRepository.findById(lectureId).orElseThrow(NullPointerException::new);
    }
}
