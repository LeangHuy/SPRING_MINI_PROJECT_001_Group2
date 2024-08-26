package com.config.miniproject.service.serviceImpl;

import com.config.miniproject.repository.ArticleRepository;
import com.config.miniproject.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
}
