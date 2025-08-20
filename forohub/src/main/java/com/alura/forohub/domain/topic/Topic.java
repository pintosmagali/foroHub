package com.alura.forohub.domain.topic;

import com.alura.forohub.domain.course.Course;
import com.alura.forohub.domain.user.AppUser;
import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(nullable = false, length = 4000)
    private String message;

    @Column(nullable = false, name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TopicStatus status = TopicStatus.OPEN;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private AppUser author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private Course course;

    public Topic() { }

    public Topic(String title, String message, AppUser author, Course course) {
        this.title = title;
        this.message = message;
        this.author = author;
        this.course = course;
    }

    // -------- getters / setters ----------
    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public TopicStatus getStatus() { return status; }
    public void setStatus(TopicStatus status) { this.status = status; }

    public AppUser getAuthor() { return author; }
    public void setAuthor(AppUser author) { this.author = author; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}
