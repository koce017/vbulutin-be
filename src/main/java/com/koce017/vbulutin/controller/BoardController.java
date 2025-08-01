package com.koce017.vbulutin.controller;

import com.koce017.vbulutin.data.dto.BoardDto;
import com.koce017.vbulutin.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public List<BoardDto> findAll() {
        return boardService.findAll();
    }

    @GetMapping("/{slug}")
    public BoardDto findBySlug(@PathVariable String slug) {
        return boardService.findBySlug(slug);
    }
}
