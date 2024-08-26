package com.config.miniproject.model.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CommentWithArticleResponse {
    private Integer commentId;
    private String comment;
    private LocalDateTime createdAt;
    private AppUserResponse user;
}
