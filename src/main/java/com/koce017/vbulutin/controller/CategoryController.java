package com.koce017.vbulutin.controller;

import com.koce017.vbulutin.data.dto.CategoryDto;
import com.koce017.vbulutin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{slug}")
    public CategoryDto findBySlug(@PathVariable String slug) {
        return categoryService.findBySlug(slug);
    }

    @PostMapping
    public void create(@RequestBody CategoryDto categoryDto) {
        categoryService.create(categoryDto);
    }

}
