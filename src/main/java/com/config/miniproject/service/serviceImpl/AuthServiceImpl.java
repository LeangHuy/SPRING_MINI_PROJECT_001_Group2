package com.config.miniproject.service.serviceImpl;

import com.config.miniproject.exception.BadRequestException;
import com.config.miniproject.exception.ConflictException;
import com.config.miniproject.exception.NotFoundException;
import com.config.miniproject.jwt.JwtService;
import com.config.miniproject.model.dto.request.AppUserRequest;
import com.config.miniproject.model.dto.request.AuthRequest;
import com.config.miniproject.model.dto.response.AppUserResponse;
import com.config.miniproject.model.dto.response.AuthResponse;
import com.config.miniproject.model.entity.AppUser;
import com.config.miniproject.model.enumaration.ERoles;
import com.config.miniproject.repository.AppUserRepository;
import com.config.miniproject.service.AuthService;
import com.config.miniproject.utils.GetCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AppUserRepository appUserRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Override
    public AppUserResponse register(AppUserRequest appUserRequest, ERoles role) {
        AppUser appUser = appUserRepository.findByEmail(appUserRequest.getEmail());
        if (!appUserRequest.getPassword().equals(appUserRequest.getConfirmPassword())) {
            throw new BadRequestException("Your confirm password does not match with your password");
        }
        if (appUser != null) {
            throw new ConflictException("This email is already registered.");
        }
        appUserRequest.setPassword(passwordEncoder.encode(appUserRequest.getPassword()));
        return appUserRepository.save(appUserRequest.toEntity(role.name())).toResponse();
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        authenticate(authRequest.getEmail(), authRequest.getPassword());
        AppUser appUser = appUserRepository.findByEmail(authRequest.getEmail());
        String token = jwtService.generateToken(appUser);
        return new AuthResponse(token);
    }

    @Override
    public AppUserResponse updateCurrentUser(AppUserRequest appUserRequest, ERoles role) {
        Integer userId = GetCurrentUser.userId();
        appUserRepository.findById(userId).orElseThrow(()-> new NotFoundException("User not found."));
        AppUser appUser = appUserRepository.findByEmail(appUserRequest.getEmail());
        if (!appUserRequest.getPassword().equals(appUserRequest.getConfirmPassword())) {
            throw new BadRequestException("Your confirm password does not match with your password");
        }
        if (appUser != null) {
            throw new ConflictException("This email is already registered.");
        }
        appUserRequest.setPassword(passwordEncoder.encode(appUserRequest.getPassword()));
        return appUserRepository.save(appUserRequest.toEntity(userId,role.name())).toResponse();
    }

    private void authenticate(String email, String password) {
        AppUser appUser = appUserRepository.findByEmail(email);
        if (appUser == null) {
            throw new NotFoundException("Invalid email address.");
        }
        if (!passwordEncoder.matches(password, appUser.getPassword())) {
            throw new NotFoundException("Password does not match.");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
