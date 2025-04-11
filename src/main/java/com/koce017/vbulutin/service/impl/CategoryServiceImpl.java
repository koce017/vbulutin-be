package com.koce017.vbulutin.service.impl;

import com.koce017.vbulutin.data.dto.BoardDTO;
import com.koce017.vbulutin.data.dto.CategoryDTO;
import com.koce017.vbulutin.data.dto.ForumDTO;
import com.koce017.vbulutin.data.entity.Category;
import com.koce017.vbulutin.data.entity.Forum;
import com.koce017.vbulutin.repository.CategoryRepository;
import com.koce017.vbulutin.service.CategoryService;
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

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDTO findBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Category " + slug + " does not exist."));
        return toCategoryDTO(category);
    }

    public CategoryDTO toCategoryDTO(Category category) {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(category.getId())
                .title(category.getTitle())
                .slug(category.getSlug())
                .description(category.getDescription())
                .forums(new ArrayList<>())
                .board(BoardDTO.builder()
                        .id(category.getBoard().getId())
                        .slug(category.getBoard().getSlug())
                        .title(category.getBoard().getTitle())
                        .build()
                )
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

    private ForumDTO toForumDTO(Forum forum) {
        return ForumDTO.builder()
                .id(forum.getId())
                .title(forum.getTitle())
                .slug(forum.getSlug())
                .description(forum.getDescription())
                .isLocked(forum.getIsLocked())
                .children(new ArrayList<>())
                .build();
    }

}
