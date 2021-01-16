package com.udacity.jwdnd.course1.cloudstorage.services.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Service
public abstract class StorageServiceUtility {

	@Autowired
	private UserService userService;
	
	public StorageServiceUtility() {}
	
	
	protected User getUser(String username) {
		User userRecord = userService.getUser(username);
		return userRecord;
	}
	
	protected boolean userOwnsResource(User user, Integer userId) {
		System.out.println("User record: " + user.toString());
		System.out.println("userId: " + userId);
		return user.getUserid().intValue() == userId.intValue();
	}

}
