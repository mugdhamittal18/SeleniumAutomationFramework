package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;
import com.ecommerce.utils.WaitUtils;

/**
 * Page Object Model for Login Page
 * Demonstrates: Centralized element location, reusable methods
 */
public class LoginPage {
    
    private WebDriver driver;
    private WaitUtils waitUtils;
    
    // Web Elements using @FindBy annotation
    @FindBy(id = "username")
    private WebElement usernameField;
    
    @FindBy(id = "password")
    private WebElement passwordField;
    
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;
    
    @FindBy(className = "error-message")
    private WebElement errorMessage;
    
    @FindBy(linkText = "Forgot Password?")
    private WebElement forgotPasswordLink;
    
    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }
    
    // Methods (Actions users perform on login page)
    
    /**
     * Enter username in username field
     * @param username - username to enter
     */
    public void enterUsername(String username) {
        waitUtils.waitForElementToBeVisible(usernameField);
        usernameField.clear();
        usernameField.sendKeys(username);
    }
    
    /**
     * Enter password in password field
     * @param password - password to enter
     */
    public void enterPassword(String password) {
        waitUtils.waitForElementToBeVisible(passwordField);
        passwordField.clear();
        passwordField.sendKeys(password);
    }
    
    /**
     * Click login button
     */
    public void clickLoginButton() {
        waitUtils.waitForElementToBeClickable(loginButton);
        loginButton.click();
    }
    
    /**
     * Complete login process - combines all actions
     * @param username - username
     * @param password - password
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
    
    /**
     * Get error message displayed after failed login
     * @return error message text
     */
    public String getErrorMessage() {
        waitUtils.waitForElementToBeVisible(errorMessage);
        return errorMessage.getText();
    }
    
    /**
     * Check if error message is displayed
     * @return true if error message visible, false otherwise
     */
    public boolean isErrorMessageDisplayed() {
        try {
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if login page is loaded
     * @return true if login button is visible
     */
    public boolean isLoginPageLoaded() {
        try {
            return waitUtils.waitForElementToBeVisible(loginButton) != null;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Click forgot password link
     */
    public void clickForgotPassword() {
        waitUtils.waitForElementToBeClickable(forgotPasswordLink);
        forgotPasswordLink.click();
    }
}

/* 
 * Benefits of Page Object Model:
 * 1. If username field ID changes from "username" to "user_name", 
 *    we update ONLY in this file, not in 50 test files
 * 2. Reusable methods - login() method used in multiple tests
 * 3. Readable - test code is like English, not HTML locators
 * 4. Maintainable - easy to find and fix issues
 * 5. Scalable - add new methods without changing tests
 */
