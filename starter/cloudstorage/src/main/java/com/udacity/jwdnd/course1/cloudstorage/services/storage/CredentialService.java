package com.udacity.jwdnd.course1.cloudstorage.services.storage;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Service
public class CredentialService extends StorageServiceUtility implements StorageService<CredentialForm, Credential> {

	@Autowired
	private CredentialMapper mapper;

	@Autowired
	private EncryptionService encryptionService;

	public CredentialService() {
	}

	@Override
	public List<Credential> getUsersStoredData(Integer userid) {
		List<Credential> credentialList = mapper.getAllUserCredentials(userid);
		for (Credential aCredentialRecord : credentialList) {
			String unenryptedPassword = encryptionService.decryptValue(aCredentialRecord.getPassword(),
					aCredentialRecord.getKey());
			aCredentialRecord.setPassword(unenryptedPassword);
		}
		return credentialList;
	}

	@Override
	public void insertUserStoredData(CredentialForm formRecord, String username) {
		Credential credentialRecord = new Credential();

		// Save owner of this credential resource
		User user = this.getUser(username);
		credentialRecord.setUserid(user.getUserid());
		// Save the username and url
		credentialRecord.setUsername(formRecord.getUsername());
		credentialRecord.setUrl(formRecord.getUrl());

		// generate a key
		SecureRandom random = new SecureRandom();
		byte[] key = new byte[16];
		random.nextBytes(key);

		String encodedKey = Base64.getEncoder().encodeToString(key);
		credentialRecord.setKey(encodedKey);

		// encrypt the password using the key
		String password = formRecord.getPassword();
		String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
		credentialRecord.setPassword(encryptedPassword);

		if (additionalContraintsPassed(credentialRecord)) {
			// save the record
			mapper.insertCredential(credentialRecord);
		} else {
			throw new StorageException(String.format("Duplicate entry attempted for same username: %s and url: %s",
					credentialRecord.getUsername(), credentialRecord.getUrl()));
		}
	}

	@Override
	public void updateUserStoredData(CredentialForm formRecord, String username, Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUserStoredData(Integer credentialid, String username) {

	}

	/**
	 * We will check that the existing URL+USERNAME does not already exist for the
	 * userid - otherwise the user should REALLY do an update and does not know they
	 * are saving the same credential into the database
	 */
	@Override
	public boolean additionalContraintsPassed(Credential modelRecord) {
		// See if we have a record matching this Credential record --> user should be
		// doing an update!
		Credential credentialRecord = mapper.findMatchingUseridUrlAndUsername(modelRecord.getUserid(),
				modelRecord.getUrl(), modelRecord.getUsername());
		return credentialRecord == null;
	}

}
