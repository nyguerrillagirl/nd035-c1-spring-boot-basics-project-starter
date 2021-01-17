package com.udacity.jwdnd.course1.cloudstorage.services.storage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteEntity;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;


@Service
public class NoteService extends StorageServiceUtility implements StorageService<NoteForm, NoteEntity>{
	
	@Autowired
	private NoteMapper mapper;

	@Override
	public List<NoteEntity> getUsersStoredData(Integer userid) {
		List<NoteEntity> notes = mapper.getAllUserNotes(userid);
		return notes;
	}

	@Override
	public void insertUserStoredData(NoteForm formRecord, String username) {
		NoteEntity noteRecord = new NoteEntity();
		// Get and then save owner of this note record
		User user = getUser(username);
		noteRecord.setUserid(user.getUserid());
		// Save note title and description
		noteRecord.setNotetitle(formRecord.getNoteTitle());
		noteRecord.setNotedescription(formRecord.getNoteDescription());
		
		mapper.insertNote(noteRecord);
		
	}

	@Override
	public void updateUserStoredData(NoteForm formRecord, String username) {
		// Get user record
		User user = getUser(username);
		
		Integer id = formRecord.getNoteId();
		
		// Get Existing note record
		NoteEntity noteRecord = mapper.findNote(id);
		
		if (noteRecord == null) {
			throw new StorageException(String.format("Note record %d not found for update.", id));
		}
		
		// Now check if this note record belongs to the user
		if (userOwnsResource(user, noteRecord.getUserid())) {
			// go ahead and update and save
			noteRecord.setNotedescription(formRecord.getNoteDescription());
			noteRecord.setNotetitle(formRecord.getNoteTitle());
			mapper.updateNote(noteRecord);
		} else {
			throw new StorageException(String.format("Note record does not belong to the user: %s.",
			        username));
		}
		
	}

	@Override
	public void deleteUserStoredData(Integer noteid, String username) {
		User user = getUser(username);
		NoteEntity noteRecord = mapper.findNote(noteid);
		
		if  (noteRecord == null) {
		  throw new StorageException("That record does not exist.");
		}
		if (userOwnsResource(user, noteRecord.getUserid())) {
		  // go ahead and delete
		  mapper.deleteNote(noteid);
		} else {
		  throw new StorageException(String.format("User %s does not own this note resource", username));
		}		
	}

	@Override
	public boolean additionalContraintsPassed(NoteEntity modelRecord) {
		// vacuously true
		return true;
	}
	


}
