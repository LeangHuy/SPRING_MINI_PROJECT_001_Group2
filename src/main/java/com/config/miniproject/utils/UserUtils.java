package com.config.miniproject.utils;

import com.config.miniproject.exception.ForbiddenException;
import com.config.miniproject.exception.NotFoundException;
import com.config.miniproject.model.entity.AppUser;
import com.config.miniproject.repository.AppUserRepository;

public class UserUtils {

    private final AppUserRepository appUserRepository;

    public UserUtils(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser getCurrentUserAndCheckRole(String requiredRole,String message) {
        Integer userId = GetCurrentUser.userId();
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found."));

        if (user.getRole().equalsIgnoreCase(requiredRole)) {
            throw new ForbiddenException(message);
        }

        return user;
    }
}

