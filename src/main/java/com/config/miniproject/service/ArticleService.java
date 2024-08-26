package com.config.miniproject.service;

import com.config.miniproject.model.dto.request.ArticleRequest;
import com.config.miniproject.model.dto.response.ArticleResponse;
import com.config.miniproject.model.dto.response.ArticleWithCommentResponse;
import com.config.miniproject.model.enumaration.EArticle;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ArticleService {

    ArticleResponse createArticle(ArticleRequest articleRequest);
    List<ArticleWithCommentResponse> getAllArticles(int page, int size, EArticle sortBy, Sort.Direction sortDirection) ;
}
