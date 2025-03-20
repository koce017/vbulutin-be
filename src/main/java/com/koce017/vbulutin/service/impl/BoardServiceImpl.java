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

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(board.getId());
        boardDTO.setSlug(board.getSlug());
        boardDTO.setTitle(board.getTitle());
        boardDTO.setDescription(board.getDescription());
        boardDTO.setVisible(board.isVisible());
        boardDTO.setCategories(
                board.getCategories().stream().map(category ->
                {
                    CategoryDTO categoryDTO = new CategoryDTO();
                    categoryDTO.setId(category.getId());
                    categoryDTO.setTitle(category.getTitle());
                    categoryDTO.setSlug(category.getSlug());

                    categoryDTO.setForums(category.getForums().stream().map(forum ->
                    {
                        ForumDTO forumDTO = new ForumDTO();
                        forumDTO.setId(forum.getId());
                        forumDTO.setTitle(forum.getTitle());
                        forumDTO.setSlug(forum.getSlug());
                        forumDTO.setLocked(forum.isLocked());

                        forumDTO.setChildren(
                                forum.getChildren().stream().map(child ->
                                {
                                    ForumDTO childDTO = new ForumDTO();
                                    childDTO.setId(forum.getId());
                                    childDTO.setTitle(forum.getTitle());
                                    childDTO.setSlug(forum.getSlug());
                                    return childDTO;
                                }).toList()
                        );

                        return forumDTO;

                    }).toList());

                    return categoryDTO;

                }).toList()
        );

        return boardDTO;
    }

}
