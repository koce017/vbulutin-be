package com.koce017.vbulutin.controller;

import com.koce017.vbulutin.data.dto.TopicDTO;
import com.koce017.vbulutin.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping("/{slug}")
    public TopicDTO findBySlug(@PathVariable String slug) {
        return topicService.findBySlug(slug);
    }

}
