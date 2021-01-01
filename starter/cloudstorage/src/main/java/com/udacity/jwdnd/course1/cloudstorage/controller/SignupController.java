package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/signup")
public class SignupController {
	private static Logger logger = LoggerFactory.getLogger(SignupController.class);
	
	private final UserService userService;
	
	public SignupController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	String signupScreen(Model model) {
		logger.info("SignupController - signupScreen(GET)");
		return "signup";
	}
	
	@PostMapping
	public String signupUser(@ModelAttribute User user, Model model) {
		
		logger.info("SignupController - signupUser(POST)");
		String signupError = null;
		
		if (!userService.isUsernameAvailable(user.getUsername())) {
			signupError = "The username already exists.";
		}
		
		if (signupError == null) {
			logger.info("signupUser - creating the user...");
			int rowsAdded = userService.createUser(user);
			logger.info("===> signupUser - rowsAdded: " + rowsAdded);
			if (rowsAdded < 0) {
				signupError = "There was an error signing you up. Please try again.";
			} 
		}
		
		if (signupError == null) {
			logger.info("signupUSer - signupSuccess");
			model.addAttribute("signupSuccess", true);
		} else {
			logger.info("signupUSer - signupError");
			model.addAttribute("signupError", signupError);
		}
		return "signup";
	}

}
