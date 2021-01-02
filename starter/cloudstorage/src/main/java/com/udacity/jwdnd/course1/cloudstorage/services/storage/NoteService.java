package com.udacity.jwdnd.course1.cloudstorage.services.storage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;


@Service
public class NoteService {
	
	@Autowired
	private NoteMapper mapper;
	
	public List<Note> getUsersStoredData(Integer userid) {
		return mapper.getAllUserNotes(userid);
	}
	
	public void insertUserStoredData(Note aNoteRecord) {

	}
	
	public void updateUserStoredData(Note aNoteRecord) {
		
	}
	
	public void deleteUserStoredData(Integer nodeid) {
		
	}

}
