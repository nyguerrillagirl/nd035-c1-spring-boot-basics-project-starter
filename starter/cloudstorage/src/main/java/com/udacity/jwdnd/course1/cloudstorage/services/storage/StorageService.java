package com.udacity.jwdnd.course1.cloudstorage.services.storage;

import java.util.List;

public interface StorageService<F,M> {

	List<M> getUsersStoredData(Integer userid);
	public void insertUserStoredData(F formRecord, String username);
	public void updateUserStoredData(F formRecord, String username);
	public void deleteUserStoredData(Integer credentialid, String username);
	
	// This is used by any storage mechanism to check additional constraints (if any)
	// that make sense for the storage type
	public boolean additionalContraintsPassed(M modelRecord);
}
