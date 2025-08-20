package com.alura.forohub.controller;

import com.alura.forohub.dto.topic.TopicCreateDTO;
import com.alura.forohub.dto.topic.TopicResponseDTO;
import com.alura.forohub.service.TopicService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    // GET /api/v1/topics  -> lista
    @PermitAll
    @GetMapping
    public List<TopicResponseDTO> list() {
        return topicService.listAll();
    }

    // POST /api/v1/topics -> crear
    @PermitAll
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TopicResponseDTO create(@Valid @RequestBody TopicCreateDTO dto) {
        return topicService.create(dto);
    }
}


