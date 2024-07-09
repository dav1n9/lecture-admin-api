package com.example.lecturerestapi.teacher;

import com.example.lecturerestapi.lecture.Lecture;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "teachers")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long id;

    @Column(name = "teacher_name")
    private String name;

    private int career;

    private String company;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String biography;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lecture> lectures = new ArrayList<>();

    @Builder
    public Teacher(String name, int career, String company, String phoneNumber, String biography) {
        this.name = name;
        this.career = career;
        this.company = company;
        this.phoneNumber = phoneNumber;
        this.biography = biography;
    }

    public void update(TeacherRequest request) {
        if (request.getCareer() != 0) this.career = request.getCareer();
        if (request.getCompany() != null) this.company = request.getCompany();
        if (request.getPhoneNumber() != null) this.phoneNumber = request.getPhoneNumber();
        if (request.getBiography() != null) this.biography = request.getBiography();
    }
}
