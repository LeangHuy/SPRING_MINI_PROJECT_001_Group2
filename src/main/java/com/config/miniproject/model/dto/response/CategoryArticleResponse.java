package com.config.miniproject.model.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class CategoryArticleResponse {
    private Integer id;
    private Integer categoryId;
    private Integer articleId;
    private LocalDateTime createdAt;
}
