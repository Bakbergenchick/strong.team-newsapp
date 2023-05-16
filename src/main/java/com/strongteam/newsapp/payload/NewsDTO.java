package com.strongteam.newsapp.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class NewsDTO {
    private String title;
    private String content;
    private String publishedAt;
    private Long sourceId;
    private Set<String> topics;
}
