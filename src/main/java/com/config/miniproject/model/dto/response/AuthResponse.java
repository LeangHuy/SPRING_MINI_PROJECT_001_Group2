package com.config.miniproject.model.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AuthResponse {
    private String token;
}
