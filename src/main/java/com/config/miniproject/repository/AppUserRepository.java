package com.config.miniproject.repository;

import com.config.miniproject.model.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Integer> {

    AppUser findByEmail(String email);

    AppUser  getAppUserById(Integer id);
}
