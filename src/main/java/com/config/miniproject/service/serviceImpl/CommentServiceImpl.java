package com.config.miniproject.service.serviceImpl;

import com.config.miniproject.exception.ForbiddenException;
import com.config.miniproject.exception.NotFoundException;
import com.config.miniproject.model.dto.request.CommentRequest;
import com.config.miniproject.model.dto.response.AppUserResponse;
import com.config.miniproject.model.dto.response.CommentWithArticleResponse;
import com.config.miniproject.model.entity.AppUser;
import com.config.miniproject.model.entity.Comment;
import com.config.miniproject.repository.CommentRepository;
import com.config.miniproject.service.CommentService;
import com.config.miniproject.utils.GetCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public CommentWithArticleResponse getCommentById(Integer id) {
        Integer userId = GetCurrentUser.userId();
        Comment comment = commentRepository.findByIdAndUserId(id, userId);
        if (comment == null) {
            throw new NotFoundException("Comment id " + id + " not found.");
        }
        AppUser user = comment.getUser();
        AppUserResponse userResponse = user.toResponse();
        return comment.toCommentResponse(userResponse);
    }

    @Override
    public void deleteComment(Integer id) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment id " + id + " not found"));

        Integer currentUserId = GetCurrentUser.userId();

        if (!comment.getUser().getId().equals(currentUserId)) {
            throw new ForbiddenException("You don't have permission to delete this comment");
        }

        commentRepository.delete(comment);
    }

    @Override
    public CommentWithArticleResponse updateComment(Integer id, CommentRequest commentRequest) {

        Comment editComment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment id " + id + " not found"));

        Integer currentUserId = GetCurrentUser.userId();
        if (!editComment.getUser().getId().equals(currentUserId)) {
            throw new ForbiddenException("You don't have permission to update this comment");
        }

        editComment.setCmt(commentRequest.getComment());
        Comment comment = commentRepository.save(editComment);

        AppUser user = comment.getUser();
        AppUserResponse userResponse = user.toResponse();

        return comment.toCommentResponse(userResponse);
    }
}