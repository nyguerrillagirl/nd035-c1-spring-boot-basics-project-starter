package com.udacity.jwdnd.course1.cloudstorage.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.common.CloudStorageConstants;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.storage.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.storage.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.storage.NoteService;

@Controller
@RequestMapping("/home")
public class HomeController {
	private static Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private CredentialService credentialService;
	
	@Autowired
	private UserService userService;

	@GetMapping
	public String getHomePage() {
		return "home";
	}
	
	@ModelAttribute
	public void allUserFiles(Authentication authentication, Model model) {
		String userName = authentication.getName();
		logger.info("HomeController - allUserFiles: " + userName);
		User userRecord = userService.getUser(userName);
		if (userRecord != null) {
			logger.info("HomeController.allUserFiles - userRecord found.");
			Integer userId = userRecord.getUserid();
			model.addAttribute(CloudStorageConstants.FILE_LIST, fileService.getUsersStoredData(userId));
			model.addAttribute(CloudStorageConstants.CREDENTIAL_LIST, credentialService.getUsersStoredData(userId));
			model.addAttribute(CloudStorageConstants.NOTE_LIST, noteService.getUsersStoredData(userId));
		}
	}
}
