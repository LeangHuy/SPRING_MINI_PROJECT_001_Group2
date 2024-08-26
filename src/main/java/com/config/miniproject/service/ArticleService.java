package com.config.miniproject.service;

import com.config.miniproject.model.dto.request.ArticleRequest;
import com.config.miniproject.model.dto.response.ArticleResponse;
import com.config.miniproject.model.enumaration.EArticle;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ArticleService {

    ArticleResponse createArticle(ArticleRequest articleRequest);
    List<ArticleResponse> getAllArticles(int page, int size, EArticle sortBy, Sort.Direction sortDirection) ;
    ArticleResponse updateArticleById(Integer articleId, ArticleRequest articleRequest);
    ArticleResponse getArticleById(Integer articleId);
    void deleteArticleById(Integer articleId);
}
