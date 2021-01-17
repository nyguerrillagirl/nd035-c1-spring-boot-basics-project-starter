package com.udacity.jwdnd.course1.cloudstorage.util;

import java.util.ArrayList;
import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialEntity;
import com.udacity.jwdnd.course1.cloudstorage.model.FileEntity;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteEntity;

public class TestSupportUtil {

	public static List<FileEntity> getDummyFileList() {
		List<FileEntity> userFiles = new ArrayList<FileEntity>();
		FileEntity fileEntry1 = new FileEntity();
		fileEntry1.setFileId(1000);
		fileEntry1.setFilename("FILE_ENTRY_1.txt");
		fileEntry1.setContenttype("text/plain");
		fileEntry1.setFilesize("100");
		fileEntry1.setUserid(1);
		userFiles.add(fileEntry1);
		
		FileEntity fileEntry2 = new FileEntity();
		fileEntry2.setFileId(1001);
		fileEntry2.setFilename("FILE_ENTRY_2.txt");
		fileEntry2.setContenttype("text/plain");
		fileEntry2.setFilesize("200");
		fileEntry2.setUserid(1);
		userFiles.add(fileEntry2);
		
		FileEntity fileEntry3 = new FileEntity();
		fileEntry3.setFileId(1002);
		fileEntry3.setFilename("FILE_ENTRY_3.txt");
		fileEntry3.setContenttype("text/plain");
		fileEntry3.setFilesize("1100");
		fileEntry3.setUserid(1);
		userFiles.add(fileEntry3);
		
		return userFiles;
	}
	
	public static List<NoteEntity> getDummyNoteList() {
		List<NoteEntity> userNotes = new ArrayList<NoteEntity>();
		
		NoteEntity noteEntry1 = new NoteEntity();
		noteEntry1.setNoteid(100);
		noteEntry1.setNotetitle("JWDND Note 1");
		noteEntry1.setNotedescription("This is a reminder to work on project 1 everyday until it is done.");
		noteEntry1.setUserid(1);
		userNotes.add(noteEntry1);
		
		NoteEntity noteEntry2 = new NoteEntity();
		noteEntry2.setNoteid(101);
		noteEntry2.setNotetitle("JWDND Note 2");
		noteEntry2.setNotedescription("Start Lesson 3 on Java Web Services with REST/API/Microservices");
		noteEntry2.setUserid(1);
		userNotes.add(noteEntry2);
		
		NoteEntity noteEntry3 = new NoteEntity();
		noteEntry3.setNoteid(102);
		noteEntry3.setNotetitle("JWDND Note 3");
		noteEntry3.setNotedescription("Check all provided references.");
		noteEntry3.setUserid(1);
		userNotes.add(noteEntry3);
		
		
		
		return userNotes;
	}
	
	public static List<CredentialEntity> getDummyCredentialList() {
		List<CredentialEntity> userCredentials = new ArrayList<CredentialEntity>();
		
		CredentialEntity credentialEntry1 = new CredentialEntity();
		credentialEntry1.setCredentialid(2000);
		credentialEntry1.setUrl("http://www.brainycode.com");
		credentialEntry1.setUsername("bob");
		credentialEntry1.setPassword("bobbybrownToday2021");
		credentialEntry1.setUserid(1);
		userCredentials.add(credentialEntry1);

		CredentialEntity credentialEntry2 = new CredentialEntity();
		credentialEntry2.setCredentialid(2001);
		credentialEntry2.setUrl("http://www.youtube.com");
		credentialEntry2.setUsername("samantha");
		credentialEntry2.setPassword("toss_me_up_put_me_down!");
		credentialEntry2.setUserid(1);
		userCredentials.add(credentialEntry2);
		
		
		CredentialEntity credentialEntry3= new CredentialEntity();
		credentialEntry3.setCredentialid(2002);
		credentialEntry3.setUrl("http://www.packtpub.com");
		credentialEntry3.setUsername("freeme");
		credentialEntry3.setPassword("password123");
		credentialEntry3.setUserid(1);
		userCredentials.add(credentialEntry3);

		
		return userCredentials;
	}
}
