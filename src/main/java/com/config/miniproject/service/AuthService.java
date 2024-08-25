package com.config.miniproject.service;

import com.config.miniproject.model.dto.request.AppUserRequest;
import com.config.miniproject.model.dto.request.AuthRequest;
import com.config.miniproject.model.dto.response.AppUserResponse;
import com.config.miniproject.model.dto.response.AuthResponse;
import com.config.miniproject.model.enumaration.ERoles;

public interface AuthService {

    AppUserResponse register(AppUserRequest appUserRequest, ERoles role);
    AuthResponse login(AuthRequest authRequest);
}
