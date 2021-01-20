package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.common.CloudStorageConstants;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.FileEntity;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.storage.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.storage.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.storage.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.storage.StorageException;

@Controller
@RequestMapping("/home")
public class HomeController {
	private static Logger logger = LoggerFactory.getLogger(HomeController.class);

	private static final String HOME_MESSAGE_FIELD = "messageField";

	@Autowired
	private FileService fileService;

	@Autowired
	private NoteService noteService;

	@Autowired
	private CredentialService credentialService;

	@Autowired
	private UserService userService;

	@GetMapping
	public String getHomePage() {
		return "home";
	}

	// ---------- CREDENTIAL ----------
	@GetMapping("/credential/delete/{credentialid}")
	public String deleteCredential(@PathVariable("credentialid") Integer credentialid, Authentication authentication,
			Model model, RedirectAttributes redirectAttributes) {

		String storageError = null;
		String userName = authentication.getName();
		try {
			credentialService.deleteUserStoredData(credentialid, userName);
			//model.addAttribute(HOME_MESSAGE_FIELD, "Successfully deleted credential item.");
			redirectAttributes.addFlashAttribute(HOME_MESSAGE_FIELD, "Successfully deleted credential item.");
		} catch (StorageException e) {
			storageError = e.getMessage();
			//model.addAttribute(HOME_MESSAGE_FIELD, storageError);
			redirectAttributes.addFlashAttribute(HOME_MESSAGE_FIELD, storageError);
		}
		allUserFiles(authentication, model);
		return "redirect:/home";
	}

	@PostMapping("/credential/addOrUpdate")
	public String addOrUpdateCredential(Authentication authentication, CredentialForm credentialForm, Model model, RedirectAttributes redirectAttributes) {
		String signupError = null;
		String userName = authentication.getName();
		try {
			// need to determine if this is an insert or update
			if (credentialForm.getCredentialId() == null) {
				// new entry
				credentialService.insertUserStoredData(credentialForm, userName);
				redirectAttributes.addFlashAttribute(HOME_MESSAGE_FIELD, "Successfully created credential item.");
			} else {
				credentialService.updateUserStoredData(credentialForm, userName);
				redirectAttributes.addFlashAttribute(HOME_MESSAGE_FIELD, "Successfully updated credential item.");
			}

		} catch (StorageException e) {
			signupError = e.getMessage();
			redirectAttributes.addFlashAttribute(HOME_MESSAGE_FIELD, signupError);
		}
		allUserFiles(authentication, model);
		return "redirect:/home";
	}

	// ---------- NOTE ----------
	@GetMapping("/note/delete/{noteid}")
	public String deleteNote(@PathVariable("noteid") Integer noteid, Authentication authentication, Model model, RedirectAttributes redirectAttributes) {

		String storageError = null;
		String userName = authentication.getName();
		try {
			noteService.deleteUserStoredData(noteid, userName);
			redirectAttributes.addFlashAttribute(HOME_MESSAGE_FIELD, "Successfully deleted note item.");
		} catch (StorageException e) {
			storageError = e.getMessage();
			redirectAttributes.addFlashAttribute(HOME_MESSAGE_FIELD, storageError);
		}
		allUserFiles(authentication, model);
		return "redirect:/home";
	}

	@PostMapping("/note/addOrUpdate")
	public String addOrUpdateNote(Authentication authentication, NoteForm noteForm, Model model, RedirectAttributes redirectAttributes) {
		String storageError = null;
		String userName = authentication.getName();
		try {
			// need to determine if this is an insert or update
			if (noteForm.getNoteId() == null) {
				// new entry
				noteService.insertUserStoredData(noteForm, userName);
				redirectAttributes.addFlashAttribute(HOME_MESSAGE_FIELD, "Successfully created note item.");
			} else {
				// update
				noteService.updateUserStoredData(noteForm, userName);
				redirectAttributes.addFlashAttribute(HOME_MESSAGE_FIELD, "Successfully updated note item.");
			}

		} catch (StorageException e) {
			storageError = e.getMessage();
			redirectAttributes.addFlashAttribute(HOME_MESSAGE_FIELD, storageError);
		}
		allUserFiles(authentication, model);
		return "redirect:/home";
	}

	// ---------- FILE ----------
	@ModelAttribute("fileForm")
	public FileForm getFileForm() {
		return new FileForm();
	}

	@PostMapping("/file/upload")
	public String fileUpload(Authentication authentication, @ModelAttribute("fileForm") MultipartFile file,
			Model model, RedirectAttributes redirectAttributes) {
		String storageError = null;
		String userName = authentication.getName();
		try {
			FileForm fileForm = getFileForm();
			fileForm.setFile(file);
			fileService.insertUserStoredData(fileForm, userName);
			redirectAttributes.addFlashAttribute(HOME_MESSAGE_FIELD, "Successfully uploaded/saved file.");

		} catch (StorageException e) {
			storageError = e.getMessage();
			redirectAttributes.addFlashAttribute(HOME_MESSAGE_FIELD, storageError);
		}

		allUserFiles(authentication, model);

		return "redirect:/home";
	}

	@GetMapping("/file/delete/{fileId}")
	public String fileDelete(@PathVariable("fileId") Integer fileId, Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
		String storageError = null;
		String userName = authentication.getName();
		try {
			fileService.deleteUserStoredData(fileId, userName);
			redirectAttributes.addFlashAttribute(HOME_MESSAGE_FIELD, "Successfully deleted file.");
		} catch (StorageException e) {
			storageError = e.getMessage();
			redirectAttributes.addFlashAttribute(HOME_MESSAGE_FIELD, storageError);
		}

		allUserFiles(authentication, model);
		return "redirect:/home";
	}

	@GetMapping("/file/view/{fileId}")
	public ResponseEntity<ByteArrayResource> fileView(@PathVariable("fileId") Integer fileId,
			Authentication authentication, Model model) {
		String userName = authentication.getName();
		FileEntity fileEntity = fileService.getFileEntity(fileId, userName);
		ByteArrayResource resource = new ByteArrayResource(fileEntity.getFiledata());
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(fileEntity.getContenttype()))
//				.contentType(MediaType.APPLICATION_OCTET_STREAM)
//				.contentType(new MediaType(fileEntity.getContenttype()))
				.contentLength(Long.parseLong(fileEntity.getFilesize()))
				.header("Content-Disposition", "attachment; filename=" + fileEntity.getFilename())
				.body(resource);

	}
	
	
	

	@ModelAttribute
	public void allUserFiles(Authentication authentication, Model model) {
		String userName = authentication.getName();
		logger.info("HomeController - allUserFiles: " + userName);
		User userRecord = userService.getUser(userName);
		if (userRecord != null) {
			logger.info("HomeController.allUserFiles - userRecord found.");
			Integer userId = userRecord.getUserid();
			model.addAttribute(CloudStorageConstants.FILE_LIST, fileService.getUsersStoredData(userId));
			model.addAttribute(CloudStorageConstants.CREDENTIAL_LIST, credentialService.getUsersStoredData(userId));
			model.addAttribute(CloudStorageConstants.NOTE_LIST, noteService.getUsersStoredData(userId));
		}
	}
}
