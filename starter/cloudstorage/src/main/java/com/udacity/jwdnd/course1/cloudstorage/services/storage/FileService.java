package com.udacity.jwdnd.course1.cloudstorage.services.storage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;

@Service
public class FileService {
	
	@Autowired
	private FileMapper mapper;
	
	public List<File> getUsersStoredData(Integer userid) {
		return mapper.getAllUserFiles(userid);
	}
	
	public void insertUserStoredData(File aFileRecord) {

	}
	
	public void updateUserStoredData(File aFileRecord) {
		
	}
	
	public void deleteUserStoredData(Integer fileId) {
		
	}

}
