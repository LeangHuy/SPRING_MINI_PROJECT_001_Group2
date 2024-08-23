package com.config.miniproject.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ApiResponse<T>{
    private String message;
    private HttpStatus status;
    private Integer statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;
    private LocalDateTime timestamp;
}
