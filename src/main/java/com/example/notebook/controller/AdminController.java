package com.example.notebook.controller;

import com.example.notebook.auth.model.User;
import com.example.notebook.dto.AdminDTO;
import com.example.notebook.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/getusers")
    public ResponseEntity<List<User>> getAllUsers() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PutMapping("/update-role/{userId}")
    public ResponseEntity<String> updateUserRole(@PathVariable Long userId, @RequestParam String roleName) {
        adminService.updateUserRole(userId, roleName);
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return ResponseEntity.ok("User role updated successfully");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<AdminDTO> getUserById(@PathVariable Long userId) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return ResponseEntity.ok(adminService.getUserById(userId));
    }

}
