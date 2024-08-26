package com.config.miniproject.service;

import com.config.miniproject.model.dto.request.CategoryRequest;
import com.config.miniproject.model.dto.response.CategoryResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);

    List<CategoryResponse> getAllCategory(Sort.Direction orderBy, String sortBy, Integer page, Integer size);

    CategoryResponse getCategoryById(Integer id);

    CategoryResponse updateCategory(Integer id, CategoryRequest categoryRequest);

    void deleteCategory(Integer id);
}
