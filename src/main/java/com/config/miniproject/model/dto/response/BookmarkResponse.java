package com.config.miniproject.model.dto.response;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookmarkResponse {
    private Integer id;
    private Integer articleId;
    private LocalDateTime updatedAt;

}
