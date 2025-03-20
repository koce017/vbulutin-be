package com.koce017.vbulutin.service;

import com.koce017.vbulutin.data.dto.CategoryDTO;

public interface CategoryService {
    CategoryDTO findBySlug(String slug);
}
