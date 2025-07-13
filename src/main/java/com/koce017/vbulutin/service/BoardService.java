package com.koce017.vbulutin.service;

import com.koce017.vbulutin.data.dto.BoardDto;
import com.koce017.vbulutin.data.dto.BoardTreeNode;

import java.util.List;

public interface BoardService {
    List<BoardDto> findAll();
    BoardDto findBySlug(String slug);
    List<BoardTreeNode> tree(String slug);
}
