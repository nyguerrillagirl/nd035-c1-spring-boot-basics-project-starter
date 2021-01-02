package com.udacity.jwdnd.course1.cloudstorage.services.storage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

@Service
public class CredentialService {
	
	@Autowired
	private CredentialMapper mapper;

	public List<Credential> getUsersStoredData(Integer userid) {
		return mapper.getAllUserCredentials(userid);
	}
	
	public void insertUserStoredData(Credential aCredentialRecord) {
		// generate a key
		
		// encrypt the password using the key
		
		// save the record
	}
	
	public void updateUserStoredData(Credential aCredentialRecord) {
		
	}
	
	public void deleteUserStoredData(Integer credentialid) {
		
	}
}
