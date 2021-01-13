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
import org.springframework.boot.web.server.LocalServerPort;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

import io.github.bonigarcia.wdm.WebDriverManager;

class TestHomePage {

	private static final String TEST_USER_NAME = "fredflintstone";

	@LocalServerPort
	private int port;

	@Autowired
	private UserMapper userMapper;

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

		// clean up test user
		User user = userMapper.getUserByName(TEST_USER_NAME);
		if (user != null) {
			userMapper.delete(user.getUserid());
		}
	}

	@Test
	void testLogoutOnHomePage() throws InterruptedException {
		// Signup new user
		String password = "yabbadabbadoo";
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Fred", "Flintstone", TEST_USER_NAME, password);
		Thread.sleep(2000);

		// Click login link
		signupPage.clickLoginPageLink(driver);
		Thread.sleep(2000);

		// Login
		Assertions.assertEquals("Login", driver.getTitle());
		// driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(TEST_USER_NAME, password);
		Thread.sleep(2000);

		// Check that we are now at the home page
		HomePage homePage = new HomePage(driver);
		assertTrue(homePage.atHomePage(driver));
		Thread.sleep(2000);
		
		// Click on logout link
		
		// Make sure user cannot get to home screen
		//TODO: Add specific instruction to test logout (e.g. something specific to home.html
	}
	


}
