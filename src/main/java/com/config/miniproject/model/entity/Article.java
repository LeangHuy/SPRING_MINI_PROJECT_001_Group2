package com.config.miniproject.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    private LocalDate createdAt = LocalDate.now();
    private LocalDate updatedAt;
    @ManyToOne
    private AppUser user;
    @OneToMany(mappedBy = "article")
    private List<CategoryArticle> categoryArticles;
    @OneToMany(mappedBy = "article")
    private List<Bookmark> bookmarks;
    @OneToMany(mappedBy = "article")
    private List<Comment> comments;

}
