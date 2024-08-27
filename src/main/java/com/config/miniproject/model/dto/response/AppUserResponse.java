package com.config.miniproject.model.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class AppUserResponse {
    private Integer userId;
    private String userName;
    private String email;
    private String address;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private String role;
}
