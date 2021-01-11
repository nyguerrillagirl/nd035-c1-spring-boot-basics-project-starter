package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
	
	public static final String LAST_NAME_PLACEHOLDER = "Enter Last Name";
	public static final String SUCCESS_SIGNUP_MESSAGE_START = "You successfully signed up!";
	private static final String LOGIN_LINK_TEXT = "Back to Login";
	
	@FindBy(id = "success-msg")
	private WebElement successMessageElement;
	
	@FindBy(id = "error-msg")
	private WebElement errorMessageElement;
	
    @FindBy(id = "inputFirstName")
    private WebElement firstNameField;

    @FindBy(id = "inputLastName")
    private WebElement lastNameField;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    public SignupPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void signup(String firstName, String lastName, String username, String password) {
        this.firstNameField.sendKeys(firstName);
        this.lastNameField.sendKeys(lastName);
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.submitButton.click();
    }
    
	public boolean isSignupSuccessful() {
		String message = successMessageElement.getText();
		return message.startsWith(SUCCESS_SIGNUP_MESSAGE_START);
	}
	
	public boolean isSignupFailure() {
		String message = errorMessageElement.getText();
		return message != null && !message.equals("");
	}	
	
	public void clickLoginPageLink(WebDriver driver) {
		driver.findElement(By.linkText(LOGIN_LINK_TEXT)).click();
	}
}
