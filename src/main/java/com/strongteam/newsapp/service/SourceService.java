package com.strongteam.newsapp.service;

import com.strongteam.newsapp.entity.News;
import com.strongteam.newsapp.entity.Source;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SourceService {
    List<Source> getAllSource();
    Optional<Source> getSourceById(Long id);
    Source saveOrUpdateSource(Source source);
    void deleteSourceById(Long id);
}
