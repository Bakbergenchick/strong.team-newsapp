package com.strongteam.newsapp.service.impls;

import com.strongteam.newsapp.entity.News;
import com.strongteam.newsapp.entity.Role;
import com.strongteam.newsapp.entity.Source;
import com.strongteam.newsapp.entity.Topic;
import com.strongteam.newsapp.entity.enums.ERole;
import com.strongteam.newsapp.entity.enums.ETopic;
import com.strongteam.newsapp.exception.domain.TopicNotFoundException;
import com.strongteam.newsapp.payload.NewsDTO;
import com.strongteam.newsapp.repository.NewsRepository;
import com.strongteam.newsapp.repository.SourceRepository;
import com.strongteam.newsapp.repository.TopicRepository;
import com.strongteam.newsapp.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final SourceRepository sourceRepository;
    private final TopicRepository topicRepository;

    @Override
    public Page<News> getAllNews(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }

    @Override
    public Optional<News> getNewsById(Long id) {
        return newsRepository.findById(id);
    }

    @Override
    public News saveNews(NewsDTO newsDTO) throws ParseException {
        News news = new News();
        return newsRepository.save(mapperFromDTO(news, newsDTO));
    }


    @Override
    public News updateNews(News news, NewsDTO newsDTO) throws ParseException {
        return newsRepository.save(mapperFromDTO(news, newsDTO));
    }

    @Override
    public void deleteNewsById(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public Page<News> findBySourceId(Long sourceId, Pageable pageable) {
        return newsRepository.findBySourceId(sourceId, pageable);
    }

    @Override
    public Page<News> findByTopicId(Long topicId, Pageable pageable) {
        return newsRepository.findByTopicsId(topicId, pageable);
    }

    public News mapperFromDTO(News news, NewsDTO newsDTO) throws ParseException {
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        if (!newsDTO.getPublishedAt().isEmpty()) {
            news.setPublishedAt(new SimpleDateFormat("yyyy-MM-dd")
                    .parse(newsDTO.getPublishedAt()));
        }
        Optional<Source> sourceDTO = sourceRepository.findById(newsDTO.getSourceId());
        sourceDTO.ifPresent(news::setSource);
        Set<String> topics = newsDTO.getTopics();
        Set<Topic> topicSet = new HashSet<>();
        topics.forEach(t -> {
            switch (t) {
                case "business" -> {
                    Topic bisTopic = null;
                    try {
                        bisTopic = topicRepository
                                .findByTopicType(ETopic.Business)
                                .orElseThrow(() -> new TopicNotFoundException("Error, Topic is not found"));
                    } catch (TopicNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    topicSet.add(bisTopic);
                }
                case "policy" -> {
                    Topic polTopic = null;
                    try {
                        polTopic = topicRepository
                                .findByTopicType(ETopic.Politics)
                                .orElseThrow(() -> new TopicNotFoundException("Error, Topic is not found"));
                    } catch (TopicNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    topicSet.add(polTopic);
                }
                case "sport" -> {
                    Topic polTopic = null;
                    try {
                        polTopic = topicRepository
                                .findByTopicType(ETopic.Sport)
                                .orElseThrow(() -> new TopicNotFoundException("Error, Topic is not found"));
                    } catch (TopicNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    topicSet.add(polTopic);
                }
                case "tech" -> {
                    Topic polTopic = null;
                    try {
                        polTopic = topicRepository
                                .findByTopicType(ETopic.Technology)
                                .orElseThrow(() -> new TopicNotFoundException("Error, Topic is not found"));
                    } catch (TopicNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    topicSet.add(polTopic);
                }
            }
        });
        news.setTopics(topicSet);

        return news;
    }

}
