package com.config.miniproject.service.serviceImpl;

import com.config.miniproject.exception.NotFoundException;
import com.config.miniproject.model.dto.response.AppUserResponse;
import com.config.miniproject.repository.AppUserRepository;
import com.config.miniproject.service.AppUserService;
import com.config.miniproject.utils.GetCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email);
    }

    @Override
    public AppUserResponse loadUserByUserId() throws UsernameNotFoundException {
        Integer userId = GetCurrentUser.userId();
        return appUserRepository.findById(userId).orElseThrow(()-> new NotFoundException("User not found.")).toResponse();
    }


}
