package com.config.miniproject.controller;

import com.config.miniproject.model.dto.request.AppUserRequest;
import com.config.miniproject.model.dto.response.AppUserResponse;
import com.config.miniproject.model.enumaration.ERoles;
import com.config.miniproject.service.AppUserService;
import com.config.miniproject.service.AuthService;
import com.config.miniproject.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/users")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class UserController {

    private final AppUserService appUserService;
    private final AuthService authService;

    @GetMapping
    @Operation(summary = "Get current user info.")
    public ResponseEntity<ApiResponse<AppUserResponse>> getCurrentUser() {
        ApiResponse<AppUserResponse> response = ApiResponse.<AppUserResponse>builder()
                .message("Get current user information successfully.    ")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(appUserService.loadUserByUserId())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    @Operation(summary = "Update current user's information.")
    public ResponseEntity<ApiResponse<AppUserResponse>> updateCurrentUser(@Valid @RequestBody AppUserRequest appUserRequest, @RequestParam ERoles role) {
        ApiResponse<AppUserResponse> response = ApiResponse.<AppUserResponse>builder()
                .message("Update current user information successfully.")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(authService.updateCurrentUser(appUserRequest,role))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
