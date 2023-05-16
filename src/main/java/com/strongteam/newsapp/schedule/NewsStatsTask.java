package com.strongteam.newsapp.schedule;

import com.strongteam.newsapp.entity.Source;
import com.strongteam.newsapp.repository.NewsRepository;
import com.strongteam.newsapp.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NewsStatsTask {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private SourceRepository sourceRepository;

    // runs daily at midnight
    @Scheduled(cron = "0 0 0 * * *")
    public void generateStatsReport() {
        List<Source> sources = sourceRepository.findAll();
        Map<String, Long> stats = new ConcurrentHashMap<>();
        sources.parallelStream().forEach(source -> {
            Long count = newsRepository.countBySource(source);
            stats.put(source.getName(), count);
        });
        String report = formatReport(stats);
        saveReportToFile(report);
    }

    private String formatReport(Map<String, Long> stats) {
        StringBuilder sb = new StringBuilder();
        sb.append("News Source, News Count\n");
        stats.forEach((source, count) -> sb.append(source).append(",").append(count).append("\n"));
        return sb.toString();
    }

    private void saveReportToFile(String report) {
        try {
            String filename = "news-stats-" + LocalDate.now() + ".csv";
            Files.write(Paths.get(filename), report.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
