package com.config.miniproject.service;

import com.config.miniproject.model.dto.request.ArticleRequest;
import com.config.miniproject.model.dto.response.ArticleResponse;

public interface ArticleService {

    ArticleResponse createArticle(ArticleRequest articleRequest);
}
