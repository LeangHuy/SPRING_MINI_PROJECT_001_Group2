package com.config.miniproject.model.dto.response;


import com.config.miniproject.model.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkResponse {
    private Integer id;
    private String title;
    private  String description;
    private LocalDateTime createAt;
    private Article ownerOfArticle;
    private List<CategoryResponse> categories;
    private LocalDateTime updatedAt;
}
