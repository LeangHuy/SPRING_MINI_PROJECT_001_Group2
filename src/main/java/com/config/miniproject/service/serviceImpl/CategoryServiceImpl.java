package com.config.miniproject.service.serviceImpl;

import com.config.miniproject.exception.BadRequestException;
import com.config.miniproject.exception.ConflictException;
import com.config.miniproject.exception.NotFoundException;
import com.config.miniproject.model.dto.request.CategoryRequest;
import com.config.miniproject.model.dto.response.ArticleResponse;
import com.config.miniproject.model.dto.response.CategoryResponse;
import com.config.miniproject.model.entity.AppUser;
import com.config.miniproject.model.entity.Category;
import com.config.miniproject.model.entity.CategoryArticle;
import com.config.miniproject.repository.AppUserRepository;
import com.config.miniproject.repository.CategoryArticleRepository;
import com.config.miniproject.repository.CategoryRepository;
import com.config.miniproject.service.CategoryService;
import com.config.miniproject.utils.GetCurrentUser;
import com.config.miniproject.utils.UserUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final AppUserRepository appUserRepository;
    private final CategoryArticleRepository categoryArticleRepository;
    private final ArticleServiceImpl articleServiceImpl;

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Integer userId = GetCurrentUser.userId();
        UserUtils userUtils = new UserUtils(appUserRepository);
        AppUser user = userUtils.getCurrentUserAndCheckRole("READER", "You are not allowed to create category.");
        List<Category> findCategoryByAuthorId = categoryRepository.findAllByUserId(userId);
        for (Category category : findCategoryByAuthorId) {
            if (category.getCategoryName().equalsIgnoreCase(categoryRequest.getCategoryName())) {
                throw new ConflictException("Category name already exist.");
            }
        }
        CategoryResponse categoryResponse = categoryRepository.save(categoryRequest.toEntity(user)).toResponse(null);
        categoryResponse.setCreatedAt(LocalDateTime.now());
        return categoryResponse;
    }

    @Override
    public List<CategoryResponse> getAllCategory(Sort.Direction orderBy, String sortBy, Integer page, Integer size) {
        Integer userId = GetCurrentUser.userId();
        Sort sort = Sort.by(orderBy, sortBy);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Category> categoryPage = categoryRepository.findAllByUserId(pageable, userId);
        UserUtils userUtils = new UserUtils(appUserRepository);
        userUtils.getCurrentUserAndCheckRole("READER", "You are not allowed to get all categories.");
        List<Category> categoryList = categoryPage.getContent();
        List<CategoryResponse> categoryResponseList = new ArrayList<>();
        ArticleResponse articleResponse = null;
        for (Category category : categoryList) {
            List<CategoryArticle> categoryArticleList = categoryArticleRepository.findAllByCategoryId(category.getId());
            for (CategoryArticle categoryArticle : categoryArticleList) {
                articleResponse = articleServiceImpl.getArticleById(categoryArticle.getArticle().getId());
            }
            CategoryResponse categoryResponse = category.toResponse(articleResponse);
            categoryResponseList.add(categoryResponse);
        }
        return categoryResponseList;
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        Integer userId = GetCurrentUser.userId();
        UserUtils userUtils = new UserUtils(appUserRepository);
        userUtils.getCurrentUserAndCheckRole("READER", "You are not allowed to get category by id.");

        Category category = categoryRepository.findByIdAndUserId(id, userId);
        if (category == null) {
            throw new NotFoundException("Category id " + id + " not found.");
        }
        List<CategoryArticle> categoryArticleList = categoryArticleRepository.findAllByCategoryId(id);
        ArticleResponse articleResponse = null;
        for (CategoryArticle categoryArticle : categoryArticleList) {
            articleResponse = articleServiceImpl.getArticleById(categoryArticle.getArticle().getId());
        }
        return category.toResponse(articleResponse);
    }

    @Override
    public CategoryResponse updateCategory(Integer id, CategoryRequest categoryRequest) {
        Integer userId = GetCurrentUser.userId();
        UserUtils userUtils = new UserUtils(appUserRepository);
        userUtils.getCurrentUserAndCheckRole("READER", "You are not allowed to update category.");

        Category editCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category id " + id + " not found"));
        List<Category> findCategoryByAuthorId = categoryRepository.findAllByUserId(userId);
        if (findCategoryByAuthorId.isEmpty()) {
            throw new BadRequestException("Cannot delete/update not found article id " + id);
        }

        editCategory.setCategoryName(categoryRequest.getCategoryName());

        Category updatedCategory = categoryRepository.save(editCategory);
        List<CategoryArticle> categoryArticleList = categoryArticleRepository.findAllByCategoryId(id);
        ArticleResponse articleResponse = null;
        for (CategoryArticle categoryArticle : categoryArticleList) {
            articleResponse = articleServiceImpl.getArticleById(categoryArticle.getArticle().getId());
        }
        return updatedCategory.toResponse(articleResponse);
    }

    @Override
    public void deleteCategory(Integer id) {
        Integer userId = GetCurrentUser.userId();
        UserUtils userUtils = new UserUtils(appUserRepository);
        userUtils.getCurrentUserAndCheckRole("READER", "You are not allowed to delete category.");

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category " + id + " not found"));
        List<Category> findCategoryByAuthorId = categoryRepository.findAllByUserId(userId);
        if (findCategoryByAuthorId.isEmpty()) {
            throw new BadRequestException("Cannot delete/update not found article id " + id);
        }
        categoryRepository.delete(category);
    }
}