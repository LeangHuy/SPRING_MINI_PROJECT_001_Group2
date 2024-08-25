package com.config.miniproject.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "comment_tb")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cmt;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    @ManyToOne
    private Article article;
    @ManyToOne
    private AppUser user;
}