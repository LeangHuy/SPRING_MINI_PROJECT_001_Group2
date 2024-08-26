package com.config.miniproject.controller;

import com.config.miniproject.model.dto.request.ArticleRequest;
import com.config.miniproject.model.dto.request.CommentRequest;
import com.config.miniproject.model.dto.response.ArticleResponse;
import com.config.miniproject.model.dto.response.CommentResponse;
import com.config.miniproject.model.enumaration.EArticle;
import com.config.miniproject.service.ArticleService;
import com.config.miniproject.utils.ApiResponse;
import com.config.miniproject.utils.GetCurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/author/article")
    @Operation(summary = "Create a new article")
    public ResponseEntity<ApiResponse<ArticleResponse>> createArticle(@Valid @RequestBody ArticleRequest articleRequest) {
        ApiResponse<ArticleResponse> response = ApiResponse.<ArticleResponse>builder()
                .message("A new article is created successfully.")
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .payload(articleService.createArticle(articleRequest))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/articles/all")
    @Operation(summary = "Get all article.")
    public ResponseEntity<ApiResponse<List<ArticleResponse>>> getAllArticle(
            @Positive(message = "Offset cannot be negative or zero") @RequestParam(defaultValue = "1") Integer pageNo,
            @Positive(message = "Limit cannot be negative or zero") @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam EArticle sortBy,
            @RequestParam Sort.Direction sortDirection
    ) {
        ApiResponse<List<ArticleResponse>> response = ApiResponse.<List<ArticleResponse>>builder()
                .message("Get all articles successfully.")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(articleService.getAllArticles(pageNo, pageSize, sortBy, sortDirection))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/article/{articleId}")
    @Operation(summary = "Get article by id.")
    public ResponseEntity<ApiResponse<ArticleResponse>> getArticleById(@PathVariable Integer articleId) {
        ApiResponse<ArticleResponse> response = ApiResponse.<ArticleResponse>builder()
                .message("Get article with id " + articleId + " successfully.")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(articleService.getArticleById(articleId))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/author/article/{articleId}")
    @Operation(summary = "Edit article by id.")
    public ResponseEntity<ApiResponse<ArticleResponse>> updateArticleById(@Valid @RequestBody ArticleRequest articleRequest, @PathVariable Integer articleId) {
        ApiResponse<ArticleResponse> response = ApiResponse.<ArticleResponse>builder()
                .message("Update article with id " + articleId + " successfully.")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(articleService.updateArticleById(articleId, articleRequest))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/author/article/{articleId}")
    @Operation(summary = "Delete article by id.")
    public ResponseEntity<ApiResponse<ArticleResponse>> deleteArticleById(@PathVariable Integer articleId) {
        articleService.deleteArticleById(articleId);
        ApiResponse<ArticleResponse> response = ApiResponse.<ArticleResponse>builder()
                .message("Delete article with id " + articleId + " successfully.")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/article/{articleId}/comment")
    @Operation(summary = "Post a comment on any article via its id.")
    public ResponseEntity<ApiResponse<ArticleResponse>> createComment(@PathVariable Integer articleId, @Valid @RequestBody CommentRequest commentRequest) {
        Integer userId = GetCurrentUser.userId();
        ApiResponse<ArticleResponse> response = ApiResponse.<ArticleResponse>builder()
                .message("A new comment is posted on article " + articleId + " by user " + userId)
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .payload(articleService.createCommentByArticleId(userId, articleId, commentRequest))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/article/{articleId}/comment")
    @Operation(summary = "Get comments on any article.")
    public ResponseEntity<ApiResponse<ArticleResponse>> getCommentByArticleId(@PathVariable Integer articleId) {
        ApiResponse<ArticleResponse> response = ApiResponse.<ArticleResponse>builder()
                .message("Get all comments on article id " + articleId + " successfully.")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(articleService.getCommentByArticleId(articleId))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
