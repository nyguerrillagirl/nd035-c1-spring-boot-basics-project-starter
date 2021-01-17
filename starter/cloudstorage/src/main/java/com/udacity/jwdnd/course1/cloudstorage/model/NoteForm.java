package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteForm {

	private String noteTitle;
	private String noteDescription;
	
	// On update
	private Integer noteId;

	public String getNoteTitle() {
		return noteTitle;
	}

	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}

	public String getNoteDescription() {
		return noteDescription;
	}

	public void setNoteDescription(String noteDescription) {
		this.noteDescription = noteDescription;
	}

	public Integer getNoteId() {
		return noteId;
	}

	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}
	
	
}
