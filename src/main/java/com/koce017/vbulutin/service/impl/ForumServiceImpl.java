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

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ForumServiceImpl implements ForumService {

    private final ForumRepository forumRepository;
    private final TopicRepository topicRepository;
    private final PostRepository postRepository;

    @Override
    public ForumDTO findBySlug(String slug) {
        Forum forum = forumRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Forum " + slug + " does not exist."));

        List<Topic> topics = topicRepository.findTopicsByForumOrderedByLastPost(forum.getId());

        Post lastPost = postRepository.findFirstByTopicForumIdOrderByCreatedAtDesc(forum.getId());

        for (Forum childForum : forum.getChildren()) {
            Post childForumLastPost = postRepository.findFirstByTopicForumIdOrderByCreatedAtDesc(childForum.getId());
            if (childForumLastPost.getCreatedAt().isAfter(lastPost.getCreatedAt()))
                lastPost = childForumLastPost;
        }

        ForumDTO forumDTO = ForumDTO.builder()
                .title(forum.getTitle())
                .slug(forum.getSlug())
                .description(forum.getDescription())
                .position(forum.getPosition())
                .isLocked(forum.getIsLocked())
                .lastPost(toLastPostDTO(lastPost))
                .category(
                        CategoryDTO.builder()
                                .slug(forum.getCategory().getSlug())
                                .title(forum.getCategory().getTitle())
                                .board(BoardDTO.builder()
                                        .title(forum.getCategory().getBoard().getTitle())
                                        .slug(forum.getCategory().getBoard().getSlug())
                                        .isHidden(forum.getCategory().getBoard().getIsHidden())
                                        .build()
                                ).build()
                )
                .children(forum.getChildren().stream().map(child ->
                                ForumDTO.builder()
                                        .title(child.getTitle())
                                        .slug(child.getSlug())
                                        .lastPost(toLastPostDTO(postRepository.findFirstByTopicForumIdOrderByCreatedAtDesc(child.getId())))
                                        .build()
                        ).toList()
                )
                .topics(topics.stream().map(topic ->
                        TopicDTO.builder()
                                .title(topic.getTitle())
                                .slug(topic.getSlug())
                                .lastPost(toLastPostDTO(topic.getPosts().getLast()))
                                .build()).toList()
                ).build();

        if (forum.getParent() != null) {
            forumDTO.setParent(ForumDTO.builder()
                    .title(forum.getParent().getTitle())
                    .slug(forum.getParent().getSlug())
                    .build()
            );
        }

        return forumDTO;
    }

    private PostDTO toLastPostDTO(Post post) {
        return PostDTO.builder()
                .topic(TopicDTO.builder()
                        .slug(post.getTopic().getSlug())
                        .title(post.getTopic().getTitle())
                        .build())
                .poster(UserDTO.builder()
                        .username(post.getPoster().getUsername())
                        .build())
                .createdAt(post.getCreatedAt())
                .build();
    }

}
