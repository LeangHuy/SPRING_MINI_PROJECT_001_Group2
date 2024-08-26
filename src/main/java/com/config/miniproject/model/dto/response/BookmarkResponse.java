package com.config.miniproject.model.dto.response;


import com.config.miniproject.model.entity.AppUser;
import com.config.miniproject.model.entity.Article;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookmarkResponse {
    private Integer id;
    private Integer articleId;

}
