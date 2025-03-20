package com.koce017.vbulutin.service;

import com.koce017.vbulutin.data.dto.ForumDTO;

public interface ForumService {
    ForumDTO findBySlug(String slug);
}
