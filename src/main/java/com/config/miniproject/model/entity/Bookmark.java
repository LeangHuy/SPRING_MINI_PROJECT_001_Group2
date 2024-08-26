package com.config.miniproject.model.entity;

import com.config.miniproject.model.dto.response.BookmarkResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "bookmark_tb")
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean status;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    @ManyToOne
    private Article article;
    @ManyToOne
    private AppUser user;


}
