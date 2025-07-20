package com.koce017.vbulutin.service;

import com.koce017.vbulutin.data.dto.CategoryDto;

public interface CategoryService {
    CategoryDto findBySlug(String slug);
    void create(CategoryDto categoryDto);
}
