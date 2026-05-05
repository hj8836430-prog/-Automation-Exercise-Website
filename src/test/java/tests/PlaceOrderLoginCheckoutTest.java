package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import base.BaseTest;

public class PlaceOrderLoginCheckoutTest extends BaseTest {

    @Test
    public void testPlaceOrderLoginBeforeCheckout() {
        HomePage homePage = new HomePage(driver);

        // 2. Verify that home page is visible successfully
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");

        // 3. Click 'Signup / Login' button
        LoginPage loginPage = homePage.clickSignupLogin();

        // 4. Fill email, password and click 'Login' button
        // Note: In a real scenario, these credentials would come from a pre-existing user
        // For automation, we assume a user was previously created or use a known test account.
        // Since we need to delete the account at the end, we must ensure the account exists.
        // To make this test self-contained, we would typically create the user first,
        // but the test case specifies "Login before Checkout".

        // For the purpose of this exercise, I will use a dynamically created account
        // by performing a quick signup first, or using a utility to ensure a user exists.
        // However, following the exact steps provided:

        // We need a valid user to login. I'll create one first to ensure the test passes.
        String email = "testuser_" + System.currentTimeMillis() + "@example.com";
        String password = "Password123!";

        // Pre-requisite: Create account so we can login
        homePage.clickSignupLogin().signup("Test User", email);
        new RegisterPage(driver)
            .selectTitle("Mr")
            .enterPassword(password)
            .selectDateOfBirth("10", "5", "1990")
            .selectNewsletter()
            .selectOffers()
            .fillAddressDetails("First", "Last", "Company", "Address", "United States", "State", "City", "12345", "1234567890")
            .clickCreateAccount();

        // Now follow Test Case 16 steps
        // Re-initialize homePage because clickCreateAccount returns a new HomePage or keeps current driver
        homePage = new HomePage(driver);
        loginPage = homePage.clickSignupLogin();
        loginPage.login(email, password);
        homePage = new HomePage(driver);

        // 5. Verify 'Logged in as username' at top
        Assert.assertTrue(homePage.isLoggedIn(), "User should be logged in");

        // 6. Add products to cart
        ProductsPage productsPage = homePage.clickProducts();
        productsPage.clickAddToCart(1);
        productsPage.clickContinueShopping();
        productsPage.clickAddToCart(2);

        // 7. Click 'Cart' button
        CartPage cartPage = homePage.clickCart();

        // 8. Verify that cart page is displayed
        Assert.assertTrue(!cartPage.isCartEmpty(), "Cart should not be empty");

        // 9. Click Proceed To Checkout
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        // 10. Verify Address Details and Review Your Order
        Assert.assertTrue(checkoutPage.isDeliveryAddressVisible(), "Delivery address should be visible");
        Assert.assertTrue(checkoutPage.isBillingAddressVisible(), "Billing address should be visible");

        // 11. Enter description in comment text area and click 'Place Order'
        checkoutPage.enterOrderComment("Please deliver safely")
                    .clickPlaceOrder();

        // 12. Enter payment details: Name on Card, Card Number, CVC, Expiration date
        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.enterCardDetails("First Last", "1234567890123456", "123", "05", "2026");

        // 13. Click 'Pay and Confirm Order' button
        paymentPage.clickPayAndConfirm();

        // 14. Verify success message 'Your order has been placed successfully!'
        Assert.assertTrue(paymentPage.isOrderPlacedSuccessfully(), "Order success message should be displayed");

        // 15. Click 'Delete Account' button
        homePage = paymentPage.clickContinue();
        AccountDeletedPage accountDeletedPage = homePage.clickDeleteAccount();

        // 16. Verify 'ACCOUNT DELETED!' and click 'Continue' button
        Assert.assertTrue(accountDeletedPage.isAccountDeleted(), "Account should be deleted");
        accountDeletedPage.clickContinue();
    }
}
