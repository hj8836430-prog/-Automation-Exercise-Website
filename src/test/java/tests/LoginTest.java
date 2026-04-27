package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.*;

public class LoginTest extends BaseTest {

    private String getUniqueEmail() {
        return "testuser" + System.currentTimeMillis() + "@mail.com";
    }

    private void fillRegistrationForm(RegisterPage reg) {
        reg.selectTitle("Mr").enterPassword("Test@12345")
           .selectDateOfBirth("10", "5", "1995").selectNewsletter().selectOffers()
           .fillAddressDetails("Auto", "User", "Test Corp", "123 Main St", "United States",
               "California", "Los Angeles", "90001", "9876543210");
    }

    @Test(description = "TC2: Login User with correct email and password")
    public void testLoginUserWithCorrectEmailAndPassword() {
        // First register a user
        String email = getUniqueEmail();
        String password = "Test@12345";

        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page not visible");

        RegisterPage reg = home.clickSignupLogin().signup("TestUser", email);
        fillRegistrationForm(reg);
        HomePage loggedIn = reg.clickCreateAccount().clickContinue();
        Assert.assertTrue(loggedIn.isLoggedIn(), "User not logged in after registration");

        // Logout
        loggedIn.logout();

        // Now login
        LoginPage loginPage = new HomePage(driver).clickSignupLogin();
        Assert.assertTrue(loginPage.isLoginPageLoaded(), "'Login to your account' not visible");

        HomePage homeAfterLogin = loginPage.login(email, password);
        Assert.assertTrue(homeAfterLogin.isLoggedIn(), "'Logged in as username' not visible");

        // Delete account
        AccountDeletedPage deleted = homeAfterLogin.clickDeleteAccount();
        Assert.assertTrue(deleted.isAccountDeleted(), "'ACCOUNT DELETED!' not visible");
        deleted.clickContinue();
    }

    @Test(description = "TC3: Login User with incorrect email and password")
    public void testLoginWithInvalidCredentials() {
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page not visible");

        LoginPage login = home.clickSignupLogin();
        Assert.assertTrue(login.isLoginPageLoaded(), "'Login to your account' not visible");

        login.login("invalid@example.com", "wrongpassword");

        String error = login.getLoginErrorMessage();
        Assert.assertEquals(error, "Your email or password is incorrect!", "Error message mismatch");
    }

    @Test(description = "TC4: Logout User")
    public void testLogout() {
        // First register a user
        String email = getUniqueEmail();
        String password = "Test@12345";

        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page not visible");

        RegisterPage reg = home.clickSignupLogin().signup("TestUser", email);
        fillRegistrationForm(reg);
        HomePage loggedIn = reg.clickCreateAccount().clickContinue();
        Assert.assertTrue(loggedIn.isLoggedIn(), "User not logged in after registration");

        // Logout
        loggedIn.logout();

        // Verify navigated to login page
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoginPageLoaded(), "User not navigated to login page after logout");
    }
}
