package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.ContactPage;
import pages.HomePage;
import pages.TestCasesPage;

public class ContactTest extends BaseTest {

    @Test(description = "TC6: Contact Us Form")
    public void testContactUsForm() {
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page not visible");

        ContactPage contact = home.clickContactUs();
        Assert.assertTrue(contact.isGetInTouchVisible(), "'GET IN TOUCH' not visible");

        contact.enterName("Test User")
               .enterEmail("test@example.com")
               .enterSubject("Test Subject")
               .enterMessage("This is a test message")
               .uploadFile(System.getProperty("user.dir") + "\\src\\test\\resources\\testfile.txt")
               .submitForm();

        Assert.assertTrue(contact.isSuccessMessageVisible(), "Success message not visible");
        Assert.assertEquals(contact.getSuccessMessage(), "Success! Your details have been submitted successfully.", "Success message mismatch");

        HomePage homeAfter = contact.clickHome();
        Assert.assertTrue(homeAfter.isHomePageVisible(), "Not landed to home page successfully");
    }

    @Test(description = "TC7: Verify Test Cases Page")
    public void testVerifyTestCasesPage() {
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page not visible");

        TestCasesPage testCases = home.clickTestCases();
        Assert.assertTrue(testCases.isTestCasesPageVisible(), "User not navigated to test cases page successfully");
    }

    @Test(description = "TC10: Verify Subscription in home page")
    public void testVerifySubscriptionInHomePage() {
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page not visible");

        // Scroll down to footer
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");

        Assert.assertTrue(home.isSubscriptionVisible(), "Text 'SUBSCRIPTION' not visible");

        home.enterSubscriptionEmail("test@example.com")
            .clickSubscribe();

        Assert.assertTrue(home.isSubscriptionSuccessVisible(), "Success message not visible");
        Assert.assertEquals(home.getSubscriptionSuccessMessage(), "You have been successfully subscribed!", "Success message mismatch");
    }
}