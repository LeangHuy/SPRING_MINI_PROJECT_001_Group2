package com.config.miniproject.model.entity;

import com.config.miniproject.model.dto.response.CategoryArticleResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity(name = "categories_articles")
public class CategoryArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  id;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    @ManyToOne
    private Article article;
    @ManyToOne
    private Category category;

    public CategoryArticleResponse toResponse() {
        return new CategoryArticleResponse(this.id,this.category.getId(),this.article.getId(),this.getCreatedAt());
    }

}
