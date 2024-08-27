package com.config.miniproject.controller;

import com.config.miniproject.model.dto.request.ArticleRequest;
import com.config.miniproject.model.dto.request.CommentRequest;
import com.config.miniproject.model.dto.response.ArticleResponse;
import com.config.miniproject.model.dto.response.ArticleWithCommentResponse;
import com.config.miniproject.model.dto.response.CategoryResponse;
import com.config.miniproject.model.dto.response.CommentResponse;
import com.config.miniproject.model.enumaration.EArticle;
import com.config.miniproject.service.ArticleService;
import com.config.miniproject.service.CommentService;
import com.config.miniproject.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
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
    private final CommentService commentService;

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
    public ResponseEntity<ApiResponse<List<ArticleWithCommentResponse>>> getAllArticle(
            @Positive(message = "Offset cannot be negative or zero") @RequestParam(defaultValue = "1") Integer pageNo,
            @Positive(message = "Limit cannot be negative or zero") @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam EArticle sortBy,
            @RequestParam Sort.Direction sortDirection
            ) {
        ApiResponse<List<ArticleWithCommentResponse>> response = ApiResponse.<List<ArticleWithCommentResponse>>builder()
                .message("Get all articles successfully.")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(articleService.getAllArticles(pageNo,pageSize,sortBy,sortDirection))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
