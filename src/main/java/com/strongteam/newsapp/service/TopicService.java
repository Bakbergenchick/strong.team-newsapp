package com.strongteam.newsapp.service;

import com.strongteam.newsapp.entity.Source;
import com.strongteam.newsapp.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TopicService {
    List<Topic> getAllTopics();
    Optional<Topic> getTopicById(Long id);
    Topic saveOrUpdateTopic(Topic topic);
    void deleteTopicById(Long id);
}
