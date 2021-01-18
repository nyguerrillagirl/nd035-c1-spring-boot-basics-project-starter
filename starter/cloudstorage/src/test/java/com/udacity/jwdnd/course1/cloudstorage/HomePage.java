package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	
	private static final String HOME_TITLE = "Home";

	// Logout Button
	@FindBy(css = "#logoutButton")
	private WebElement logoutButton;

	// File-Notes-Credentials Tab
	@FindBy(css = "#nav-files-tab")
	private WebElement navFilesTab;
	
	@FindBy(css = "#nav-notes-tab")
	private WebElement navNotesTab;
	
	@FindBy(css = "#nav-credentials-tab")
	private WebElement navCredentialsTab;
	
	// Elements for Note Testing
	
	
	// Elements for Credential Testing
	
	
    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }
	
    public void logout() {
        this.logoutButton.click();
    }
    
    public boolean atHomePage(WebDriver driver) {
    	return driver.getTitle().equals(HOME_TITLE);
    }

}
