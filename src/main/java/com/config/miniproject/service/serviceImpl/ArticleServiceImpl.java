package com.config.miniproject.service.serviceImpl;

import com.config.miniproject.exception.ForbiddenException;
import com.config.miniproject.exception.NotFoundException;
import com.config.miniproject.model.dto.request.ArticleRequest;
import com.config.miniproject.model.dto.request.CommentRequest;
import com.config.miniproject.model.dto.response.*;
import com.config.miniproject.model.entity.*;
import com.config.miniproject.model.enumaration.EArticle;
import com.config.miniproject.repository.*;
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
    private final CommentRepository commentRepository;

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
    public List<ArticleResponse> getAllArticles(int page, int size, EArticle sortBy, Sort.Direction sortDirection) {
        Integer userId = GetCurrentUser.userId();
        Sort sort = Sort.by(sortDirection,sortBy.name());
        Pageable pageable = PageRequest.of(page-1, size, sort);
        List<ArticleResponse> articles = articleRepository.findAll(pageable).getContent().stream().map(Article::toResponse).toList();
        List<ArticleResponse> articleResponseList = new ArrayList<>();

        for (ArticleResponse article : articles) {
            List<CategoryArticleResponse> categoryArticles = categoryArticleRepository.findAllByArticleId(article.getArticleId()).stream().map(CategoryArticle::toResponse).toList();
            ArticleResponse articleResponse = new ArticleResponse();
            Article articleC = articleRepository.findById(article.getArticleId()).orElseThrow();
            AppUser appUser = appUserRepository.findById(userId).orElseThrow(()-> new NotFoundException("User not found."));
            List<Comment> comment = commentRepository.findAllByArticle(articleC);
            List<CommentWithArticleResponse> commentWithArticleResponses = comment.stream()
                    .map(c -> c.toCommentResponse(appUser.toResponse()))
                    .toList();
            List<Integer> categoryIdList = categoryArticles.stream().map(CategoryArticleResponse::getCategoryId).toList();
            articleResponse.setCategoryIdList(categoryIdList);
            articleResponse.setArticleId(article.getArticleId());
            articleResponse.setTitle(article.getTitle());
            articleResponse.setDescription(article.getDescription());
            articleResponse.setCreatedAt(article.getCreatedAt());
            articleResponse.setOwnerOfArticle(article.getOwnerOfArticle());
            articleResponse.setCommentList(commentWithArticleResponses);
            articleResponseList.add(articleResponse);
        }
        return articleResponseList;
    }

    @Override
    public ArticleResponse updateArticleById(Integer articleId, ArticleRequest articleRequest) {
        ArticleResponse  articleResponse =  new ArticleResponse();
        Integer userId = GetCurrentUser.userId();
        AppUser user = appUserRepository.findById(userId).orElseThrow(()->new NotFoundException("User Not Found."));
        checkRole(userId,"You are not allowed to edit articles.");
        ArticleResponse articleById = getArticleById(articleId);
        if (!articleById.getOwnerOfArticle().equals(userId)){
            throw new ForbiddenException("Cannot delete/update not found article id "+articleId);
        }
        Article article = articleRepository.save(articleRequest.toEntity(articleId,user,articleById.getCreatedAt()));
        categoryArticleRepository.deleteAllByArticle(article);
        for (Integer categoryId : articleRequest.getCategoryId()) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(()->new NotFoundException("Category id "+categoryId+" not found."));
            CategoryArticle categoryArticle= new CategoryArticle(null, LocalDateTime.now(), LocalDateTime.now(),article,category);
            categoryArticleRepository.save(categoryArticle);
        }
        ArticleResponse articleResponse1 = article.toResponse();
        Article articleC = articleRepository.findById(articleId).orElseThrow();
        AppUser appUser = appUserRepository.findById(userId).orElseThrow(()-> new NotFoundException("User not found."));
        List<Comment> comment = commentRepository.findAllByArticle(articleC);
        List<CommentWithArticleResponse> commentWithArticleResponses = comment.stream()
                .map(c -> c.toCommentResponse(appUser.toResponse()))
                .toList();
        List<CategoryArticleResponse> categoryArticles = categoryArticleRepository.findAllByArticleId(articleId).stream().map(CategoryArticle::toResponse).toList();
        List<Integer> categoryIdList = categoryArticles.stream().map(CategoryArticleResponse::getCategoryId).toList();
        articleResponse.setCategoryIdList(categoryIdList);
        articleResponse.setArticleId(article.getId());
        articleResponse.setTitle(article.getTitle());
        articleResponse.setDescription(article.getDescription());
        articleResponse.setCreatedAt(article.getCreatedAt());
        articleResponse.setOwnerOfArticle(articleResponse1.getOwnerOfArticle());
        articleResponse.setCommentList(commentWithArticleResponses);
        return articleResponse;
    }

    @Override
    public ArticleResponse getArticleById(Integer articleId) {
        Integer userId = GetCurrentUser.userId();
        ArticleResponse articleResponse = articleRepository.findById(articleId).orElseThrow(()->new NotFoundException("Article id "+articleId+" not found.")).toResponse();
        List<CategoryArticleResponse> categoryArticles = categoryArticleRepository.findAllByArticleId(articleId).stream().map(CategoryArticle::toResponse).toList();
        List<Integer> categoryIdList = categoryArticles.stream().map(CategoryArticleResponse::getCategoryId).toList();
        Article articleC = articleRepository.findById(articleId).orElseThrow();
        AppUser appUser = appUserRepository.findById(userId).orElseThrow(()-> new NotFoundException("User not found."));
        List<Comment> comment = commentRepository.findAllByArticle(articleC);
        List<CommentWithArticleResponse> commentWithArticleResponses = comment.stream()
                .map(c -> c.toCommentResponse(appUser.toResponse()))
                .toList();
        articleResponse.setCategoryIdList(categoryIdList);
        articleResponse.setOwnerOfArticle(articleResponse.getOwnerOfArticle());
        articleResponse.setUpdatedAt(articleResponse.getUpdatedAt());
        articleResponse.setCommentList(commentWithArticleResponses);
        return articleResponse;
    }

    @Override
    public void deleteArticleById(Integer articleId) {
        Integer userId = GetCurrentUser.userId();
        checkRole(userId,"You are not allowed to delete articles.");
        ArticleResponse articleResponse = getArticleById(articleId);
        if (!articleResponse.getOwnerOfArticle().equals(userId)){
            throw new ForbiddenException("Cannot delete/update not found article id "+articleId);
        }
        getArticleById(articleId);
        articleRepository.deleteById(articleId);
    }

    @Override
    public ArticleResponse createCommentByArticleId(Integer userId, Integer articleId, CommentRequest commentRequest) {
        ArticleResponse findArticleById = getArticleById(articleId);
        Article article = articleRepository.findById(articleId).orElseThrow();
        AppUser appUser = appUserRepository.findById(userId).orElseThrow(()-> new NotFoundException("User not found."));
        commentRepository.save(commentRequest.toEntity(article,appUser));
        List<Comment> comment = commentRepository.findAllByArticle(article);
        List<CommentWithArticleResponse> commentWithArticleResponses = comment.stream()
                .map(c -> c.toCommentResponse(appUser.toResponse()))
                .toList();
        ArticleResponse articleResponse = new ArticleResponse();
        List<CategoryArticleResponse> categoryArticles = categoryArticleRepository.findAllByArticleId(articleId).stream().map(CategoryArticle::toResponse).toList();
        List<Integer> categoryIdList = categoryArticles.stream().map(CategoryArticleResponse::getCategoryId).toList();
        articleResponse.setCategoryIdList(categoryIdList);
        articleResponse.setArticleId(findArticleById.getArticleId());
        articleResponse.setTitle(findArticleById.getTitle());
        articleResponse.setDescription(findArticleById.getDescription());
        articleResponse.setCreatedAt(findArticleById.getCreatedAt());
        articleResponse.setUpdatedAt(findArticleById.getCreatedAt());
        articleResponse.setOwnerOfArticle(findArticleById.getOwnerOfArticle());
        articleResponse.setCommentList(commentWithArticleResponses);
        return articleResponse;
    }

    @Override
    public ArticleResponse getCommentByArticleId(Integer articleId) {
        Integer userId = GetCurrentUser.userId();
        ArticleResponse findArticleById = getArticleById(articleId);
        Article article = articleRepository.findById(articleId).orElseThrow();
        AppUser appUser = appUserRepository.findById(userId).orElseThrow(()-> new NotFoundException("User not found."));
        List<Comment> comment = commentRepository.findAllByArticle(article);
        List<CommentWithArticleResponse> commentWithArticleResponses = comment.stream()
                .map(c -> c.toCommentResponse(appUser.toResponse()))
                .toList();
        ArticleResponse articleResponse = new ArticleResponse();
        List<CategoryArticleResponse> categoryArticles = categoryArticleRepository.findAllByArticleId(articleId).stream().map(CategoryArticle::toResponse).toList();
        List<Integer> categoryIdList = categoryArticles.stream().map(CategoryArticleResponse::getCategoryId).toList();
        articleResponse.setCategoryIdList(categoryIdList);
        articleResponse.setArticleId(findArticleById.getArticleId());
        articleResponse.setTitle(findArticleById.getTitle());
        articleResponse.setDescription(findArticleById.getDescription());
        articleResponse.setCreatedAt(findArticleById.getCreatedAt());
        articleResponse.setUpdatedAt(findArticleById.getCreatedAt());
        articleResponse.setOwnerOfArticle(findArticleById.getOwnerOfArticle());
        articleResponse.setCommentList(commentWithArticleResponses);
        return articleResponse;
    }

    public void checkRole(Integer userId,String message){
        AppUser user = appUserRepository.findById(userId).orElseThrow(()->new NotFoundException("User Not Found."));
        if (user.getRole().equalsIgnoreCase("READER")){
            throw new ForbiddenException(message);
        }
    }
}
