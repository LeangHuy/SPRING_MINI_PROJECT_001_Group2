package com.config.miniproject.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class ArticleResponse {
    private Integer articleId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private Integer ownerOfArticle;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Integer> categoryIdList;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LocalDateTime updatedAt;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CommentWithArticleResponse> commentList;
}
