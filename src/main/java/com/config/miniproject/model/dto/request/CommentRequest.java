package com.config.miniproject.model.dto.request;

import com.config.miniproject.model.entity.AppUser;
import com.config.miniproject.model.entity.Article;
import com.config.miniproject.model.entity.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommentRequest {
    @NotNull(message = "Comment is required.")
    @NotBlank(message = "Comment is required.")
    private String comment;

    public Comment toEntity(Article article, AppUser appUser){
        LocalDateTime createdAt = LocalDateTime.now();
        return new Comment(null,this.comment,createdAt,null,article,appUser);
    }
}
