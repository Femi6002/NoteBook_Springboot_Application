package com.example.notebook.service;

import com.example.notebook.dto.AdminDTO;
import com.example.notebook.auth.model.User;

import java.util.List;

public interface AdminService {
    void updateUserRole(Long userId, String roleName);

    List<User> getAllUsers();

    AdminDTO getUserById(Long id);
}