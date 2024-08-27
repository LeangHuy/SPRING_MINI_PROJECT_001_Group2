package com.config.miniproject.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private Integer id;
    private String categoryName;
    private Integer amountOfArticle;
    private LocalDateTime createdAt = LocalDateTime.now();
}
