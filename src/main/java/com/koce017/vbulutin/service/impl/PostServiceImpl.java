package com.koce017.vbulutin.service.impl;

import com.koce017.vbulutin.data.dto.PostDto;
import com.koce017.vbulutin.data.entity.Post;
import com.koce017.vbulutin.data.entity.Topic;
import com.koce017.vbulutin.data.entity.User;
import com.koce017.vbulutin.repository.PostRepository;
import com.koce017.vbulutin.repository.TopicRepository;
import com.koce017.vbulutin.repository.UserRepository;
import com.koce017.vbulutin.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final TopicRepository topicRepository;

    @Override
    public PostDto create(PostDto postDto) {
        Topic topic = topicRepository.findById(postDto.getTopic().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Topic with id " + postDto.getTopic().getId() + " does not exist"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId = ((Jwt)authentication.getPrincipal()).getClaim("uid");

        User poster = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User with id " + postDto.getPoster().getId() + " does not exist"));

        Post post = Post.builder()
                .content(postDto.getContent())
                .topic(topic)
                .poster(poster)
                .createdAt(LocalDateTime.now()) // TODO sometimes posts appear out of order
                .build();

        Post saved = postRepository.save(post);

        topic.getPosts().add(post);
        topicRepository.save(topic);

        return PostDto.builder().id(saved.getId()).build();
    }

}
