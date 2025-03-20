package com.koce017.vbulutin.service.impl;

import com.koce017.vbulutin.data.dto.BoardDTO;
import com.koce017.vbulutin.data.dto.CategoryDTO;
import com.koce017.vbulutin.data.dto.ForumDTO;
import com.koce017.vbulutin.data.entity.Board;
import com.koce017.vbulutin.data.entity.Forum;
import com.koce017.vbulutin.repository.BoardRepository;
import com.koce017.vbulutin.service.BoardService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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
        Board board = boardRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find board + " + slug));

        BoardDTO boardDTO = BoardDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .slug(board.getSlug())
                .description(board.getDescription())
                .isVisible(board.isVisible())
                .categories(new ArrayList<>())
                .build();

        boardDTO.setCategories(board.getCategories().stream()
                .map(category -> {
                    CategoryDTO categoryDTO = CategoryDTO.builder()
                            .id(category.getId())
                            .title(category.getTitle())
                            .slug(category.getSlug())
                            .forums(new ArrayList<>())
                            .build();

                    Map<Long, ForumDTO> rootForums = new HashMap<>();

                    for (Forum forum : category.getForums()) {
                        if (forum.getParent() == null) {
                            ForumDTO forumDTO = toForumDTO(forum);
                            categoryDTO.getForums().add(forumDTO);
                            rootForums.put(forumDTO.getId(), forumDTO);
                        }
                    }

                    for (Forum forum : category.getForums()) {
                        if (forum.getParent() != null) {
                           rootForums.get(forum.getParent().getId()).getChildren().add(toForumDTO(forum));
                        }
                    }

                    return categoryDTO;
                }
                ).toList());

        return boardDTO;
    }

    private ForumDTO toForumDTO(Forum forum) {
        return ForumDTO.builder()
                .id(forum.getId())
                .title(forum.getTitle())
                .slug(forum.getSlug())
                .isLocked(forum.isLocked())
                .children(new ArrayList<>())
                .build();
    }

}
