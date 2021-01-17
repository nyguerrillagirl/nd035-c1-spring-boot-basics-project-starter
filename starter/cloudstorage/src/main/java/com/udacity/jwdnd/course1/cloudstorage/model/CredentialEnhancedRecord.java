package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialEnhancedRecord extends CredentialEntity {

	protected String clearPassword;

	public CredentialEnhancedRecord() {}
	
	public CredentialEnhancedRecord(CredentialEntity credential) {
		super(credential);
	}
	
	public String getClearPassword() {
		return clearPassword;
	}

	public void setClearPassword(String clearPassword) {
		this.clearPassword = clearPassword;
	}
	
	
}
