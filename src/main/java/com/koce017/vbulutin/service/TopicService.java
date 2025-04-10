package com.koce017.vbulutin.service;

import com.koce017.vbulutin.data.dto.TopicDTO;

public interface TopicService {
    TopicDTO findBySlug(String slug);
}
