package com.config.miniproject.model.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiDeleteResponse<T> {
    private String message;
    private HttpStatus status;
}
