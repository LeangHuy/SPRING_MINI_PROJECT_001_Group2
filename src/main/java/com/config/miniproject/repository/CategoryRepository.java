package com.config.miniproject.repository;

import com.config.miniproject.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByCategoryName(String categoryName);
    List<Category> findAllByUserId(int userId);
}
