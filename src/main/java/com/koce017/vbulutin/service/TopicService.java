package com.koce017.vbulutin.service;

import com.koce017.vbulutin.data.dto.TopicDto;

public interface TopicService {
    TopicDto findBySlug(String slug);
    void create(Long forumId, String title, String content, Long posterId);
}
