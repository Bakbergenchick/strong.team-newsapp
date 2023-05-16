package com.strongteam.newsapp.service.impls;

import com.strongteam.newsapp.entity.Source;
import com.strongteam.newsapp.entity.Topic;
import com.strongteam.newsapp.repository.TopicRepository;
import com.strongteam.newsapp.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @Override
    public Optional<Topic> getTopicById(Long id) {
        return topicRepository.findById(id);
    }

    @Override
    public Topic saveOrUpdateTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public void deleteTopicById(Long id) {
        topicRepository.deleteById(id);
    }

}
