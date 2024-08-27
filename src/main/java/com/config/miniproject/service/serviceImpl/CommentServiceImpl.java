package com.config.miniproject.service.serviceImpl;

import com.config.miniproject.exception.NotFoundException;
import com.config.miniproject.model.dto.request.CommentRequest;
import com.config.miniproject.model.dto.response.CommentResponse;
import com.config.miniproject.model.entity.AppUser;
import com.config.miniproject.model.entity.Category;
import com.config.miniproject.model.entity.Comment;
import com.config.miniproject.repository.AppUserRepository;
import com.config.miniproject.repository.CategoryRepository;
import com.config.miniproject.repository.CommentRepository;
import com.config.miniproject.service.CommentService;
import com.config.miniproject.utils.UserUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public CommentResponse getCommentById(Integer id) {
        UserUtils userUtils = new UserUtils(appUserRepository);
        AppUser user = userUtils.getCurrentUserAndCheckRole("READER");

        return commentRepository.findById(id).orElseThrow().toResponse();
    }

    @Override
    public void deleteComment(Integer id) {
        UserUtils userUtils = new UserUtils(appUserRepository);
        AppUser user = userUtils.getCurrentUserAndCheckRole("READER");

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        commentRepository.delete(comment);
    }

    @Override
    public CommentResponse updateComment(Integer id, CommentRequest commentRequest) {
        UserUtils userUtils = new UserUtils(appUserRepository);
        AppUser appUser = userUtils.getCurrentUserAndCheckRole("READER");

        Comment editComment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        editComment.setCmt(commentRequest.getComment());

        Comment comment = commentRepository.save(editComment);

        return comment.toResponse();
    }
}