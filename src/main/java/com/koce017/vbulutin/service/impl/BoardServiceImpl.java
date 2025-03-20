package com.koce017.vbulutin.service.impl;

import com.koce017.vbulutin.data.dto.BoardDTO;
import com.koce017.vbulutin.data.dto.CategoryDTO;
import com.koce017.vbulutin.data.dto.ForumDTO;
import com.koce017.vbulutin.data.entity.Board;
import com.koce017.vbulutin.repository.BoardRepository;
import com.koce017.vbulutin.service.BoardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
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
    public BoardDTO findBoardHome(String slug) {
        Board board = boardRepository.findBySlug(slug).orElseThrow();

        BoardDTO boardDTO = BoardDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .slug(board.getSlug())
                .description(board.getDescription())
                .isVisible(board.isVisible())
                .build();

        boardDTO.setCategories(
                board.getCategories().stream().map(category ->
                {
                    CategoryDTO categoryDTO = CategoryDTO.builder()
                            .id(category.getId())
                            .title(category.getTitle())
                            .slug(category.getSlug())
                            .build();

//                    categoryDTO.setForums(category.getForums().stream().map(forum ->
//                    {
//                        ForumDTO forumDTO = ForumDTO.builder()
//                                .id(forum.getId())
//                                .title(forum.getTitle())
//                                .slug(forum.getSlug())
//                                .isLocked(forum.isLocked())
//                                .position(forum.getPosition())
//                                .build();

//                        forumDTO.setChildren(
//                                forum.getChildren().stream().map(child ->
//                                        ForumDTO.builder()
//                                                .id(forum.getId())
//                                                .title(forum.getTitle())
//                                                .slug(forum.getSlug())
//                                                .position(forum.getPosition())
//                                                .build())
//                                        .toList()
//                        );

//                        return forumDTO;
//
//                    }).toList());

                    return categoryDTO;

                }).toList()
        );

        return boardDTO;
    }

}
