package com.koce017.vbulutin.service.impl;

import com.koce017.vbulutin.data.dto.BoardDTO;
import com.koce017.vbulutin.data.entity.Board;
import com.koce017.vbulutin.repository.BoardRepository;
import com.koce017.vbulutin.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Override
    public List<BoardDTO> findAll() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream().map(board -> {
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setId(board.getId());
            boardDTO.setSlug(board.getSlug());
            boardDTO.setTitle(board.getTitle());
            boardDTO.setVisible(board.isVisible());
            return boardDTO;
        }).toList();
    }

}
