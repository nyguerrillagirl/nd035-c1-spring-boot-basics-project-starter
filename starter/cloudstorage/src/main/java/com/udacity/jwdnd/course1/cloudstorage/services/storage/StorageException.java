package com.udacity.jwdnd.course1.cloudstorage.services.storage;

public class StorageException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StorageException(String message) {
		super(message);
	}
}
