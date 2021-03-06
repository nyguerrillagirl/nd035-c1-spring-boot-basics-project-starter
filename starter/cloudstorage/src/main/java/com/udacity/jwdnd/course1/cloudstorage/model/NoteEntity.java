package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteEntity {

	private Integer noteid;
	private String notetitle;
	private String notedescription;
	private Integer userid;
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[noteid: " + noteid);
		sb.append(";notetitle: " + notetitle);
		sb.append("\nnotedesription:\n" + notedescription + "\n");
		sb.append(";userid: " + userid + "]");
		
		return sb.toString();
	}
	
	public Integer getNoteid() {
		return noteid;
	}
	public void setNoteid(Integer noteid) {
		this.noteid = noteid;
	}
	public String getNotetitle() {
		return notetitle;
	}
	public void setNotetitle(String notetitle) {
		this.notetitle = notetitle;
	}
	public String getNotedescription() {
		return notedescription;
	}
	public void setNotedescription(String notedescription) {
		this.notedescription = notedescription;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	
}
