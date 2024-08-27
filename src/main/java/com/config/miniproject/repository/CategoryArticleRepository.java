package com.config.miniproject.repository;

import com.config.miniproject.model.entity.Article;
import com.config.miniproject.model.entity.CategoryArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


public interface CategoryArticleRepository extends JpaRepository<CategoryArticle, Integer> {
    List<CategoryArticle> findAllByArticleId(Integer articleId);

    @Transactional
    void deleteAllByArticle(Article article);

    List<CategoryArticle> findAllByCategoryId(Integer categoryId);
    Integer countAllByCategoryId(Integer categoryId);
}
