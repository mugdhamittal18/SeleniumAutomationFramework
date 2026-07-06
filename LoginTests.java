package com.ecommerce.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.ecommerce.base.BaseTest;
import com.ecommerce.pages.LoginPage;

/**
 * Test Cases for Login Functionality
 * Demonstrates: TestNG framework, test organization, assertions
 */
public class LoginTests extends BaseTest {
    
    private LoginPage loginPage;
    
    @Override
    public void beforeMethod() {
        super.beforeMethod();
        driver.get("https://www.example-ecommerce.com/login");
        loginPage = new LoginPage(driver);
    }
    
    /**
     * Test Case 1: Valid Login
     * Scenario: User logs in with correct credentials
     * Expected: User redirected to dashboard
     */
    @Test(priority = 1, description = "Test valid login with correct credentials")
    public void testValidLogin() {
        // Arrange
        String username = "testuser@gmail.com";
        String password = "TestPassword123";
        
        // Act
        loginPage.login(username, password);
        
        // Assert
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.example-ecommerce.com/dashboard");
        Assert.assertTrue(driver.findElement(By.className("dashboard-header")).isDisplayed());
    }
    
    /**
     * Test Case 2: Invalid Password
     * Scenario: User enters wrong password
     * Expected: Error message displayed
     */
    @Test(priority = 2, description = "Test login with invalid password")
    public void testInvalidPassword() {
        // Arrange
        String username = "testuser@gmail.com";
        String wrongPassword = "WrongPassword123";
        
        // Act
        loginPage.login(username, wrongPassword);
        
        // Assert
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Invalid credentials"), 
                         "Error message should contain 'Invalid credentials'");
    }
    
    /**
     * Test Case 3: Empty Username
     * Scenario: User tries to login without entering username
     * Expected: Validation error for empty username
     */
    @Test(priority = 3, description = "Test login with empty username")
    public void testEmptyUsername() {
        // Act
        loginPage.enterPassword("TestPassword123");
        loginPage.clickLoginButton();
        
        // Assert
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"));
    }
    
    /**
     * Test Case 4: Empty Password
     * Scenario: User tries to login without entering password
     * Expected: Validation error for empty password
     */
    @Test(priority = 4, description = "Test login with empty password")
    public void testEmptyPassword() {
        // Act
        loginPage.enterUsername("testuser@gmail.com");
        loginPage.clickLoginButton();
        
        // Assert
        Assert.assertTrue(loginPage.getErrorMessage().contains("Password is required"));
    }
    
    /**
     * Test Case 5: SQL Injection Prevention
     * Scenario: User tries SQL injection attack in password field
     * Expected: Attack prevented, error message displayed
     */
    @Test(priority = 5, description = "Test SQL injection prevention")
    public void testSQLInjectionPrevention() {
        // Arrange - SQL injection payload
        String username = "testuser@gmail.com";
        String sqlInjectionPayload = "' OR '1'='1";
        
        // Act
        loginPage.login(username, sqlInjectionPayload);
        
        // Assert - Should not bypass login
        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertFalse(driver.getCurrentUrl().contains("dashboard"));
    }
    
    /**
     * Test Case 6: Forgot Password Link
     * Scenario: User clicks forgot password link
     * Expected: Forgot password page opens
     */
    @Test(priority = 6, description = "Test forgot password link")
    public void testForgotPasswordLink() {
        // Act
        loginPage.clickForgotPassword();
        
        // Assert
        Assert.assertTrue(driver.getCurrentUrl().contains("forgot-password"));
        Assert.assertTrue(driver.findElement(By.className("forgot-password-form")).isDisplayed());
    }
    
    /**
     * Test Case 7: Login Page Load Verification
     * Scenario: Verify login page loads correctly
     * Expected: All login elements visible
     */
    @Test(priority = 7, description = "Test login page loads correctly")
    public void testLoginPageLoad() {
        // Assert
        Assert.assertTrue(loginPage.isLoginPageLoaded(), "Login page should be loaded");
        Assert.assertTrue(driver.getTitle().contains("Login"), "Page title should contain 'Login'");
    }
    
    /**
     * Test Case 8: Remember Me Functionality
     * Scenario: User checks remember me checkbox and logs in
     * Expected: User credentials saved for next login
     */
    @Test(priority = 8, description = "Test remember me functionality")
    public void testRemembeMeFunctionality() {
        // Act
        loginPage.enterUsername("testuser@gmail.com");
        loginPage.clickRememberMe();
        loginPage.enterPassword("TestPassword123");
        loginPage.clickLoginButton();
        
        // Navigate back to login
        driver.navigate().back();
        
        // Assert - Username should be pre-filled
        Assert.assertEquals(loginPage.getUsernameFieldValue(), "testuser@gmail.com");
    }
    
    @Override
    public void afterMethod() {
        super.afterMethod();
    }
}

/* 
 * TestNG Annotations Used:
 * @Test - Marks method as test case
 * @BeforeMethod - Runs before each test (setup)
 * @AfterMethod - Runs after each test (cleanup)
 * 
 * Test Naming Convention:
 * testActionExpectedResult()
 * Example: testInvalidPassword() - test invalid password scenario
 * 
 * Assertion Examples:
 * Assert.assertTrue() - check if condition is true
 * Assert.assertEquals() - check if values are equal
 * Assert.assertFalse() - check if condition is false
 */
