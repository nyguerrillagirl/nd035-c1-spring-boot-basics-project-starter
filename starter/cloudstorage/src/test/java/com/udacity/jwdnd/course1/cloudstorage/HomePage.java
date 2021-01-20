package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	private static final String HOME_TITLE = "Home";
	private static final String EXAMPLE_NOTE_TITLE = "This is a test";
	private static final String EXAMPLE_NOTE_DESCRIPTION = "TODO: Write note test case for add, edit and delete.";
	// Logout Button
	@FindBy(id = "logoutButton")
	private WebElement logoutButton;

	// File-Notes-Credentials Tab
	@FindBy(id = "nav-files-tab")
	protected WebElement navFilesTab;

	@FindBy(id = "nav-notes-tab")
	protected WebElement navNotesTab;

	// Elements for Note Testing
	@FindBy(id = "noteAddBtn")
	protected WebElement addNoteButton;
	
	@FindBy(id = "nav-credentials-tab")
	protected WebElement navCredentialsTab;
	
	@FindBy(id = "note-title")
	protected WebElement dialogNoteTitle;
	
	@FindBy(id = "note-description")
	protected WebElement dialogNoteDescription;
	
	@FindBy(id = "noteDialogSaveBtn")
	protected WebElement dialogNoteSaveButton;
	
	@FindBy(id = "noteTableEditBtn")
	protected WebElement noteTableEditButton;

	@FindBy(id = "noteTableDeleteBtn")
	protected WebElement noteTableDeleteButton;
	
	@FindBy(id = "noteTableTitle")
	protected WebElement noteTableTitle;
	
	@FindBy(id = "noteTableDescription")
	protected WebElement noteTableDescription;
	
	
	
	private JavascriptExecutor javaScriptExecutor;

	public HomePage(WebDriver webDriver) {
		PageFactory.initElements(webDriver, this);
		javaScriptExecutor = (JavascriptExecutor) webDriver;
	}


	// Elements for Credential Testing

	public WebElement waitForVisibility(WebDriver driver, String elementId) {
		// Set so it does not wait any longer than 10 seconds
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementId)));
		return webElement;
	}
	
	public void clickOnWebElement(WebElement element) {
		javaScriptExecutor.executeScript("arguments[0].click();", element);
	}

	// Used to enter text data into the element (note or credential field)
	public void enterDataIntoElement(WebElement element, String data) {
		javaScriptExecutor.executeScript("arguments[0].value='" + data + "';", element);		
	}

	public boolean doesHomePageMessageMatch(String expectedMessage) {		
		boolean result = false;
		String text = javaScriptExecutor.executeScript("return document.getElementById('error-msg').innerHTML").toString();
		if (text != null) {
			result = text.contains(expectedMessage);
		}
		return result;
	}
	
	public boolean doesElementTextMatch(WebElement element, String expectedMessage) {
		boolean result = false;
		String text = element.getText();
		if (text != null) {
			result = text.equals(expectedMessage);
		}
		return result;
	}
	
	public void logout() {
		this.logoutButton.click();
	}

	public boolean atHomePage(WebDriver driver) {
		return driver.getTitle().equals(HOME_TITLE);
	}

}
