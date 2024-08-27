package com.config.miniproject.controller;

import com.config.miniproject.model.dto.request.CommentRequest;
import com.config.miniproject.model.dto.response.CommentResponse;
import com.config.miniproject.model.dto.response.CommentWithArticleResponse;
import com.config.miniproject.service.CommentService;
import com.config.miniproject.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/comment")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}")
    @Operation(summary = "Get comment by its id")
    public ResponseEntity<ApiResponse<CommentWithArticleResponse>> getCommentById(@PathVariable Integer id) {
        CommentWithArticleResponse response = commentService.getCommentById(id);
        ApiResponse<CommentWithArticleResponse> apiResponse = ApiResponse.<CommentWithArticleResponse>builder()
                .status(HttpStatus.OK)
                .message("Get comment by id successfully.")
                .payload(response)
                .timestamp(LocalDateTime.now())
                .statusCode(200)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete comment by its id")
    public ResponseEntity<ApiResponse<String>> deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .message("Comment with id " + id + " is deleted successfully.")
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .statusCode(200)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit comment by its id")
    public ResponseEntity<ApiResponse<CommentWithArticleResponse>> updateComment(@PathVariable Integer id, @RequestBody CommentRequest commentRequest) {
        CommentWithArticleResponse response = commentService.updateComment(id, commentRequest);
        ApiResponse<CommentWithArticleResponse> apiResponse = ApiResponse.<CommentWithArticleResponse>builder()
                .status(HttpStatus.OK)
                .message("Update comment id " + id + " successfully")
                .payload(response)
                .timestamp(LocalDateTime.now())
                .statusCode(200)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
