package com.koce017.vbulutin.service;

import com.koce017.vbulutin.data.dto.BoardDTO;

import java.util.List;

public interface BoardService {
    List<BoardDTO> findAll();
    BoardDTO findBoardHome(String slug);
}
