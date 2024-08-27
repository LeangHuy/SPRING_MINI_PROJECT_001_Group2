package com.config.miniproject.controller;


import com.config.miniproject.model.dto.response.ArticleResponse;
import com.config.miniproject.model.dto.response.CommentWithArticleResponse;
import com.config.miniproject.model.entity.Bookmark;
import com.config.miniproject.service.BookmarkService;
import com.config.miniproject.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RequestMapping("/api/v1/bookmark")
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class BookmarkController {


    private final BookmarkService bookmarkService;

    @Operation(summary = "Bookmark an article.")
    @PostMapping("/{ArticleId}")
    public ResponseEntity<?> postBookMark( Integer ArticleId){
        bookmarkService.createBookmark(ArticleId);
        ApiResponse<?> response = ApiResponse.builder()
                .message("article has been bookmarked successfully")
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok(response);
    }


    @GetMapping
    @Operation(summary = "Get all bookmarked articles.")
    public ResponseEntity<ApiResponse<List<ArticleResponse>>> getBookMark(
            @RequestParam(required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "articleId") String sortBy,
            @RequestParam Sort.Direction sortDirection
    ){

        ApiResponse<List<ArticleResponse>> response = ApiResponse.<List<ArticleResponse>>builder()
                .message("Get all bookmarked articles successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(bookmarkService.getAllBookmarks(pageNo, pageSize, sortBy, sortDirection))
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/article/{id}")
    @Operation(summary = "Change status when this article is no longer your favourite")
    public ResponseEntity<?> updateBookmarked (@PathVariable  Integer id){

        bookmarkService.updateBookmarked(id);
        ApiResponse<?> response = ApiResponse.builder()
                .message(" an Article with id "+ id + " is unmarked successfully")
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok(response);
    }


}
