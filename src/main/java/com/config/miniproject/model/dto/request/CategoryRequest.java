package com.config.miniproject.model.dto.request;

import com.config.miniproject.model.entity.AppUser;
import com.config.miniproject.model.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CategoryRequest {
    @NotNull(message = "Category name is required.")
    @NotBlank(message = "Category name is required.")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Category name cannot contain number or symbols")
    @Size(max = 15, message = "Category name must not exceed 15 characters.")
    private String categoryName;

    public Category toEntity(AppUser appUser) {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        return new Category(null, this.categoryName, 0, createdAt, updatedAt, appUser, null);
    }
}
