package com.strongteam.newsapp.controller;

import com.strongteam.newsapp.entity.Topic;
import com.strongteam.newsapp.service.impls.TopicServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicContoller {
    private final TopicServiceImpl topicService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MODERATOR')")
    public ResponseEntity<?> getAllTopics() {
        return new ResponseEntity<>(topicService.getAllTopics(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MODERATOR')")
    public ResponseEntity<?> getTopicById(@PathVariable Long id) {
        Optional<Topic> topicById = topicService.getTopicById(id);
        if (topicById.isPresent()) {
            return ResponseEntity.ok(topicById.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<?> createTopic(@RequestBody Topic topic) {
        return new ResponseEntity<>(topicService.saveOrUpdateTopic(topic), HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<?> updateTopic(@RequestBody Topic topic) {
        Optional<Topic> topicById = topicService.getTopicById(topic.getId());
        if (topicById.isPresent()) {
            return new ResponseEntity<>(topicService.saveOrUpdateTopic(topic), HttpStatus.CREATED);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopicById(id);
        return ResponseEntity.noContent().build();
    }
}
