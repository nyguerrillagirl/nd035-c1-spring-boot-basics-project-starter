package com.udacity.jwdnd.course1.cloudstorage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestCredentialOperations {

	private static final String TEST_USER_NAME = "fredflintstone";
	private static final String TEST_URL = "http://www.brainycode.com";
	private static final String TEST_SITE_USERNAME  = "nyguerrillagirl";
	private static final String TEST_SITE_PASSWORD = "password123";
	
	private static final String TEST_SITE_USERNAME_UPDATE = "lafigueroa";

	@LocalServerPort
	private int port;

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private CredentialMapper credentialMapper;

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

		// clean up test user and credential assets
		User user = userMapper.getUserByName(TEST_USER_NAME);
		if (user != null) {
			// clean  up any note assets		
			credentialMapper.deleteAllUserCredentials(user.getUserid());
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
	public void testCreateANewCredential() throws InterruptedException {
		
		HomePage homePage = signupAndLoginTestUser();
		
		homePage.waitForVisibility(driver, homePage.navCredentialsTab);
		// confirm that we are at the  home page
		assertTrue(homePage.atHomePage(driver));
		
		// Now perform a click on the credential tab
		homePage.clickOnWebElement(homePage.navCredentialsTab);
		homePage.waitForVisibility(driver, homePage.addCredentialButton);
	
		// Click on Create button
		homePage.clickOnWebElement(homePage.addCredentialButton);
		homePage.waitForVisibility(driver, homePage.dialogCredentialUrl);
		
		// Fill  in the new credential 
		homePage.enterDataIntoElement(homePage.dialogCredentialUrl, TEST_URL);
		homePage.enterDataIntoElement(homePage.dialogCredentialUsername, TEST_SITE_USERNAME);
		homePage.enterDataIntoElement(homePage.dialogCredentialPassword, TEST_SITE_PASSWORD);
		
		// Click on submit button
		homePage.clickOnWebElement(homePage.dialogCredentialSubmit);
		homePage.waitForVisibility(driver, homePage.messageElement);
		
		// Check for success message
		assertTrue(homePage.doesHomePageMessageMatch("Successfully created credential item."));
		
		// Navigate back to the credential tab
		homePage.clickOnWebElement(homePage.navCredentialsTab);
		homePage.waitForVisibility(driver, homePage.addCredentialButton);
		
		// Check list for new entry
		homePage = new HomePage(driver); // since new page let's re-populate elements
		
		assertTrue(homePage.doesElementTextMatch(homePage.credentialTableUrl, TEST_URL));
		
		// Now logoff
		homePage.logout();
		Thread.sleep(2000);
		Assertions.assertEquals("Login", driver.getTitle());

	}
	
	@Test
	public void testEditExistingCredential() throws InterruptedException {
		HomePage homePage = signupAndLoginTestUser();
		assertTrue(homePage.atHomePage(driver));
		
		// Now perform a click on the credential tab
		homePage.clickOnWebElement(homePage.navCredentialsTab);
		homePage.waitForVisibility(driver, homePage.addCredentialButton);
		
		// Click on Create button
		homePage.clickOnWebElement(homePage.addCredentialButton);
		
		// Fill it in 
		homePage.enterDataIntoElement(homePage.dialogCredentialUrl, TEST_URL);
		homePage.enterDataIntoElement(homePage.dialogCredentialUsername, TEST_SITE_USERNAME);
		homePage.enterDataIntoElement(homePage.dialogCredentialPassword, TEST_SITE_PASSWORD);
		
		// Click on submit button
		homePage.clickOnWebElement(homePage.dialogCredentialSubmit);
		homePage.waitForVisibility(driver, homePage.messageElement);
		
		// Check for success message
		assertTrue(homePage.doesHomePageMessageMatch("Successfully created credential item."));
		
		
		// Check list for new entry
		homePage = new HomePage(driver); // since new page let's re-populate elements
		
		homePage.clickOnWebElement(homePage.navCredentialsTab);
		homePage.waitForVisibility(driver, homePage.addCredentialButton);

		assertTrue(homePage.doesElementTextMatch(homePage.credentialTableUrl, TEST_URL));
		
		// Select the note just created for editing
		homePage.clickOnWebElement(homePage.credentialTableEditButton);
		homePage.waitForVisibility(driver, homePage.dialogCredentialUrl);
		
		// Let's change the title
		homePage.enterDataIntoElement(homePage.dialogCredentialUsername, TEST_SITE_USERNAME_UPDATE);
		
		// Click on submit button
		homePage.clickOnWebElement(homePage.dialogCredentialSubmit);
		homePage.waitForVisibility(driver, homePage.messageElement);
		
		// Check that the title changed
		homePage = new HomePage(driver); // since new page let's re-populate elements
		
		
		// check the success message
		assertTrue(homePage.doesHomePageMessageMatch("Successfully updated credential item."));
		
		homePage.clickOnWebElement(homePage.navCredentialsTab);
		homePage.waitForVisibility(driver, homePage.addCredentialButton);

		// Check update
		assertTrue(homePage.doesElementTextMatch(homePage.credentialTableUsername, TEST_SITE_USERNAME_UPDATE));
		
		
		// Now logoff
		homePage.logout();
		Thread.sleep(2000);
		Assertions.assertEquals("Login", driver.getTitle());		
	}
	
	
	@Test
	public void testDeleteACredential() throws InterruptedException {
		HomePage homePage = signupAndLoginTestUser();
		assertTrue(homePage.atHomePage(driver));
		// Now perform a click on the credential tab
		homePage.clickOnWebElement(homePage.navCredentialsTab);
		homePage.waitForVisibility(driver, homePage.addCredentialButton);
		
		// Click on Create button
		homePage.clickOnWebElement(homePage.addCredentialButton);
		
		// Fill it in 
		homePage.enterDataIntoElement(homePage.dialogCredentialUrl, TEST_URL);
		homePage.enterDataIntoElement(homePage.dialogCredentialUsername, TEST_SITE_USERNAME);
		homePage.enterDataIntoElement(homePage.dialogCredentialPassword, TEST_SITE_PASSWORD);
		
		// Click on submit button
		homePage.clickOnWebElement(homePage.dialogCredentialSubmit);
		homePage.waitForVisibility(driver, homePage.messageElement);
		
		// Check list for new entry
		homePage = new HomePage(driver); // since new page let's re-populate elements
		
		// Check for success message
		assertTrue(homePage.doesHomePageMessageMatch("Successfully created credential item."));
		
		// Navigate back to the credential tab
		homePage.clickOnWebElement(homePage.navCredentialsTab);
		homePage.waitForVisibility(driver, homePage.addCredentialButton);
		

		assertTrue(homePage.doesElementTextMatch(homePage.credentialTableUrl, TEST_URL));
		
		// Delete this note
		homePage.clickOnWebElement(homePage.credentialTableDeleteButton);
		homePage.waitForVisibility(driver, homePage.messageElement);
		
		// Check list for new entry
		homePage = new HomePage(driver); // since new page let's re-populate elements
	
		
		// check the success message
		assertTrue(homePage.doesHomePageMessageMatch("Successfully deleted credential item."));
		
		homePage.clickOnWebElement(homePage.navCredentialsTab);
		homePage.waitForVisibility(driver, homePage.addCredentialButton);
				
		// Now logoff
		homePage.logout();
		Thread.sleep(2000);
		Assertions.assertEquals("Login", driver.getTitle());		
		
	}


}
