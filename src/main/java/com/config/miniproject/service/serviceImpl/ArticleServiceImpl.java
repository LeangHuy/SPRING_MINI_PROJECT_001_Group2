package com.config.miniproject.service.serviceImpl;

import com.config.miniproject.exception.ForbiddenException;
import com.config.miniproject.exception.NotFoundException;
import com.config.miniproject.model.dto.request.ArticleRequest;
import com.config.miniproject.model.dto.response.ArticleResponse;
import com.config.miniproject.model.entity.AppUser;
import com.config.miniproject.model.entity.Article;
import com.config.miniproject.model.entity.Category;
import com.config.miniproject.model.entity.CategoryArticle;
import com.config.miniproject.repository.AppUserRepository;
import com.config.miniproject.repository.ArticleRepository;
import com.config.miniproject.repository.CategoryArticleRepository;
import com.config.miniproject.repository.CategoryRepository;
import com.config.miniproject.service.ArticleService;
import com.config.miniproject.utils.GetCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final AppUserRepository appUserRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryArticleRepository categoryArticleRepository;
    @Override
    public ArticleResponse createArticle(ArticleRequest articleRequest) {
        Integer userId = GetCurrentUser.userId();
        AppUser user = appUserRepository.findById(userId).orElseThrow(()->new NotFoundException("User Not Found."));
        if (user.getRole().equalsIgnoreCase("READER")){
            throw new ForbiddenException("You are not allowed to add articles.");
        }
        List<Integer> categoryIdList = articleRequest.getCategoryId();
        Article article = articleRepository.save(articleRequest.toEntity(user));

        for (Integer categoryId : categoryIdList) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(()->new NotFoundException("Category Not Found."));
            CategoryArticle categoryArticle= new CategoryArticle(null, LocalDateTime.now(), LocalDateTime.now(),article,category);
            categoryArticleRepository.save(categoryArticle);
        }
        return article.toResponse();
    }
}
