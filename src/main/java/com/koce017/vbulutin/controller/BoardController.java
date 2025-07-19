package com.koce017.vbulutin.controller;

import com.koce017.vbulutin.data.dto.BoardDto;
import com.koce017.vbulutin.data.dto.BoardTreeNode;
import com.koce017.vbulutin.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{slug}/tree") // TODO allow only for board owners
    public List<BoardTreeNode> tree(@PathVariable String slug) {
        return boardService.tree(slug);
    }

    @PutMapping("/tree") // TODO allow only for board owners
    public void saveTree(@RequestBody List<BoardTreeNode> tree) {
        boardService.saveTree(tree);
    }
}
