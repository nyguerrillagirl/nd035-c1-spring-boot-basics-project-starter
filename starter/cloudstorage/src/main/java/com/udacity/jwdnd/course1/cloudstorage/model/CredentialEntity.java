package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialEntity {

	protected Integer credentialid;
	protected String url;
	protected String username;
	protected String key;
	protected String password;
	protected Integer userid;
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[credentialid: " + credentialid);
		sb.append(";url: " + url);
		sb.append(";username: " + username);
		sb.append(";key: " + key);
		sb.append(";password: " + password);
		sb.append(";userid: " + userid + "]");
		return sb.toString();
		
	}
	
	public CredentialEntity() {}
	
	public CredentialEntity(CredentialEntity credential) {
		this.credentialid = credential.getCredentialid();
		this.url = credential.getUrl();
		this.username = credential.getUsername();
		this.key = credential.getKey();
		this.password = credential.getPassword();
		this.userid = credential.getUserid();
	}
	
	public Integer getCredentialid() {
		return credentialid;
	}
	public void setCredentialid(Integer credentialid) {
		this.credentialid = credentialid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	
}
