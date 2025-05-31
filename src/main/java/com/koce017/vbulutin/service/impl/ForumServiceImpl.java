package com.koce017.vbulutin.service.impl;

import com.koce017.vbulutin.data.dto.*;
import com.koce017.vbulutin.data.entity.Forum;
import com.koce017.vbulutin.data.entity.Post;
import com.koce017.vbulutin.data.entity.Topic;
import com.koce017.vbulutin.repository.ForumRepository;
import com.koce017.vbulutin.repository.PostRepository;
import com.koce017.vbulutin.repository.TopicRepository;
import com.koce017.vbulutin.service.ForumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ForumServiceImpl implements ForumService {

    private final ForumRepository forumRepository;
    private final TopicRepository topicRepository;
    private final PostRepository postRepository;

    @Override
    public ForumDto findBySlug(String slug) {
        Forum forum = forumRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Forum " + slug + " does not exist."));

        List<Topic> topics = topicRepository.findTopicsByForumOrderedByLastPost(forum.getId());

        Optional<Post> lastPost = postRepository.findFirstByTopicForumIdOrderByCreatedAtDesc(forum.getId());

        for (Forum childForum : forum.getChildren()) {
            Optional<Post> childForumLastPost = postRepository.findFirstByTopicForumIdOrderByCreatedAtDesc(childForum.getId());
            if (childForumLastPost.isPresent() && lastPost.isPresent()
                    && childForumLastPost.get().getCreatedAt().isAfter(lastPost.get().getCreatedAt())) {
                lastPost = childForumLastPost;
            }
        }

        ForumDto forumDto = ForumDto.builder()
                .title(forum.getTitle())
                .slug(forum.getSlug())
                .description(forum.getDescription())
                .isLocked(forum.getIsLocked())
                .category(
                        CategoryDto.builder()
                                .slug(forum.getCategory().getSlug())
                                .title(forum.getCategory().getTitle())
                                .board(BoardDto.builder()
                                        .title(forum.getCategory().getBoard().getTitle())
                                        .slug(forum.getCategory().getBoard().getSlug())
                                        .isHidden(forum.getCategory().getBoard().getIsHidden())
                                        .build()
                                ).build()
                )
                .children(forum.getChildren().stream().map(child -> {

                    ForumDto childForum = ForumDto.builder()
                            .title(child.getTitle())
                            .slug(child.getSlug())
                            .isLocked(child.getIsLocked())
                            .build();

                    postRepository.findFirstByTopicForumIdOrderByCreatedAtDesc(child.getId())
                            .ifPresent(post -> childForum.setLastPost(toLastPostDto(post)));

                    return childForum;

                }).toList())
                .topics(topics.stream().map(topic ->
                        TopicDto.builder()
                                .title(topic.getTitle())
                                .slug(topic.getSlug())
                                .isLocked(topic.getIsLocked() || forum.getIsLocked())
                                .lastPost(toLastPostDto(topic.getPosts().getLast()))
                                .build()).toList()
                ).build();

        if (forum.getParent() != null) {
            forumDto.setParent(ForumDto.builder()
                    .title(forum.getParent().getTitle())
                    .slug(forum.getParent().getSlug())
                    .build()
            );
        }

        return forumDto;
    }

    public PostDto toLastPostDto(Post post) {
        return PostDto.builder()
                .topic(TopicDto.builder()
                        .slug(post.getTopic().getSlug())
                        .title(post.getTopic().getTitle())
                        .build())
                .poster(UserDto.builder()
                        .username(post.getPoster().getUsername())
                        .build())
                .createdAt(post.getCreatedAt())
                .build();
    }

}
