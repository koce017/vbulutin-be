package com.koce017.vbulutin.service.impl;

import com.koce017.vbulutin.data.dto.*;
import com.koce017.vbulutin.data.entity.Forum;
import com.koce017.vbulutin.data.entity.Post;
import com.koce017.vbulutin.data.entity.Topic;
import com.koce017.vbulutin.data.entity.User;
import com.koce017.vbulutin.repository.PostRepository;
import com.koce017.vbulutin.repository.TopicRepository;
import com.koce017.vbulutin.service.TopicService;
import com.koce017.vbulutin.util.SlugifyUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final PostRepository postRepository;
    private final TopicRepository topicRepository;

    @Override
    public TopicDto findBySlug(String slug) {
        Topic topic = topicRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Topic " + slug + " does not exist."));

        ForumDto forumDto = ForumDto.builder()
                .slug(topic.getForum().getSlug())
                .title(topic.getForum().getTitle())
                .category(CategoryDto.builder()
                        .slug(topic.getForum().getCategory().getSlug())
                        .title(topic.getForum().getCategory().getTitle())
                        .board(BoardDto.builder()
                                .slug(topic.getForum().getCategory().getBoard().getSlug())
                                .title(topic.getForum().getCategory().getBoard().getTitle())
                                .isHidden(topic.getForum().getCategory().getBoard().getIsHidden())
                                .owner(UserDto.builder().username(topic.getForum().getCategory().getBoard().getOwner().getUsername()).build())
                                .build())
                        .build())
                .build();

        if (topic.getForum().getParent() != null) {
            forumDto.setParent(ForumDto.builder()
                    .slug(topic.getForum().getParent().getSlug())
                    .title(topic.getForum().getParent().getTitle())
                    .build()
            );
        }

        return TopicDto.builder()
                .id(topic.getId())
                .forum(forumDto)
                .title(topic.getTitle())
                .slug(topic.getSlug())
                .isLocked(topic.getIsLocked() || topic.getForum().getIsLocked())
                .posts(topic.getPosts().stream()
                        .map(post -> PostDto.builder()
                                .id(post.getId())
                                .content(post.getContent())
                                .poster(UserDto.builder()
                                        .username(post.getPoster().getUsername())
                                        .signature(post.getPoster().getSignature())
                                        .build())
                                .createdAt(post.getCreatedAt())
                                .build())
                        .toList()
                ).build();
    }

    @Override
    @Transactional
    public TopicDto create(Long forumId, String title, String content, Long posterId) {
        Post post = Post.builder()
                .content(content)
                .poster((User.builder().id(posterId).build()))
                .createdAt(LocalDateTime.now())
                .build();

        Topic topic = Topic.builder()
                .title(title)
                .forum(Forum.builder().id(forumId).build())
                .slug(SlugifyUtil.slugify(title))
                .build();

        post.setTopic(topic);
        topic.getPosts().add(post);

        postRepository.save(post);
        topicRepository.save(topic);

        return TopicDto.builder()
                .slug(topic.getSlug())
                .build();
    }

}
