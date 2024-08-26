package com.config.miniproject.controller;

import com.config.miniproject.model.dto.request.AppUserRequest;
import com.config.miniproject.model.dto.request.AuthRequest;
import com.config.miniproject.model.dto.response.AppUserResponse;
import com.config.miniproject.model.dto.response.AuthResponse;
import com.config.miniproject.model.enumaration.ERoles;
import com.config.miniproject.service.AppUserService;
import com.config.miniproject.service.AuthService;
import com.config.miniproject.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/auths")
@AllArgsConstructor
@CrossOrigin
public class    AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register as a new user.")
    public ResponseEntity<ApiResponse<AppUserResponse>> createUser(@Valid @RequestBody AppUserRequest appUserRequest,@RequestParam ERoles role) {
        ApiResponse<AppUserResponse> response = ApiResponse.<AppUserResponse>builder()
                .message("Registered successfully.")
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .payload(authService.register(appUserRequest,role))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Login via credentials to get token.")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest authRequest) {
        ApiResponse<AuthResponse> response = ApiResponse.<AuthResponse>builder()
                .message("You have logged in to the system successfully.")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(authService.login(authRequest))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
