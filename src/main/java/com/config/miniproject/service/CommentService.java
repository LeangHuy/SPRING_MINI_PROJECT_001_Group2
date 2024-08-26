package com.config.miniproject.service;

import com.config.miniproject.model.dto.request.CommentRequest;
import com.config.miniproject.model.dto.response.CommentResponse;

public interface CommentService {
    CommentResponse getCommentById(Integer id);

    void deleteComment(Integer id);

    CommentResponse updateComment(Integer id, CommentRequest commentRequest);
}
