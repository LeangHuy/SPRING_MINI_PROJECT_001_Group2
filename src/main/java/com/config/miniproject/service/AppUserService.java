package com.config.miniproject.service;

import com.config.miniproject.model.dto.response.AppUserResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AppUserService extends UserDetailsService {
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
    AppUserResponse loadUserByUserId() throws UsernameNotFoundException;

}
