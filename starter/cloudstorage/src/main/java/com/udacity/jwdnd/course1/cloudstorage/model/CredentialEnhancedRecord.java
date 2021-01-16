package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialEnhancedRecord extends Credential {

	protected String clearPassword;

	public CredentialEnhancedRecord() {}
	
	public CredentialEnhancedRecord(Credential credential) {
		super(credential);
	}
	
	public String getClearPassword() {
		return clearPassword;
	}

	public void setClearPassword(String clearPassword) {
		this.clearPassword = clearPassword;
	}
	
	
}
