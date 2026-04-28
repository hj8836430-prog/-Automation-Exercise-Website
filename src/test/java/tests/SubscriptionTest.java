package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.*;

public class SubscriptionTest extends BaseTest {

    @Test(description = "TC10: Verify Subscription in home page")
    public void testSubscriptionOnHomePage() {
        // Step 1: Launch browser (handled by BaseTest @BeforeMethod)

        // Step 2: Navigate to url
        HomePage home = new HomePage(driver);

        // Step 3: Verify that home page is visible successfully
        Assert.assertTrue(home.isHomePageVisible(), "Home page is not visible");

        // Step 4: Scroll down to footer
        home.scrollToBottom();

        // Step 5: Verify text 'SUBSCRIPTION'
        Assert.assertTrue(home.isSubscriptionVisible(), "'SUBSCRIPTION' text not visible");

        // Step 6: Enter email address in input and click arrow button
        String email = "subscriber" + System.currentTimeMillis() + "@mail.com";
        home.enterSubscriptionEmail(email);
        home.clickSubscribe();

        // Step 7: Verify success message 'You have been successfully subscribed!' is visible
        Assert.assertTrue(home.isSubscriptionSuccessVisible(), "Subscription success message not visible");
        Assert.assertTrue(home.getSubscriptionSuccessMessage().contains("successfully subscribed"),
            "Wrong success message: " + home.getSubscriptionSuccessMessage());
    }

    @Test(description = "TC11: Verify Subscription in Cart page")
    public void testSubscriptionOnCartPage() {
        // Step 1: Launch browser (handled by BaseTest @BeforeMethod)

        // Step 2: Navigate to url
        HomePage home = new HomePage(driver);

        // Step 3: Verify that home page is visible successfully
        Assert.assertTrue(home.isHomePageVisible(), "Home page is not visible");

        // Step 4: Click 'Cart' button
        CartPage cart = home.clickCart();

        // Step 5: Scroll down to footer
        cart.scrollToBottom();

        // Step 6: Verify text 'SUBSCRIPTION'
        Assert.assertTrue(cart.isSubscriptionVisible(), "'SUBSCRIPTION' text not visible on cart page");

        // Step 7: Enter email address in input and click arrow button
        String email = "carts subscriber" + System.currentTimeMillis() + "@mail.com";
        cart.enterSubscriptionEmail(email);
        cart.clickSubscribe();

        // Step 8: Verify success message 'You have been successfully subscribed!' is visible
        Assert.assertTrue(cart.isSubscriptionSuccessVisible(), "Subscription success message not visible on cart page");
        Assert.assertTrue(cart.getSubscriptionSuccessMessage().contains("successfully subscribed"),
            "Wrong success message: " + cart.getSubscriptionSuccessMessage());
    }
}
