package com.in.ecommerce.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.in.ecommerce.entity.User;
import com.in.ecommerce.repository.UserRepository;


@Service
public class UserService {
	
	@Autowired private UserRepository userRepository;
	@Autowired private PasswordEncoder passwordEncoder;
	
	public List<User> getAllUser() {
		return userRepository.findAll();
	}
	
   public String registerUser(User user,Model model) {
	    	
	    	if(!user.getPassword().equals(user.getConfirmPassword())) {
	    		model.addAttribute("passwordMatch", "Passwords do not match!");
	    		return "register";
	    	}
	    	Optional<User> existingUser=userRepository.findByEmail(user.getEmail());
	    	if(existingUser.isPresent()) {
	    		 model.addAttribute("userExist", "Username already exists!");
	    		 return "register";
	    	}
	    	user.setPassword(passwordEncoder.encode(user.getPassword()));
	    	userRepository.save(user);
	    	
	    	return "redirect:/login";		
	    }

}
