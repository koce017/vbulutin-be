package com.koce017.vbulutin.service.impl;

import com.koce017.vbulutin.data.dto.PostDTO;
import com.koce017.vbulutin.data.dto.TopicDTO;
import com.koce017.vbulutin.data.dto.UserDTO;
import com.koce017.vbulutin.data.entity.Post;
import com.koce017.vbulutin.data.entity.Topic;
import com.koce017.vbulutin.repository.TopicRepository;
import com.koce017.vbulutin.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    @Override
    public TopicDTO findBySlug(String slug) {
        Topic topic = topicRepository.findBySlugAndDeletedAtIsNull(slug)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Topic " + slug + " does not exist."));  // TODO: admins should be able to see deleted items

        return TopicDTO.builder()
                .id(topic.getId())
                .title(topic.getTitle())
                .slug(topic.getSlug())
                .isLocked(topic.isLocked())
                .solution(toDto(topic.getSolution()))
                .posts(topic.getPosts().stream()
                        .filter(post -> post.getDeletedAt() == null) // TODO: admins should be able to see deleted items
                        .map(this::toDto)
                        .toList()
                ).build();
    }

    private PostDTO toDto(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .content(post.getContent())
                .user(UserDTO.builder()
                        .id(post.getUser().getId())
                        .username(post.getUser().getUsername())
                        .build())
                .createdAt(post.getCreatedAt())
                .build();
    }

}
