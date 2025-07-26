package com.koce017.vbulutin.service.impl;

import com.koce017.vbulutin.data.dto.BoardDto;
import com.koce017.vbulutin.data.dto.CategoryDto;
import com.koce017.vbulutin.data.dto.ForumDto;
import com.koce017.vbulutin.data.dto.UserDto;
import com.koce017.vbulutin.data.entity.Board;
import com.koce017.vbulutin.data.entity.Category;
import com.koce017.vbulutin.data.entity.Forum;
import com.koce017.vbulutin.repository.BoardRepository;
import com.koce017.vbulutin.repository.CategoryRepository;
import com.koce017.vbulutin.repository.PostRepository;
import com.koce017.vbulutin.service.CategoryService;
import com.koce017.vbulutin.util.SlugifyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;

    private final ForumServiceImpl forumServiceImpl;

    @Override
    public CategoryDto findBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Category " + slug + " does not exist."));

        CategoryDto categoryDto = toCategoryDto(category);

        categoryDto.setBoard(
                BoardDto.builder()
                        .slug(category.getBoard().getSlug())
                        .title(category.getBoard().getTitle())
                        .isHidden(category.getBoard().getIsHidden())
                        .owner(UserDto.builder().username(category.getBoard().getOwner().getUsername()).build())
                        .build()
        );

        return categoryDto;
    }

    @Override
    public void create(CategoryDto categoryDto) {
        Board board = boardRepository.findBySlug(categoryDto.getBoard().getSlug())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Board " + categoryDto.getBoard().getSlug() + " does not exist."));

        Category category = Category.builder()
                .title(categoryDto.getTitle())
                .slug(SlugifyUtil.slugify(categoryDto.getTitle()))
                .description(categoryDto.getDescription())
                .position(categoryRepository.findMaxPositionByBoardId(board.getId()))
                .board(board)
                .build();

        categoryRepository.save(category);
    }

    public CategoryDto toCategoryDto(Category category) {
        CategoryDto categoryDto = CategoryDto.builder()
                .title(category.getTitle())
                .slug(category.getSlug())
                .description(category.getDescription())
                .forums(new ArrayList<>())
                .build();

        Map<Long, ForumDto> rootForums = new HashMap<>();

        for (Forum forum : category.getForums()) {
            if (forum.getParent() == null) {
                ForumDto forumDto = toForumDto(forum);
                categoryDto.getForums().add(forumDto);
                rootForums.put(forum.getId(), forumDto);
            }
        }

        for (Forum forum : category.getForums()) {
            if (forum.getParent() != null) {
                rootForums.get(forum.getParent().getId()).getChildren().add(toForumDto(forum));
            }
        }

        return categoryDto;
    }

    private ForumDto toForumDto(Forum forum) {
        ForumDto forumDto = ForumDto.builder()
                .title(forum.getTitle())
                .slug(forum.getSlug())
                .description(forum.getDescription())
                .isLocked(forum.getIsLocked())
                .children(new ArrayList<>())
                .build();

        postRepository.findFirstByTopicForumIdOrderByCreatedAtDesc(forum.getId())
                .ifPresent(post -> forumDto.setLastPost(forumServiceImpl.toLastPostDto(post)));

        return forumDto;
    }

}
