package com.in.ecommerce.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.in.ecommerce.entity.User;
import com.in.ecommerce.service.UserService;

import jakarta.validation.Valid;

@Controller
public class authController {

	@Autowired private UserService userService;
	   
	
	@GetMapping("/login")
	public String loginUser() {
		return "login";
	}

	
	    @GetMapping("/register")
	    public String registerPage(Model model) {
	    	model.addAttribute("user", new User());
	        return "register";  
	    }
	    
	    @PostMapping("/register")
	    public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result,Model model) {
	    	 if (result.hasErrors()) {
	    	        return "register"; 
	    	    }
			return userService.registerUser(user, model);
	    	 
	    	
	    }
}
