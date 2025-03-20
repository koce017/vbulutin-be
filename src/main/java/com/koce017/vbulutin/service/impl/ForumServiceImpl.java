package com.koce017.vbulutin.service.impl;

import com.koce017.vbulutin.data.dto.BoardDTO;
import com.koce017.vbulutin.data.dto.CategoryDTO;
import com.koce017.vbulutin.data.dto.ForumDTO;
import com.koce017.vbulutin.data.dto.TopicDTO;
import com.koce017.vbulutin.data.entity.Category;
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

        Category category = forum.getCategory();

        ForumDTO forumDTO = ForumDTO.builder()
                .id(forum.getId())
                .title(forum.getTitle())
                .slug(forum.getSlug())
                .description(forum.getDescription())
                .position(forum.getPosition())
                .isLocked(forum.isLocked())
                .category(
                        CategoryDTO.builder()
                                .id(category.getId())
                                .slug(category.getSlug())
                                .title(category.getTitle())
                                .board(BoardDTO.builder()
                                        .id(category.getBoard().getId())
                                        .title(category.getBoard().getTitle())
                                        .slug(category.getBoard().getSlug())
                                        .build()
                                ).build()
                )
                .children(forum.getChildren().stream().map(child ->
                                        ForumDTO.builder()
                                                .id(child.getId())
                                                .title(child.getTitle())
                                                .slug(child.getSlug())
                                                .build()
                                ).toList()
                )
                .topics(forum.getTopics().stream().map(topic ->
                        TopicDTO.builder()
                                .id(topic.getId())
                                .title(topic.getTitle())
                                .slug(topic.getSlug())
                                .build()).toList()
                ).build();

        if (forum.getParent() != null) {
            forumDTO.setParent(ForumDTO.builder()
                            .id(forum.getParent().getId())
                            .title(forum.getParent().getTitle())
                            .slug(forum.getParent().getSlug()
                            ).build()
            );
        }

        return forumDTO;
    }
}
