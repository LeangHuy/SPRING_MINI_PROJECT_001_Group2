package com.config.miniproject.model.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkRequest {

    @NotNull(message = "Article Id is required")
    private Integer articleId;
}
