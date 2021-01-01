package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Service
public class UserService {

	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	private final UserMapper userMapper;
	private final HashService hashService;

	public UserService(UserMapper userMapper, HashService hashService) {
		this.userMapper = userMapper;
		this.hashService = hashService;
	}

	public boolean isUsernameAvailable(String username) {
		logger.debug("===> isUsernameAvailable invoked with username: " + username);
		User aUser = userMapper.getUserByName(username);
		// TODO: Remove or comment out this debugging code
		if (aUser == null) {
			logger.debug("===> user record NOT FOUND.");
		} else {
			logger.debug("===> user record FOUND.");
		}
		return userMapper.getUserByName(username) == null;
	}

	public int createUser(User user) {
		logger.debug("===> createUser invoked with user: " + user.toString());
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		String encodedSalt = Base64.getEncoder().encodeToString(salt);
		String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
		
		logger.debug("===> createUser inserting record using userMapper");
		return userMapper.insert(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstname(),
				user.getLastname()));
	}

	public User getUser(String username) {
		logger.debug("===> getUser where username: " + username);
	return userMapper.getUserByName(username);
	}
	
	public List<User> getAllUsers() {
		logger.debug("===> getAllUsers");
		return userMapper.getAllUsers();
	}
}