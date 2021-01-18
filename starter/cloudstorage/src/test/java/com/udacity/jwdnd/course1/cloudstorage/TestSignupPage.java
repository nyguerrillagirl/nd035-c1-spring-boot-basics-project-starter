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

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestSignupPage {

	private static final String TEST_USER_NAME = "pzastoup";

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@Autowired
	private UserMapper userMapper;

	public String baseURL;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
		// clean up test user
		User user = userMapper.getUserByName(TEST_USER_NAME);
		if (user != null) {
			userMapper.delete(user.getUserid());
		}

	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void testUserSignupSuccess() throws InterruptedException {
		String password = "whatabadpassword";
		String messageText = "Hello!";

		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Peter", "Zastoupil", TEST_USER_NAME, password);

		Thread.sleep(2000);
		// Check for Login Page
		Assertions.assertEquals("Login", driver.getTitle());

	}

	@Test
	public void testUserSignupFailure() throws InterruptedException {
		String password = "whatabadpassword";
		String messageText = "Hello!";

		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Peter", "Zastoupil", TEST_USER_NAME, password);
		Thread.sleep(2000);

		// Make sure we are still on the signup page
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(baseURL + "/signup");
		
		// Signup again - same user as before
		driver.get(baseURL + "/signup");
		signupPage = new SignupPage(driver);
		signupPage.signup("John", "Doe", TEST_USER_NAME, "johndoeisCrazy!");
		Thread.sleep(2000);
		// Check for failure message
		assertTrue(signupPage.isSignupFailure());

	}

}
