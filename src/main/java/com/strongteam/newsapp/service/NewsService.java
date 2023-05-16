package com.strongteam.newsapp.service;

import com.strongteam.newsapp.entity.News;
import com.strongteam.newsapp.payload.NewsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.Optional;

public interface NewsService {
    Page<News> getAllNews(Pageable pageable);
    Optional<News> getNewsById(Long id);
    News saveNews(NewsDTO newsDTO) throws ParseException;
    News updateNews(News news,NewsDTO newsDTO) throws ParseException;
    void deleteNewsById(Long id);
    Page<News> findBySourceId(Long sourceId, Pageable pageable);
    Page<News> findByTopicId(Long topicId, Pageable pageable);
}
