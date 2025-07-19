package com.koce017.vbulutin.service.impl;

import com.koce017.vbulutin.data.dto.BoardDto;
import com.koce017.vbulutin.data.dto.BoardTreeNode;
import com.koce017.vbulutin.data.dto.UserDto;
import com.koce017.vbulutin.data.entity.Board;
import com.koce017.vbulutin.data.entity.Category;
import com.koce017.vbulutin.data.entity.Forum;
import com.koce017.vbulutin.repository.BoardRepository;
import com.koce017.vbulutin.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final CategoryServiceImpl categoryServiceImpl;

    @Override
    public List<BoardDto> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Board> boards = boardRepository.findByOwnerUsernameOrIsHiddenFalseOrderByOwnerUsernameAscTitleAsc(authentication.getName());
        return boards.stream().map(board ->
                BoardDto.builder()
                        .slug(board.getSlug())
                        .title(board.getTitle())
                        .description(board.getDescription())
                        .isHidden(board.getIsHidden())
                        .owner(UserDto.builder().username(board.getOwner().getUsername()).build())
                        .build()
        ).toList();
    }

    @Override
    public BoardDto findBySlug(String slug) {
        Board board = boardRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Board " + slug + " does not exist."));

        return BoardDto.builder()
                .title(board.getTitle())
                .slug(board.getSlug())
                .description(board.getDescription())
                .isHidden(board.getIsHidden())
                .owner(UserDto.builder().username(board.getOwner().getUsername()).build())
                .categories(board.getCategories().stream().map(categoryServiceImpl::toCategoryDto).toList())
                .build();
    }

    @Override
    public List<BoardTreeNode> tree(String slug) {
        Board board = boardRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Board " + slug + " does not exist."));

        List<BoardTreeNode> tree = new ArrayList<>();

        for (Category category : board.getCategories()) {
            BoardTreeNode categoryNode = BoardTreeNode.builder()
                    .id(category.getSlug())
                    .text(category.getTitle())
                    .children(new ArrayList<>())
                    .build();
            tree.add(categoryNode);

            for (Forum forum : category.getForums()) {

                BoardTreeNode forumNode = BoardTreeNode.builder()
                        .id(forum.getSlug())
                        .text(forum.getTitle())
                        .children(new ArrayList<>())
                        .build();

                if (forum.getParent() == null) {
                    categoryNode.getChildren().add(forumNode);
                }

                for (Forum childForum : forum.getChildren()) {
                    forumNode.getChildren().add(BoardTreeNode.builder()
                            .id(childForum.getSlug())
                            .text(childForum.getTitle())
                            .children(new ArrayList<>())
                            .build());
                }

            }

        }

        return tree;
    }

}
