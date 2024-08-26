package com.config.miniproject.controller;


import com.config.miniproject.model.dto.ApiResponse;
import com.config.miniproject.model.dto.request.BookmarkRequest;
import com.config.miniproject.model.entity.Bookmark;
import com.config.miniproject.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RequestMapping("/api/v1/bookmark")
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class BookmarkController {


    private final BookmarkService bookmarkService;

    @Operation(summary = "Bookmark an article.")
    @PostMapping("/{ArticleId}")
    public ResponseEntity<?> postBookMark( Integer ArticleId){
        Bookmark bookmark = bookmarkService.createBookmark(ArticleId);
        ApiResponse<?> response = ApiResponse.builder()
                .message("article has been bookmarked successfully")
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping
    @Operation(summary = "Get all bookmarked articles.")
    public ResponseEntity<?> getBookMark(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "articleId") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction orderBy
    ){
        Bookmark bookmark = bookmarkService.getAllBookmarks(page, size, sortBy, orderBy);
        ApiResponse<?> response = ApiResponse.builder()
                .message("Get all bookmarked articles successfully")
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/article/{id}")
    @Operation(summary = "Change status when this article is no longer your favourite")
    public ResponseEntity<?> updateBookmarked (@PathVariable  Integer id){

        bookmarkService.updateBookmarked(id);
        ApiResponse<?> response = ApiResponse.builder()
                .message(" an Article with id "+ id + " is unmarked successfully")
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
