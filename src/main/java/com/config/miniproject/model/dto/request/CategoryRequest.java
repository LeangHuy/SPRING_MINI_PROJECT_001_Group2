package com.config.miniproject.model.dto.request;

import com.config.miniproject.model.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CategoryRequest {
    @NotNull(message = "Category name is required.")
    @NotBlank(message = "Category name is required.")
    private String categoryName;

    public Category toEntity() {
        return new Category(null, this.categoryName, null, null, null, null, null);
    }
}
