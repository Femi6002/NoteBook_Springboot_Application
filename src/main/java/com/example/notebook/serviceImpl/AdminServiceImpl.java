package com.example.notebook.serviceImpl;

import com.example.notebook.auth.model.AppRole;
import com.example.notebook.auth.model.Role;
import com.example.notebook.auth.model.User;
import com.example.notebook.auth.repository.RoleRepository;
import com.example.notebook.auth.repository.UserRepository;
import com.example.notebook.dto.AdminDTO;
import com.example.notebook.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AdminServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void updateUserRole(Long userId, String roleName) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("<UNK>"));
        AppRole appRole = AppRole.valueOf(roleName);
        Role role = roleRepository.findByRoleName(appRole).orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public AdminDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return convertToDTO(user);
    }

    private AdminDTO convertToDTO(User user) {
        return new AdminDTO(
                user.getUserId(),
                user.getFullName(),
                user.getUserName(),
                user.getEmail(),
                user.isAccountNonLocked(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isEnabled(),
                user.getCredentialsExpiryDate(),
                user.getAccountExpiryDate(),
                user.getSignUpMethod(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
