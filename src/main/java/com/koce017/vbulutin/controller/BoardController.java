package com.koce017.vbulutin.controller;

import com.koce017.vbulutin.data.dto.BoardDTO;
import com.koce017.vbulutin.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping
    public List<BoardDTO> findAll() {
        return boardService.findAll();
    }

}
