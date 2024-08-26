package com.config.miniproject.model.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ArticleResponse {
    private Integer articleId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private Integer ownerOfArticle;
}
