package com.koce017.vbulutin.service;

import com.koce017.vbulutin.data.dto.ForumDto;

public interface ForumService {
    ForumDto findBySlug(String slug);
    void create(ForumDto forumDto);
}
