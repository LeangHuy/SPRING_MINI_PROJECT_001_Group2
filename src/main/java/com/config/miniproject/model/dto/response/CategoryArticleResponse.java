package com.config.miniproject.model.dto.response;

import lombok.*;

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
}
