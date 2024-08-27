package com.config.miniproject.repository;

import com.config.miniproject.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Page<Category> findAllByUserId(Pageable pageable, int userId);
    List<Category> findAllByUserId(int userId);
    Category findByIdAndUserId(int categoryId, int userId);
}
