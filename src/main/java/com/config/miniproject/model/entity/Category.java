package com.config.miniproject.model.entity;

import com.config.miniproject.model.dto.response.CategoryResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "category_tb")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String categoryName;
    private Integer amountOfArticle;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    @ManyToOne
    private AppUser user;
    @OneToMany(mappedBy = "category")
    private List<CategoryArticle> categoryArticles;

    public CategoryResponse toResponse() {
        return new CategoryResponse(this.id, this.categoryName, this.amountOfArticle, this.createdAt, this.updatedAt, categoryArticles);
    }
}
