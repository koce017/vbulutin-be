package com.koce017.vbulutin.service.impl;

import com.koce017.vbulutin.data.dto.ForumDTO;
import com.koce017.vbulutin.data.entity.Forum;
import com.koce017.vbulutin.repository.ForumRepository;
import com.koce017.vbulutin.service.ForumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ForumServiceImpl implements ForumService {

    private final ForumRepository forumRepository;

    @Override
    public ForumDTO findBySlug(String slug) {
        Forum forum = forumRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Forum " + slug + " does not exist."));
        return null;
    }
}
