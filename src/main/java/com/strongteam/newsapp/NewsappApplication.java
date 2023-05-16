package com.strongteam.newsapp;

import com.strongteam.newsapp.entity.Role;
import com.strongteam.newsapp.entity.Source;
import com.strongteam.newsapp.entity.Topic;
import com.strongteam.newsapp.entity.enums.ERole;
import com.strongteam.newsapp.entity.enums.ETopic;
import com.strongteam.newsapp.repository.RoleRepository;
import com.strongteam.newsapp.repository.SourceRepository;
import com.strongteam.newsapp.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class NewsappApplication {
	private final RoleRepository roleRepository;
	private final TopicRepository topicRepository;
	private final SourceRepository sourceRepository;

	@PostConstruct
	public void initialInserts(){
		List<Role> roles = new ArrayList<>();
		roles.add(new Role(1L, ERole.ROLE_USER));
		roles.add(new Role(2L, ERole.ROLE_ADMIN));
		roles.add(new Role(3L, ERole.ROLE_MODERATOR));
		roleRepository.saveAll(roles);

		List<Topic> topics = new ArrayList<>();
		topics.add(new Topic(1L, ETopic.Politics));
		topics.add(new Topic(2L, ETopic.Sport));
		topics.add(new Topic(3L, ETopic.Technology));
		topics.add(new Topic(4L, ETopic.Business));
		topicRepository.saveAll(topics);

		List<Source> sources = new ArrayList<>();
		sources.add(new Source(1L, "Tengrinews", "https://tengrinews.kz/"));
		sources.add(new Source(2L, "Digitaltrends", "https://www.digitaltrends.com/"));
		sources.add(new Source(3L, "Vesti.kz", "https://vesti.kz/"));
		sourceRepository.saveAll(sources);

	}
	public static void main(String[] args) {
		SpringApplication.run(NewsappApplication.class, args);
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
