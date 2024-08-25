package com.config.miniproject.model.dto.request;

import com.config.miniproject.model.entity.AppUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AppUserRequest {
    @NotNull
    @NotBlank
    private String userName;

    @NotNull
    @NotBlank
    @Pattern(
            regexp = "^(\\\\+[0-9]{1,3}[- ]?)?([0-9]{3,4})[- ]?([0-9]{3})[- ]?([0-9]{3})$",
            message = "Please ensure the phone number is in a valid format"
    )
    private String phone;

    @NotNull
    @NotBlank
    private String address;

    @NotNull
    @NotBlank
    @Pattern(
            regexp = "(?i)^(male|female|other)$",
            message = "Please specify a valid gender (male, female, or other)"
    )
    private String gender;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "password must be at least 8 characters long and include both letters and numbers"
    )
    private String password;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "password must be at least 8 characters long and include both letters and numbers"
    )
    private String confirmPassword;

    public AppUser toEntity(String role) {
        LocalDateTime updateAt = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();
        return new AppUser(null, this.userName,
                this.email, this.address,
                this.phone, role, this.password,
                createdAt, updateAt,
                null, null, null, null);
    }
}
