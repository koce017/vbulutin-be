package com.koce017.vbulutin.controller;

import com.koce017.vbulutin.data.dto.ForumDto;
import com.koce017.vbulutin.service.ForumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forum")
@RequiredArgsConstructor
public class ForumController {

    private final ForumService forumService;

    @GetMapping("/{slug}")
    public ForumDto findBySlug(@PathVariable String slug) {
        return forumService.findBySlug(slug);
    }

    @PostMapping
    public void create(@RequestBody ForumDto forumDto) {
        forumService.create(forumDto);
    }

}
