package com.config.miniproject.service.serviceImpl;

import com.config.miniproject.exception.ForbiddenException;
import com.config.miniproject.exception.NotFoundException;
import com.config.miniproject.model.dto.request.CategoryRequest;
import com.config.miniproject.model.dto.response.CategoryResponse;
import com.config.miniproject.model.entity.AppUser;
import com.config.miniproject.model.entity.Category;
import com.config.miniproject.repository.AppUserRepository;
import com.config.miniproject.repository.CategoryRepository;
import com.config.miniproject.service.CategoryService;
import com.config.miniproject.utils.GetCurrentUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final AppUserRepository appUserRepository;
    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {

        Integer userId = GetCurrentUser.userId();
        AppUser user = appUserRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found."));

        if (user.getRole().equalsIgnoreCase("READER")) {
            throw new ForbiddenException("You don't have permission");
        }

        return categoryRepository.save(categoryRequest.toEntity()).toResponse();
    }

    @Override
    public List<CategoryResponse> getAllCategory(Sort.Direction orderBy, String sortBy, Integer page, Integer size) {
        Sort sort = Sort.by(orderBy, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        return categoryPage.getContent().stream().map(Category::toResponse).toList();
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        Integer userId = GetCurrentUser.userId();
        AppUser user = appUserRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found."));

        if (user.getRole().equalsIgnoreCase("READER")) {
            throw new ForbiddenException("You don't have permission");
        }
        return categoryRepository.findById(id).orElseThrow().toResponse();
    }

    @Override
    public CategoryResponse updateCategory(Integer id, CategoryRequest categoryRequest) {
        Category editCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        editCategory.setCategoryName(categoryRequest.getCategoryName());

        Category updatedCategory = categoryRepository.save(editCategory);

        return updatedCategory.toResponse();
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }
}
