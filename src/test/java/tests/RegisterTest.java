package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.*;

public class RegisterTest extends BaseTest {

    @Test(description = "TC1: Register new user with valid details and delete account")
    public void testRegisterNewUserAndDeleteAccount() {
        String email = "testuser" + System.currentTimeMillis() + "@mail.com";

        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page not visible");

        RegisterPage reg = home.clickSignupLogin().signup("AutoUser", email);
        Assert.assertTrue(reg.isEnterAccountInfoVisible(), "Account info form not visible");

        reg.selectTitle("Mr");
        reg.enterPassword("Test@12345");
        reg.selectDateOfBirth("10", "5", "1995");
        reg.selectNewsletter();
        reg.selectOffers();
        reg.fillAddressDetails("Auto", "User", "Test Corp", "123 Main St", "United States",
            "California", "Los Angeles", "90001", "9876543210");
        AccountCreatedPage created = reg.clickCreateAccount();

        Assert.assertTrue(created.isAccountCreated(), "Account created message not shown");

        HomePage loggedIn = created.clickContinue();
        Assert.assertTrue(loggedIn.isLoggedIn(), "User not logged in");
        Assert.assertEquals(loggedIn.getLoggedInUsername(), "AutoUser", "Username mismatch");

        AccountDeletedPage deleted = loggedIn.clickDeleteAccount();
        Assert.assertTrue(deleted.isAccountDeleted(), "Account deleted message not shown - verify you are on delete account page");
        deleted.clickContinue();
    }

    @Test(description = "TC2: Register with Mrs title and verify account creation")
    public void testRegisterWithMrsTitle() throws InterruptedException {
        String email = "mrsuser" + System.currentTimeMillis() + "@mail.com";

        HomePage home = new HomePage(driver);
        RegisterPage reg = home.clickSignupLogin().signup("Jane Smith", email);

        reg.selectTitle("Mrs");
        reg.enterPassword("Mrs@Secure99");
        reg.selectDateOfBirth("15", "8", "1990");
        reg.selectNewsletter();
        reg.selectOffers();
        reg.fillAddressDetails("Jane", "Smith", "Smith Ltd", "456 Oak Ave", "United States",
            "New York", "New York City", "10001", "1234567890");
        AccountCreatedPage created = reg.clickCreateAccount();

        Assert.assertTrue(created.isAccountCreated(), "Account not created");
        HomePage loggedIn = created.clickContinue();
        // Small wait for page to fully load
        Thread.sleep(1000);
        AccountDeletedPage deleted = loggedIn.clickDeleteAccount();
        Assert.assertTrue(deleted.isAccountDeleted(), "Account not deleted - verify delete account page loaded");
        deleted.clickContinue();
    }

    @Test(description = "TC3: Register with existing email shows error")
    public void testRegisterWithExistingEmail() {
        HomePage home = new HomePage(driver);
        LoginPage login = home.clickSignupLogin();
        login.signup("Duplicate User", "existing@example.com");

        String error = login.getSignupErrorMessage();
        Assert.assertFalse(error.isEmpty(), "No error for duplicate email");
        Assert.assertTrue(error.contains("Email Address already exist"), "Wrong error: " + error);
    }

    @Test(description = "TC4: Register with empty name should not proceed")
    public void testRegisterWithEmptyName() {
        HomePage home = new HomePage(driver);
        LoginPage login = home.clickSignupLogin();
        login.signup("", "emptyname@mail.com");
        Assert.assertTrue(login.isNewUserSignupVisible(), "Form submitted with empty name");
    }

    @Test(description = "TC5: Register with empty email should not proceed")
    public void testRegisterWithEmptyEmail() {
        HomePage home = new HomePage(driver);
        LoginPage login = home.clickSignupLogin();
        login.signup("ValidName", "");
        Assert.assertTrue(login.isNewUserSignupVisible(), "Form submitted with empty email");
    }

    @Test(description = "TC6: Register with invalid email format should not proceed")
    public void testRegisterWithInvalidEmail() {
        HomePage home = new HomePage(driver);
        LoginPage login = home.clickSignupLogin();
        login.signup("TestUser", "invalidemail");
        Assert.assertTrue(login.isNewUserSignupVisible(), "Form submitted with invalid email");
    }

