package com.config.miniproject.model.dto.response;

import com.config.miniproject.model.entity.AppUser;
import com.config.miniproject.model.entity.Article;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommentResponse {
    private Integer id;
    private String cmt;
    private LocalDateTime createdAt = LocalDateTime.now();
    private AppUser user;
}
