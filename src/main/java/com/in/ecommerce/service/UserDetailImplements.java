package com.in.ecommerce.service;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.in.ecommerce.entity.User;
import com.in.ecommerce.repository.UserRepository;


@Service
public class UserDetailImplements implements UserDetailsService  {
    @Autowired
    private UserRepository userRepository;

  
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 🔍 Login with email as username
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 👇 Role (hardcoded for now)
        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(user.getRole())
        );

        // 🔐 Return Spring Security's built-in User object
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),        // username
                user.getPassword(),     // encrypted password
                authorities             // list of roles
        );
    }



	   

	
    
    
}
