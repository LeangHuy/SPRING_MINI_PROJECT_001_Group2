package com.config.miniproject.model.entity;

import com.config.miniproject.model.dto.response.ArticleResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "article_tb")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne
    private AppUser user;
    @OneToMany
    @ToString.Exclude
    private List<CategoryArticle> categoryArticles;
    @OneToMany
    @ToString.Exclude
    private List<Bookmark> bookmarks;
    @OneToMany
    @ToString.Exclude
    private List<Comment> comments;

    public ArticleResponse toResponse(){
        return new ArticleResponse(this.id, this.title,
                this.description, this.createdAt, this.user.getId(),
                null,null,null
                );
    }

}
