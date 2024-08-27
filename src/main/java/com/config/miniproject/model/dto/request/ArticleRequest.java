package com.config.miniproject.model.dto.request;

import com.config.miniproject.model.entity.AppUser;
import com.config.miniproject.model.entity.Article;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ArticleRequest {
    @NotNull(message = "Title is required.")
    @NotBlank(message = "Title is required.")
    private String title;
    @NotNull(message = "Description is required.")
    @NotBlank(message = "Description is required.")
    private String description;
    private List<Integer> categoryId;

    public Article toEntity(AppUser user) {
        LocalDateTime createdAt = LocalDateTime.now();
        return new Article(null, this.title, this.description,
                createdAt, null, user, null, null, null);
    }

    public Article toEntity(Integer articleId,AppUser user,LocalDateTime createdAt) {
        LocalDateTime updatedAt = LocalDateTime.now();
        return new Article(articleId, this.title, this.description,
                createdAt, updatedAt, user, null, null, null);
    }
}
