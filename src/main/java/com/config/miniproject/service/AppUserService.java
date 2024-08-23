package com.config.miniproject.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AppUserService {
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

}
