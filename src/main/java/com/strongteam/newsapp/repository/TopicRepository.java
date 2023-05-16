package com.strongteam.newsapp.repository;

import com.strongteam.newsapp.entity.Role;
import com.strongteam.newsapp.entity.Source;
import com.strongteam.newsapp.entity.Topic;
import com.strongteam.newsapp.entity.enums.ERole;
import com.strongteam.newsapp.entity.enums.ETopic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    Optional<Topic> findByTopicType(ETopic topicType);
}
