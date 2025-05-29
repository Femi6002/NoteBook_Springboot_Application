package com.example.notebook.auth.configuration;

import com.example.notebook.auth.model.AppRole;
import com.example.notebook.auth.model.Role;
import com.example.notebook.auth.model.User;
import com.example.notebook.auth.repository.RoleRepository;
import com.example.notebook.auth.repository.UserRepository;
import com.example.notebook.auth.service.UserDetailServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.time.LocalDate;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final UserDetailServiceImpl userDetailService;

    public SecurityConfiguration(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Recommended over NoOp
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) ->
                requests
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(withDefaults())
                .sessionManagement(
                session -> session
                        .sessionCreationPolicy(
                                SessionCreationPolicy
                                        .STATELESS
                        )
        );// But Stateless session ID does not work with form login
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }



    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository) {
        return args -> {
            Role userRole  =  roleRepository.findByRoleName(AppRole.ROLE_USER).orElseGet(()-> roleRepository.save(new Role(AppRole.ROLE_USER)));
            Role adminRole  =  roleRepository.findByRoleName(AppRole.ROLE_ADMIN).orElseGet(()-> roleRepository.save(new Role(AppRole.ROLE_ADMIN)));

            // Optional: default admin
            if (!userRepository.existsByUserName("olatunji.t") || !userRepository.existsByEmail("admin@example.com")) {
                User admin = new User();
                admin.setFullName("Olatunji Oluwafemi");
                admin.setUserName("olatunji.t");
                admin.setEmail("admin@example.com");
                passwordEncoder(); //
                admin.setPassword(passwordEncoder().encode("securePassword123"));
                admin.setAccountNonLocked(true);
                admin.setAccountNonExpired(true);
                admin.setCredentialsNonExpired(true);
                admin.setEnabled(true);
                admin.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                admin.setAccountExpiryDate(LocalDate.now().plusYears(1));
                admin.setSignUpMethod("email");
                admin.setRole(adminRole);
                userRepository.save(admin);
            }
        };
    }

}
