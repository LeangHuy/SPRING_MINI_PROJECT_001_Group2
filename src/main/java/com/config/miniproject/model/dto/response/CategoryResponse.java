package com.config.miniproject.model.dto.response;

import com.config.miniproject.model.entity.AppUser;
import com.config.miniproject.model.entity.CategoryArticle;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private Integer id;
    private String categoryName;
    private LocalDateTime createdAt = LocalDateTime.now();
}
