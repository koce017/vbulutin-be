package com.koce017.vbulutin.service;

import com.koce017.vbulutin.data.dto.BoardDto;

import java.util.List;

public interface BoardService {
    List<BoardDto> findAll();
    BoardDto findBySlug(String slug);
}
