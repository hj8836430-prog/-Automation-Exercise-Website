package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import base.BaseTest;
import java.util.UUID;

public class PlaceOrderRegisterCheckoutTest extends BaseTest {

    @Test
    public void testPlaceOrderRegisterWhileCheckout() {
        HomePage homePage = new HomePage(driver);

        // 1. Launch browser and Navigate to url 'http://automationexercise.com' (Handled by BaseTest)

        // 2. Verify that home page is visible successfully
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");

        // 3. Add products to cart
        ProductsPage productsPage = homePage.clickProducts();

        // Add a product directly from the products page without searching to avoid the timeout
        productsPage.clickAddToCart(1);
        productsPage.clickContinueShopping();
        productsPage.clickAddToCart(2);

        // 4. Click 'Cart' button
        CartPage cartPage = homePage.clickCart();

        // 5. Verify that cart page is displayed
        Assert.assertTrue(!cartPage.isCartEmpty(), "Cart should not be empty");

        // 6. Click Proceed To Checkout
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        // 7. Click 'Register / Login' button
        LoginPage loginPage = checkoutPage.clickRegisterLogin();

        // 8. Fill all details in Signup and create account
        String username = "user_" + UUID.randomUUID().toString().substring(0, 8);
        String password = "Password123!";

        RegisterPage registerPage = loginPage.signup("TestUser", "testuser_" + UUID.randomUUID().toString().substring(0, 5) + "@example.com");
        registerPage.selectTitle("Mr")
                   .enterPassword(password)
                   .selectDateOfBirth("10", "May", "1990")
                   .selectNewsletter()
                   .selectOffers()
                   .fillAddressDetails(
                       "Firstname", "Lastname", "Company",
                       "Address 123", "United States", "State",
                       "City", "12345", "1234567890"
                   );

        AccountCreatedPage accountCreatedPage = registerPage.clickCreateAccount();

        // 9. Verify 'ACCOUNT CREATED!' and click 'Continue' button
        Assert.assertTrue(accountCreatedPage.isAccountCreated(), "Account should be created");
        homePage = accountCreatedPage.clickContinue();

        // 10. Verify ' Logged in as username' at top
        Assert.assertTrue(homePage.isLoggedIn(), "User should be logged in");

        // 11. Click 'Cart' button
        cartPage = homePage.clickCart();

        // 12. Click 'Proceed To Checkout' button
        checkoutPage = cartPage.proceedToCheckout();

        // 13. Verify Address Details and Review Your Order
        Assert.assertTrue(checkoutPage.isDeliveryAddressVisible(), "Delivery address should be visible");
        Assert.assertTrue(checkoutPage.isBillingAddressVisible(), "Billing address should be visible");

        // 14. Enter description in comment text area and click 'Place Order'
        checkoutPage.enterOrderComment("Please deliver safely")
                    .clickPlaceOrder();

        // 15. Enter payment details: Name on Card, Card Number, CVC, Expiration date
        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.enterCardDetails(
            "First Last", "1234567890123456", "123", "05", "2026"
        );

        // 16. Click 'Pay and Confirm Order' button
        paymentPage.clickPayAndConfirm();

        // 17. Verify success message 'Your order has been placed successfully!'
        Assert.assertTrue(paymentPage.isOrderPlacedSuccessfully(), "Order success message should be displayed");

        // 18. Click 'Delete Account' button
        homePage = paymentPage.clickContinue();
        AccountDeletedPage accountDeletedPage = homePage.clickDeleteAccount();

        // 19. Verify 'ACCOUNT DELETED!' and click 'Continue' button
        Assert.assertTrue(accountDeletedPage.isAccountDeleted(), "Account should be deleted");
        accountDeletedPage.clickContinue();
    }
}