    @Test(description = "TC7: Register user with existing email shows error")
    public void testRegisterWithExistingEmailShowsError() {
        // Step 1: Launch browser (handled by BaseTest @BeforeMethod)

        // Step 2: Navigate to url
        HomePage home = new HomePage(driver);

        // Step 3: Verify that home page is visible successfully
        Assert.assertTrue(home.isHomePageVisible(), "Home page is not visible");

        // Step 4: Click on 'Signup / Login' button
        LoginPage login = home.clickSignupLogin();

        // Step 5: Verify 'New User Signup!' is visible
        Assert.assertTrue(login.isNewUserSignupVisible(), "'New User Signup!' is not visible");

        // Step 6: Enter name and already registered email address
        String existingEmail = "existinguser" + System.currentTimeMillis() + "@mail.com";

        // First register the user successfully
        RegisterPage reg = login.signup("ExistingUser", existingEmail);
        reg.selectTitle("Mr");
        reg.enterPassword("Test@12345");
        reg.selectDateOfBirth("10", "5", "1995");
        reg.selectNewsletter();
        reg.selectOffers();
        reg.fillAddressDetails("Auto", "User", "Test Corp", "123 Main St", "United States",
            "California", "Los Angeles", "90001", "9876543210");
        AccountCreatedPage created = reg.clickCreateAccount();
        Assert.assertTrue(created.isAccountCreated(), "Account created message not shown");

        // Now go back to home and try to register again with SAME email
        HomePage loggedIn = created.clickContinue();
        loggedIn.logout();

        // Navigate to signup again
        home = new HomePage(driver);
        login = home.clickSignupLogin();

        // Step 7: Click 'Signup' button with existing email
        login.signup("DuplicateUser", existingEmail);

        // Step 8: Verify error 'Email Address already exist!' is visible
        String error = login.getSignupErrorMessage();
        Assert.assertFalse(error.isEmpty(), "No error message displayed for existing email");
        Assert.assertTrue(error.contains("Email Address already exist"),
            "Expected 'Email Address already exist!' but got: " + error);
    }

    @Test(description = "TC8: Logout user")
    public void testLogoutUser() {
        // Step 1: Launch browser (handled by BaseTest @BeforeMethod)

        // Step 2: Navigate to url
        HomePage home = new HomePage(driver);

        // Step 3: Verify that home page is visible successfully
        Assert.assertTrue(home.isHomePageVisible(), "Home page is not visible");

        // Step 4: Click on 'Signup / Login' button
        LoginPage login = home.clickSignupLogin();

        // Step 5: Verify 'Login to your account' is visible
        Assert.assertTrue(login.isLoginPageLoaded(), "'Login to your account' is not visible");

        // Step 6: Enter correct email address and password
        // First we need to create an account to login with
        String email = "logoutuser" + System.currentTimeMillis() + "@mail.com";
        RegisterPage reg = login.signup("LogoutUser", email);
        reg.selectTitle("Mr");
        reg.enterPassword("Test@12345");
        reg.selectDateOfBirth("10", "5", "1995");
        reg.selectNewsletter();
        reg.selectOffers();
        reg.fillAddressDetails("Auto", "User", "Test Corp", "123 Main St", "United States",
            "California", "Los Angeles", "90001", "9876543210");
        AccountCreatedPage created = reg.clickCreateAccount();
        Assert.assertTrue(created.isAccountCreated(), "Account created message not shown");

        // Step 7: Click 'login' button - Continue to home, then logout
        HomePage loggedIn = created.clickContinue();

        // Step 8: Verify that 'Logged in as username' is visible
        Assert.assertTrue(loggedIn.isLoggedIn(), "'Logged in as username' is not visible");
        Assert.assertEquals(loggedIn.getLoggedInUsername(), "LogoutUser", "Username mismatch");

        // Step 9: Click 'Logout' button
        loggedIn.logout();

        // Step 10: Verify that user is navigated to login page
        // After logout, user should be on home page, navigate to login to verify
        home = new HomePage(driver);
        login = home.clickSignupLogin();
        Assert.assertTrue(login.isLoginPageLoaded(), "User not navigated to login page after logout");
    }
}



