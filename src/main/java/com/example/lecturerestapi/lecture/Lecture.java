package com.example.lecturerestapi.lecture;

import com.example.lecturerestapi.teacher.Teacher;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "lectures")
@EntityListeners(AuditingEntityListener.class)
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    private String title;

    private int price;

    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Builder
    public Lecture(String title, int price, String description, Category category, Teacher teacher) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.teacher = teacher;
    }

    public void update(LectureRequest request) {
        if (request.getTitle() != null) this.title = request.getTitle();
        if (request.getPrice() != 0) this.price = request.getPrice();
        if (request.getDescription() != null) this.description = request.getDescription();
        if (request.getCategory() != null) this.category = request.getCategory();
    }
}
