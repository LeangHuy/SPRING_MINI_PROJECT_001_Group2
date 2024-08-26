package com.config.miniproject.controller;

import com.config.miniproject.model.dto.request.ArticleRequest;
import com.config.miniproject.model.dto.response.ArticleResponse;
import com.config.miniproject.service.ArticleService;
import com.config.miniproject.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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
}
