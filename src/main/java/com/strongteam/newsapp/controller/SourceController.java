package com.strongteam.newsapp.controller;

import com.strongteam.newsapp.entity.Source;
import com.strongteam.newsapp.service.impls.SourceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/source")
@RequiredArgsConstructor
public class SourceController {
    private final SourceServiceImpl sourceService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MODERATOR')")
    public ResponseEntity<?> getAllSources() {
        return new ResponseEntity<>(sourceService.getAllSource(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MODERATOR')")
    public ResponseEntity<?> getSourceById(@PathVariable Long id) {
        Optional<Source> sourceById = sourceService.getSourceById(id);
        if (sourceById.isPresent()) {
            return ResponseEntity.ok(sourceById.get());
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<?> createSource(@RequestBody Source source) {
        return new ResponseEntity<>(sourceService.saveOrUpdateSource(source), HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<?> updateSource(@RequestBody Source source) {
        Optional<Source> sourceById = sourceService.getSourceById(source.getId());
        if (sourceById.isPresent()) {
            return new ResponseEntity<>(sourceService.saveOrUpdateSource(source), HttpStatus.CREATED);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deleteSource(@PathVariable Long id) {
        Optional<Source> sourceById = sourceService.getSourceById(id);
        if (sourceById.isPresent()) {
            sourceService.deleteSourceById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }
}
