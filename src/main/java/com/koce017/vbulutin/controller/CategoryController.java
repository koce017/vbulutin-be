package com.koce017.vbulutin.controller;

import com.koce017.vbulutin.data.dto.CategoryDto;
import com.koce017.vbulutin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{slug}")
    public CategoryDto findBySlug(@PathVariable String slug) {
        return categoryService.findBySlug(slug);
    }

}
