package com.example.notebook.auth.repository;

import com.example.notebook.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserName(String userName);

    Optional<User> findByUserNameOrEmail(String userName, String email);

    boolean existsByEmail(String email);
}
