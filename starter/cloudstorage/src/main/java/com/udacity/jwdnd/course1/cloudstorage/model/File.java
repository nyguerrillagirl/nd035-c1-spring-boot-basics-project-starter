package com.udacity.jwdnd.course1.cloudstorage.model;

import java.sql.Blob;

public class File {

	private Integer fileId;
	private String filename;
	private String contenttype;
	private String filesize;
	private Integer userid;
	private Blob filedata;
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[fileId: " + fileId);
		sb.append(";filename: " + filename);
		sb.append(";contenttype: " + contenttype);
		sb.append(";filesize: " + filesize);
		sb.append(";userid: " + userid + "]");
		
		return sb.toString();
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getContenttype() {
		return contenttype;
	}
	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Blob getFiledata() {
		return filedata;
	}
	public void setFiledata(Blob filedata) {
		this.filedata = filedata;
	}
	
	
}
