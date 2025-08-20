package com.alura.forohub.service;

import com.alura.forohub.dto.topic.TopicCreateDTO;
import com.alura.forohub.dto.topic.TopicResponseDTO;
import com.alura.forohub.repository.CourseRepository;
import com.alura.forohub.repository.TopicRepository;
import com.alura.forohub.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

// ⬇ Ajustá estos imports si tus entidades están en otros paquetes
import com.alura.forohub.domain.topic.Topic;
import com.alura.forohub.domain.course.Course;
import com.alura.forohub.domain.user.AppUser;

@Service
public class TopicService {

    private final TopicRepository topicRepo;
    private final CourseRepository courseRepo;
    private final UserRepository userRepo; // <- para asignar autor por defecto

    public TopicService(TopicRepository topicRepo,
                        CourseRepository courseRepo,
                        UserRepository userRepo) {
        this.topicRepo = topicRepo;
        this.courseRepo = courseRepo;
        this.userRepo = userRepo;
    }

    // LISTAR
    @Transactional(readOnly = true)
    public List<TopicResponseDTO> listAll() {
        return topicRepo.findAll().stream()
                .map(t -> new TopicResponseDTO(
                        t.getId(),
                        t.getTitle(),
                        t.getMessage(),
                        t.getAuthor() != null ? t.getAuthor().getEmail() : null,
                        t.getCourse() != null ? t.getCourse().getId() : null,
                        t.getCourse() != null ? t.getCourse().getName() : null,
                        t.getCreatedAt()
                ))
                .toList();
    }

    // CREAR
    @Transactional
    public TopicResponseDTO create(TopicCreateDTO dto) {
        // 1) Validar curso
        Course course = courseRepo.findById(dto.courseId())
                .orElseThrow(() -> new IllegalArgumentException("courseId no existe: " + dto.courseId()));

        // 2) Construir tópico
        Topic t = new Topic();
        t.setTitle(dto.title());
        t.setMessage(dto.message());
        t.setCourse(course);

        // 3) Si Topic.author es NOT NULL en la DB, asignar un usuario existente (id=1 o el que tengas)
        //    Cambiá el id/email a algo que exista en tu tabla app_user.
        AppUser author = userRepo.findById(1L).orElse(null);
        t.setAuthor(author); // si la columna es NOT NULL, asegurate que 'author' no quede null

        // 4) createdAt por si tu entidad no lo setea con @PrePersist
        if (t.getCreatedAt() == null) {
            t.setCreatedAt(OffsetDateTime.now());
        }

        try {
            t = topicRepo.save(t);
        } catch (DataIntegrityViolationException e) {
            // Cualquier constraint de DB → 400 claro
            throw new IllegalArgumentException("Datos inválidos para crear el tópico (revisá author/curso obligatorios).");
        }

        // 5) Mapear a DTO de respuesta
        return new TopicResponseDTO(
                t.getId(),
                t.getTitle(),
                t.getMessage(),
                t.getAuthor() != null ? t.getAuthor().getEmail() : null,
                t.getCourse() != null ? t.getCourse().getId() : null,
                t.getCourse() != null ? t.getCourse().getName() : null,
                t.getCreatedAt()
        );
    }
}
