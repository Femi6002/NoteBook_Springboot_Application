package com.example.notebook.serviceImpl;

import com.example.notebook.auth.model.AppRole;
import com.example.notebook.auth.model.Role;
import com.example.notebook.auth.model.User;
import com.example.notebook.auth.repository.RoleRepository;
import com.example.notebook.auth.repository.UserRepository;
import com.example.notebook.dto.RegisterDTO;
import com.example.notebook.exception.ResourceAlreadyExistsException;
import com.example.notebook.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public RegisterServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User newUser(RegisterDTO registerDTO) {
        if (userRepository.existsByUserName(registerDTO.getUsername())) {
            throw new ResourceAlreadyExistsException("Username already exists.");
        }
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already registered.");
        }


        Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_USER)));

        User newUser = new User();
        newUser.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        newUser.setUserName(registerDTO.getUsername());
        newUser.setEmail(registerDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        newUser.setAccountNonLocked(true);
        newUser.setAccountNonExpired(true);
        newUser.setCredentialsNonExpired(true);
        newUser.setEnabled(true);
        newUser.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
        newUser.setAccountExpiryDate(LocalDate.now().plusYears(1));
        newUser.setSignUpMethod("email");
        newUser.setRole(userRole);
        log.info("New user registered: {}", newUser.getEmail());
        return userRepository.save(newUser);
    }
}
