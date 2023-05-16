package com.strongteam.newsapp.controller;

import com.strongteam.newsapp.entity.News;
import com.strongteam.newsapp.payload.NewsDTO;
import com.strongteam.newsapp.service.impls.NewsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Optional;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsServiceImpl newsService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MODERATOR')")
    public ResponseEntity<?> getAllTopics(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return new ResponseEntity<>(newsService.getAllNews(PageRequest.of(page, size)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MODERATOR')")
    public ResponseEntity<?> getTopicById(@PathVariable Long id) {
        Optional<News> newsID = newsService.getNewsById(id);
        if (newsID.isPresent()) {
            return ResponseEntity.ok(newsID.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/bySource/{sourceId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MODERATOR')")
    public ResponseEntity<?> getNewsBySourceId(
            @PathVariable Long sourceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return new ResponseEntity<>(newsService.findBySourceId(sourceId, PageRequest.of(page, size)), HttpStatus.OK);
    }

    @GetMapping("/byTopic/{topicId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MODERATOR')")
    public ResponseEntity<?> getNewsByTopicId(
            @PathVariable Long topicId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return new ResponseEntity<>(newsService.findByTopicId(topicId, PageRequest.of(page, size)), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<?> createTopic(@RequestBody NewsDTO newsDTO) throws ParseException {
        return new ResponseEntity<>(newsService.saveNews(newsDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{newsId}")
    @PreAuthorize("hasAnyRole('ADMIN',  'MODERATOR')")
    public ResponseEntity<?> updateTopic(
            @PathVariable Long newsId,
            @RequestBody NewsDTO newsDTO) throws ParseException {
        Optional<News> newsById = newsService.getNewsById(newsId);

        if (newsById.isPresent()) {
            return new ResponseEntity<>(newsService.updateNews(newsById.get(),newsDTO), HttpStatus.CREATED);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deleteTopic(@PathVariable Long id) {
        Optional<News> newsID = newsService.getNewsById(id);
        if (newsID.isPresent()) {
            newsService.deleteNewsById(id);
            return ResponseEntity.noContent().build();
        } else
            return ResponseEntity.internalServerError().build();
    }
}
