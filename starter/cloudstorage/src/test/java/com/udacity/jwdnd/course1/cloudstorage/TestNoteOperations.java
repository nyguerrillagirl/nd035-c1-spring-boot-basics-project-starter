package com.udacity.jwdnd.course1.cloudstorage;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestNoteOperations {
	private static final String TEST_USER_NAME = "fredflintstone";

	@LocalServerPort
	private int port;

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private NoteMapper noteMapper;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {

		if (this.driver != null) {
			driver.quit();
		}

		// clean up test user and note assets
		User user = userMapper.getUserByName(TEST_USER_NAME);
		if (user != null) {
			// clean  up any note assets		
			noteMapper.deleteAllUserNotes(user.getUserid());
			userMapper.delete(user.getUserid());
		}
	}
	   
	
	private HomePage signupAndLoginTestUser() throws InterruptedException {
		// Signup new user
		String password = "yabbadabbadoo";
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Fred", "Flintstone", TEST_USER_NAME, password);
		Thread.sleep(2000);


		// Login
		Assertions.assertEquals("Login", driver.getTitle());
		// Obtain the elements on Login page
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(TEST_USER_NAME, password);
		//Thread.sleep(2000);
		// Check that we are now at the home page
		HomePage homePage = new HomePage(driver);
		return homePage;
	}
	
	@Test
	public void testCreateANewNote() throws InterruptedException {
		
		HomePage homePage = signupAndLoginTestUser();
		
		homePage.waitForVisibility(driver, homePage.navNotesTab);
		// confirm that we are at the  home page
		assertTrue(homePage.atHomePage(driver));
		
		// Now perform a click on the note tab
		homePage.clickOnWebElement(homePage.navNotesTab);
		homePage.waitForVisibility(driver, homePage.addNoteButton);
	
		// Click on Create button
		homePage.clickOnWebElement(homePage.addNoteButton);
		homePage.waitForVisibility(driver, homePage.dialogNoteTitle);
		
		// Fill  in the new note 
		homePage.enterDataIntoElement(homePage.dialogNoteTitle, "Test Note");
		homePage.enterDataIntoElement(homePage.dialogNoteDescription, "If this works I am going to a) drink a beer b) let my hair down c) TikTok for 30 minutes!");
		
		// Click on submit button
		homePage.clickOnWebElement(homePage.dialogNoteSaveButton);
		homePage.waitForVisibility(driver, homePage.messageElement);
		
		// Check for success message
		assertTrue(homePage.doesHomePageMessageMatch("Successfully created note item."));
		
		// Navigate back to the note tab
		homePage.clickOnWebElement(homePage.navNotesTab);
		homePage.waitForVisibility(driver, homePage.addNoteButton);
		
		// Check list for new entry
		homePage = new HomePage(driver); // since new page let's re-populate elements
		
		assertTrue(homePage.doesElementTextMatch(homePage.noteTableTitle, "Test Note"));
		
		// Now logoff
		homePage.logout();
		Thread.sleep(2000);
		Assertions.assertEquals("Login", driver.getTitle());

	}
	
	@Test
	public void testEditExistingNote() throws InterruptedException {
		HomePage homePage = signupAndLoginTestUser();
		assertTrue(homePage.atHomePage(driver));
		
		// Now perform a click on the note tab
		homePage.clickOnWebElement(homePage.navNotesTab);
		homePage.waitForVisibility(driver, homePage.addNoteButton);
		
		// Click on Create button
		homePage.clickOnWebElement(homePage.addNoteButton);
		
		// Fill it in 
		homePage.enterDataIntoElement(homePage.dialogNoteTitle, "Test Note");
		homePage.enterDataIntoElement(homePage.dialogNoteDescription, "If this works I am going to a) drink a beer b) let my hair down c) TikTok for 30 minutes!");
		
		// Click on submit button
		homePage.clickOnWebElement(homePage.dialogNoteSaveButton);
		homePage.waitForVisibility(driver, homePage.messageElement);
		
		// Check for success message
		assertTrue(homePage.doesHomePageMessageMatch("Successfully created note item."));
		
		// Navigate back to the note tab
//		homePage.clickOnWebElement(homePage.navNotesTab);
		
		// Check list for new entry
		homePage = new HomePage(driver); // since new page let's re-populate elements
		
		homePage.clickOnWebElement(homePage.navNotesTab);
		homePage.waitForVisibility(driver, homePage.noteTableTitle);

		assertTrue(homePage.doesElementTextMatch(homePage.noteTableTitle, "Test Note"));
		
		// Select the note just created for editing
		homePage.clickOnWebElement(homePage.noteTableEditButton);
		homePage.waitForVisibility(driver, homePage.dialogNoteTitle);
		
		// Let's change the title
		homePage.enterDataIntoElement(homePage.dialogNoteTitle, "Freds goals");
		// Click on submit button
		homePage.clickOnWebElement(homePage.dialogNoteSaveButton);
		homePage.waitForVisibility(driver, homePage.messageElement);
		
		// Check that the title changed
		homePage = new HomePage(driver); // since new page let's re-populate elements
		
		
		// check the success message
		assertTrue(homePage.doesHomePageMessageMatch("Successfully updated note item."));
		
		homePage.clickOnWebElement(homePage.navNotesTab);
		homePage.waitForVisibility(driver, homePage.addNoteButton);

		// Check update
		assertTrue(homePage.doesElementTextMatch(homePage.noteTableTitle, "Freds goals"));
		
		
		// Now logoff
		homePage.logout();
		Thread.sleep(2000);
		Assertions.assertEquals("Login", driver.getTitle());		
	}
	
	
	@Test
	public void testDeleteANote() throws InterruptedException {
		HomePage homePage = signupAndLoginTestUser();
		assertTrue(homePage.atHomePage(driver));
		// Now perform a click on the note tab
		homePage.clickOnWebElement(homePage.navNotesTab);
		homePage.waitForVisibility(driver, homePage.addNoteButton);
		
		// Click on Create button
		homePage.clickOnWebElement(homePage.addNoteButton);
		
		// Fill it in 
		homePage.enterDataIntoElement(homePage.dialogNoteTitle, "Test Note");
		homePage.enterDataIntoElement(homePage.dialogNoteDescription, "If this works I am going to a) drink a beer b) let my hair down c) TikTok for 30 minutes!");
		
		// Click on submit button
		homePage.clickOnWebElement(homePage.dialogNoteSaveButton);
		homePage.waitForVisibility(driver, homePage.messageElement);
		
		// Check list for new entry
		homePage = new HomePage(driver); // since new page let's re-populate elements
		
		// Check for success message
		assertTrue(homePage.doesHomePageMessageMatch("Successfully created note item."));
		
		// Navigate back to the note tab
		homePage.clickOnWebElement(homePage.navNotesTab);
		homePage.waitForVisibility(driver, homePage.addNoteButton);
		

		assertTrue(homePage.doesElementTextMatch(homePage.noteTableTitle, "Test Note"));
		
		// Delete this note
		homePage.clickOnWebElement(homePage.noteTableDeleteButton);
		homePage.waitForVisibility(driver, homePage.messageElement);
		
		// Check list for new entry
		homePage = new HomePage(driver); // since new page let's re-populate elements
	
		
		// check the success message
		assertTrue(homePage.doesHomePageMessageMatch("Successfully deleted note item."));
		
		homePage.clickOnWebElement(homePage.navNotesTab);
		homePage.waitForVisibility(driver, homePage.addNoteButton);
				
		// Now logoff
		homePage.logout();
		Thread.sleep(2000);
		Assertions.assertEquals("Login", driver.getTitle());		
		
	}
}
