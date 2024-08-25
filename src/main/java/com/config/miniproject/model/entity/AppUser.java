package com.config.miniproject.model.entity;

import com.config.miniproject.model.dto.response.AppUserResponse;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity(name = "user_tb")
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userName;
    private String email;
    private String address;
    private String phoneNumber;
    private String role;
    private String password;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    @OneToMany(mappedBy = "user")
    private List<Category> categories;
    @OneToMany(mappedBy = "user")
    private List<Article> articles;
    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarks;
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("user"));
    }

    public AppUserResponse toResponse() {
        return new AppUserResponse(this.id,this.userName,this.email,this.address,this.phoneNumber,this.created_at,this.role);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
