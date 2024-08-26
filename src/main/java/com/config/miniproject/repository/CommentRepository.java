package com.config.miniproject.repository;

import com.config.miniproject.model.entity.Article;
import com.config.miniproject.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByArticle(Article article);
}
