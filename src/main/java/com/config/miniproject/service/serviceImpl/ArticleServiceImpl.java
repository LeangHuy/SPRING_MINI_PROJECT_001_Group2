package com.config.miniproject.service.serviceImpl;

import com.config.miniproject.exception.ForbiddenException;
import com.config.miniproject.exception.NotFoundException;
import com.config.miniproject.model.dto.request.ArticleRequest;
import com.config.miniproject.model.dto.response.ArticleResponse;
import com.config.miniproject.model.dto.response.ArticleWithCommentResponse;
import com.config.miniproject.model.dto.response.CategoryArticleResponse;
import com.config.miniproject.model.entity.AppUser;
import com.config.miniproject.model.entity.Article;
import com.config.miniproject.model.entity.Category;
import com.config.miniproject.model.entity.CategoryArticle;
import com.config.miniproject.model.enumaration.EArticle;
import com.config.miniproject.repository.AppUserRepository;
import com.config.miniproject.repository.ArticleRepository;
import com.config.miniproject.repository.CategoryArticleRepository;
import com.config.miniproject.repository.CategoryRepository;
import com.config.miniproject.service.ArticleService;
import com.config.miniproject.utils.GetCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        checkRole(userId,"You are not allowed to add articles.");
        AppUser user = appUserRepository.findById(userId).orElseThrow(()->new NotFoundException("User Not Found."));
        List<Integer> categoryIdList = articleRequest.getCategoryId();
        Article article = articleRepository.save(articleRequest.toEntity(user));
        for (Integer categoryId : categoryIdList) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(()->new NotFoundException("Category id "+categoryId+" not found."));
            CategoryArticle categoryArticle= new CategoryArticle(null, LocalDateTime.now(), LocalDateTime.now(),article,category);
            categoryArticleRepository.save(categoryArticle);
        }
        return article.toResponse();
    }

    @Override
    public List<ArticleWithCommentResponse> getAllArticles(int page, int size, EArticle sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection,sortBy.name());
        Pageable pageable = PageRequest.of(page-1, size, sort);
        List<ArticleResponse> articles = articleRepository.findAll(pageable).getContent().stream().map(Article::toResponse).toList();
        List<ArticleWithCommentResponse> articleWithCommentResponseList = new ArrayList<>();
        for (ArticleResponse article : articles) {
            List<CategoryArticleResponse> categoryArticles = categoryArticleRepository.findAllByArticleId(article.getArticleId()).stream().map(CategoryArticle::toResponse).toList();
            ArticleWithCommentResponse articleWithCommentResponse = new ArticleWithCommentResponse();
            List<Integer> categoryIdList = categoryArticles.stream().map(CategoryArticleResponse::getCategoryId).toList();
            articleWithCommentResponse.setCategoryIdList(categoryIdList);
            articleWithCommentResponse.setArticleId(article.getArticleId());
            articleWithCommentResponse.setTitle(article.getTitle());
            articleWithCommentResponse.setDescription(article.getDescription());
            articleWithCommentResponse.setCreatedAt(article.getCreatedAt());
            articleWithCommentResponse.setOwnerOfArticle(article.getOwnerOfArticle());
            articleWithCommentResponseList.add(articleWithCommentResponse);
        }
        return articleWithCommentResponseList;
    }

    @Override
    public ArticleWithCommentResponse updateArticleById(Integer articleId, ArticleRequest articleRequest) {
        ArticleWithCommentResponse  articleWithCommentResponse =  new ArticleWithCommentResponse();
        Integer userId = GetCurrentUser.userId();
        AppUser user = appUserRepository.findById(userId).orElseThrow(()->new NotFoundException("User Not Found."));
        checkRole(userId,"You are not allowed to edit articles.");
        ArticleWithCommentResponse articleResponse = getArticleById(articleId);
        if (!articleResponse.getOwnerOfArticle().equals(userId)){
            throw new ForbiddenException("Cannot delete/update not found article id "+articleId);
        }
        Article article = articleRepository.save(articleRequest.toEntity(articleId,user,articleResponse.getCreatedAt()));
        categoryArticleRepository.deleteAllByArticle(article);
        for (Integer categoryId : articleRequest.getCategoryId()) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(()->new NotFoundException("Category id "+categoryId+" not found."));
            CategoryArticle categoryArticle= new CategoryArticle(null, LocalDateTime.now(), LocalDateTime.now(),article,category);
            categoryArticleRepository.save(categoryArticle);
        }
        ArticleResponse articleResponse1 = article.toResponse();
        List<CategoryArticleResponse> categoryArticles = categoryArticleRepository.findAllByArticleId(articleId).stream().map(CategoryArticle::toResponse).toList();
        List<Integer> categoryIdList = categoryArticles.stream().map(CategoryArticleResponse::getCategoryId).toList();
        articleWithCommentResponse.setCategoryIdList(categoryIdList);
        articleWithCommentResponse.setArticleId(article.getId());
        articleWithCommentResponse.setTitle(article.getTitle());
        articleWithCommentResponse.setDescription(article.getDescription());
        articleWithCommentResponse.setCreatedAt(article.getCreatedAt());
        articleWithCommentResponse.setOwnerOfArticle(articleResponse1.getOwnerOfArticle());
        return articleWithCommentResponse;
    }

    @Override
    public ArticleWithCommentResponse getArticleById(Integer articleId) {
        ArticleResponse articleResponse = articleRepository.findById(articleId).orElseThrow(()->new NotFoundException("Article id "+articleId+" not found.")).toResponse();
        List<CategoryArticleResponse> categoryArticles = categoryArticleRepository.findAllByArticleId(articleId).stream().map(CategoryArticle::toResponse).toList();
        ArticleWithCommentResponse articleWithCommentResponse = new ArticleWithCommentResponse();
        List<Integer> categoryIdList = categoryArticles.stream().map(CategoryArticleResponse::getCategoryId).toList();
        articleWithCommentResponse.setCategoryIdList(categoryIdList);
        articleWithCommentResponse.setArticleId(articleResponse.getArticleId());
        articleWithCommentResponse.setTitle(articleResponse.getTitle());
        articleWithCommentResponse.setDescription(articleResponse.getDescription());
        articleWithCommentResponse.setCreatedAt(articleResponse.getCreatedAt());
        articleWithCommentResponse.setOwnerOfArticle(articleResponse.getOwnerOfArticle());
        return articleWithCommentResponse;
    }

    public void checkRole(Integer userId,String message){
        AppUser user = appUserRepository.findById(userId).orElseThrow(()->new NotFoundException("User Not Found."));
        if (user.getRole().equalsIgnoreCase("READER")){
            throw new ForbiddenException(message);
        }
    }
}
