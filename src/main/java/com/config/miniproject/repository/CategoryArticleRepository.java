package com.config.miniproject.repository;

import com.config.miniproject.model.entity.CategoryArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryArticleRepository extends JpaRepository<CategoryArticle, Integer> {
}
