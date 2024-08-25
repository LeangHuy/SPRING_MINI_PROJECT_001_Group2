package com.config.miniproject.utils;

import com.config.miniproject.model.entity.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;


public class GetCurrentUser {
    //Get current user id
    public static Integer userId(){
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }
}
