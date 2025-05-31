package com.koce017.vbulutin.service.impl;

import com.koce017.vbulutin.data.dto.BoardDto;
import com.koce017.vbulutin.data.dto.UserDto;
import com.koce017.vbulutin.data.entity.Board;
import com.koce017.vbulutin.repository.BoardRepository;
import com.koce017.vbulutin.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                .categories(board.getCategories().stream().map(categoryServiceImpl::toCategoryDto).toList())
                .build();
    }

}
