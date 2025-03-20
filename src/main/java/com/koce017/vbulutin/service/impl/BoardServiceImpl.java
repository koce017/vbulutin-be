package com.koce017.vbulutin.service.impl;

import com.koce017.vbulutin.data.dto.BoardDTO;
import com.koce017.vbulutin.data.entity.Board;
import com.koce017.vbulutin.repository.BoardRepository;
import com.koce017.vbulutin.service.BoardService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final CategoryServiceImpl categoryServiceImpl;

    public BoardServiceImpl(BoardRepository boardRepository, CategoryServiceImpl categoryServiceImpl) {
        this.boardRepository = boardRepository;
        this.categoryServiceImpl = categoryServiceImpl;
    }

    @Override
    public List<BoardDTO> findAll() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream().map(board -> {
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setId(board.getId());
            boardDTO.setSlug(board.getSlug());
            boardDTO.setTitle(board.getTitle());
            boardDTO.setDescription(board.getDescription());
            boardDTO.setVisible(board.isVisible());
            return boardDTO;
        }).toList();
    }

    @Override
    public BoardDTO findBySlug(String slug) {
        Board board = boardRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Board " + slug + " does not exist."));

        return BoardDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .slug(board.getSlug())
                .description(board.getDescription())
                .isVisible(board.isVisible())
                .categories(board.getCategories().stream().map(categoryServiceImpl::toCategoryDTO).toList())
                .build();
    }

}
