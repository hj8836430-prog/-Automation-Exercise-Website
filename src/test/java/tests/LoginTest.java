package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.*;

public class LoginTest extends BaseTest {

    @Test(description = "TC2: Login User with correct email and password")
    public void testLoginUserWithCorrectEmailAndPassword() {
        String email = getUniqueEmail("testuser");
        String password = "Test@12345";

        HomePage loggedIn = registerAndLoginUser("TestUser", email, password);
        Assert.assertTrue(loggedIn.isLoggedIn(), "User not logged in after registration");

        loggedIn.logout();

        HomePage homeAfterLogin = loginUser(email, password);
        Assert.assertTrue(homeAfterLogin.isLoggedIn(), "'Logged in as username' not visible");

        AccountDeletedPage deleted = deleteUserAccount(homeAfterLogin);
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
        String email = getUniqueEmail("testuser");
        String password = "Test@12345";

        HomePage loggedIn = registerAndLoginUser("TestUser", email, password);
        Assert.assertTrue(loggedIn.isLoggedIn(), "User not logged in after registration");

        loggedIn.logout();

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoginPageLoaded(), "User not navigated to login page after logout");
    }
}
