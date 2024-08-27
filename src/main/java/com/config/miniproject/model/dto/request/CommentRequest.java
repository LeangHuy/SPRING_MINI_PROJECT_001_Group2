package com.config.miniproject.model.dto.request;

import com.config.miniproject.model.entity.AppUser;
import com.config.miniproject.model.entity.Comment;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommentRequest {
    private String cmt;

    public Comment toEntity(AppUser user) {
        return new Comment(null, this.cmt, null, null, null, user);
    }
}
