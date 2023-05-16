package com.strongteam.newsapp.repository;

import com.strongteam.newsapp.entity.News;
import com.strongteam.newsapp.entity.Source;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<News> findByTitle(String title);
    Page<News> findBySourceId(Long sourceId, Pageable pageable);
    Page<News> findByTopicsId(Long sourceId, Pageable pageable);
    Long countBySource(Source source);
}
