package com.config.miniproject.service;

import com.config.miniproject.model.dto.request.CommentRequest;
import com.config.miniproject.model.dto.response.CommentResponse;
import com.config.miniproject.model.dto.response.CommentWithArticleResponse;

public interface CommentService {
    CommentWithArticleResponse getCommentById(Integer id);

    void deleteComment(Integer id);

    CommentWithArticleResponse updateComment(Integer id, CommentRequest commentRequest);

}
