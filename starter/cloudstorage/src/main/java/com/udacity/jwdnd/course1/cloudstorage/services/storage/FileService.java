package com.udacity.jwdnd.course1.cloudstorage.services.storage;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileEntity;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;


@Service
public class FileService extends StorageServiceUtility implements StorageService<FileForm, FileEntity>{
	private static Logger logger = LoggerFactory.getLogger(FileService.class);
	
	@Autowired
	private FileMapper mapper;
	
	private final Path rootLocation;

	@Autowired
	public FileService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}
	
	public FileEntity getFileEntity(Integer fileId, String username) {
		User user = getUser(username);
		FileEntity fileEntity = mapper.findFile(fileId);
		if (fileEntity == null) {
			throw new StorageException("Failed to find file record.");
		}
		
		if (userOwnsResource(user, fileEntity.getUserid())) {
			// Sent the record back
			return fileEntity;
		} else {
			throw new StorageException("User does not own that file resource.");
		}
	}
	
	@Override
	public List<FileEntity> getUsersStoredData(Integer userid) {
		List<FileEntity> fileLst = mapper.getAllUserFiles(userid);
		return fileLst;
	}

	@Override
	public void insertUserStoredData(FileForm formRecord, String username) {
		MultipartFile uploadedFile = formRecord.getFile();
		
		if (uploadedFile.isEmpty()) {
			throw new StorageException("Failed to store empty file.");
		}
		
		FileEntity fileEntity = new FileEntity();
		// Get and then save into file the user/owner of this storage unit
		User user = getUser(username);
		fileEntity.setUserid(user.getUserid());

		// Process the file
		Path destinationFile = this.rootLocation.resolve(
				Paths.get(uploadedFile.getOriginalFilename()))
				.normalize().toAbsolutePath();
		if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
			// This is a security check
			throw new StorageException(
					"Cannot store file outside current directory.");
		}
		try (InputStream inputStream = uploadedFile.getInputStream()) {
			byte[] data = IOUtils.toByteArray(inputStream);
			fileEntity.setFiledata(data);
		} catch (Exception e) {
			logger.warn(ExceptionUtils.getStackTrace(e));
			throw new StorageException(e.getMessage());
		}
		
		// Get and save file information
		fileEntity.setContenttype(uploadedFile.getContentType());
		fileEntity.setFilesize(String.valueOf(uploadedFile.getSize()));
		fileEntity.setFilename(destinationFile.getFileName().toString());
		if (additionalContraintsPassed(fileEntity)) {
			mapper.insertFile(fileEntity);	
		} else {
			throw new StorageException("File has already been previously uploaded.");
		}
	}

	
	@Override
	public void updateUserStoredData(FileForm formRecord, String username) {
		throw new StorageException("Update function not supported for files.");		
	}

	@Override
	public void deleteUserStoredData(Integer fileId, String username) {
		User user = getUser(username);
		FileEntity fileEntity = mapper.findFile(fileId);
		if (fileEntity == null) {
			throw new StorageException("That record does not exist.");
		}
		
		if (userOwnsResource(user, fileEntity.getUserid())) {
			mapper.deleteFile(fileId);
		} else {
			throw new StorageException(String.format("User %s does not own this file resource", username));
		}
	}
	
	@Override
	public boolean additionalContraintsPassed(FileEntity modelRecord) {
		// See if we can find record in db with this userid and filename
		FileEntity dbRecord = mapper.findFileByUserIdAndFilename(modelRecord.getUserid(), modelRecord.getFilename());
		return dbRecord == null;
	}
	

}
