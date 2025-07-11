package com.koce017.vbulutin.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.koce017.vbulutin.data.dto.TopicDto;
import com.koce017.vbulutin.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping("/{slug}")
    public TopicDto findBySlug(@PathVariable String slug) {
        return topicService.findBySlug(slug);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ObjectNode json) {
        Long forumId = json.get("forumId").asLong();
        String title = json.get("title").asText();
        String content = json.get("content").asText();
        Long posterId = json.get("posterId").asLong();
        topicService.create(forumId, title, content, posterId);
    }

}
