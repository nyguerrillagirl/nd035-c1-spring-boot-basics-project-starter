package com.udacity.jwdnd.course1.cloudstorage;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

/** 
 * Use this to test UserMapper 
 * @author lafig
 *
 */
//@Component
public class UserCommandLineRunner implements CommandLineRunner {

	private static Logger logger = LoggerFactory.getLogger(UserCommandLineRunner.class);
	
	@Autowired
	private UserService userService;
	
	@Override
	public void run(String... args) throws Exception {
		logger.info("===> UserCommandLineRunner <===");
		//User firstUser = new User(null, "nyguerrillgirl", null, "password123", "Lorraine", "Figueroa");
		//User firstUser = new User(null, "samro65", null, "password123", "Samantha", "Neill");
		//userService.createUser(firstUser);
		
		List<User> users = userService.getAllUsers();
		logger.info("===> users: " + users.toString());
	}

}
