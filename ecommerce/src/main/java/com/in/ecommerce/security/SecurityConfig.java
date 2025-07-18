package com.in.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.in.ecommerce.entity.User;
import com.in.ecommerce.repository.UserRepository;
import com.in.ecommerce.service.UserDetailImplements;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailImplements userDetailsService;
    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/","/register", "/login", "/index", "/style.css/**", "/js/**","/images/**").permitAll()
                .requestMatchers("/cart/**", "/order/**", "/wishlist/**","/admin/**").authenticated()
                .anyRequest().authenticated()
            )
            
            .formLogin(login -> login
                .loginPage("/login")
                .successHandler(customLoginSuccessHandler) // ← yaha use hua
                .failureUrl("/login?error=true")
                .permitAll()
            )
          
            .logout(logout -> logout
               .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        
        return http.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(authenticationProvider())
            .build();
    }
    @Bean
    public CommandLineRunner createAdmin(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        return args -> {
            if (!userRepository.existsByEmail("admin@shopvibe.com")) {
                User admin = new User();
                admin.setName("admin");
                admin.setEmail("admin@shopvibe.com");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole("ROLE_ADMIN");
                userRepository.save(admin);
                System.out.println("Admin created.");
            } else {
                System.out.println("Admin already exists.");
            }
        };
    }


}