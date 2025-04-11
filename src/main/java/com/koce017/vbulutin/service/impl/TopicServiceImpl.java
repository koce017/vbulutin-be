package com.koce017.vbulutin.service.impl;

import com.koce017.vbulutin.data.dto.*;
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
        Topic topic = topicRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Topic " + slug + " does not exist."));

        ForumDTO forumDTO = ForumDTO.builder()
                .slug(topic.getForum().getSlug())
                .title(topic.getForum().getTitle())
                .category(CategoryDTO.builder()
                        .slug(topic.getForum().getCategory().getSlug())
                        .title(topic.getForum().getCategory().getTitle())
                        .board(BoardDTO.builder()
                                .slug(topic.getForum().getCategory().getBoard().getSlug())
                                .title(topic.getForum().getCategory().getBoard().getTitle())
                                .build())
                        .build())
                .build();

        if (topic.getForum().getParent() != null) {
            forumDTO.setParent(ForumDTO.builder()
                    .slug(topic.getForum().getParent().getSlug())
                    .title(topic.getForum().getParent().getTitle())
                    .build()
            );
        }

        TopicDTO topicDTO = TopicDTO.builder()
                .id(topic.getId())
                .forum(forumDTO)
                .title(topic.getTitle())
                .slug(topic.getSlug())
                .isLocked(topic.getIsLocked())
                .posts(topic.getPosts().stream()
                        .map(this::toDto)
                        .toList()
                ).build();

        if (topic.getSolution() != null) {
            topicDTO.setSolution(toDto(topic.getSolution()));
        }

        return topicDTO;
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
