package com.koce017.vbulutin.controller;

import com.koce017.vbulutin.data.dto.PostDto;
import com.koce017.vbulutin.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostDto create(@RequestBody @Valid PostDto postDto) {
        return postService.create(postDto);
    }

}
