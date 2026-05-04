package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.*;

public class ProductsTest extends BaseTest {

    // Unique email generate karne ke liye helper method
    private String getUniqueEmail() {
        return "testuser" + System.currentTimeMillis() + "@mail.com";
    }

    // Test Case 8: Products page pe navigate karo
    @Test(description = "TC8: Navigate to products page")
    public void testNavigateToProductsPage() {
        // Home page se Products button click karo
        ProductsPage products = new HomePage(driver).clickProducts();

        // Check karo ki Products page load hui hai
        Assert.assertTrue(products.isProductsPageLoaded(), "Products page load nahi hui");
        Assert.assertTrue(products.getProductCount() > 0, "Koi products display nahi ho rahe");
    }

    // Test Case 9: Product search functionality
    @Test(description = "TC9: Search product functionality")
    public void testSearchProduct() {
        // Home page check karo
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page visible nahi hai");

        // Products page pe jao
        ProductsPage products = home.clickProducts();
        Assert.assertTrue(products.isProductsPageLoaded(), "ALL PRODUCTS page pe nahi gaya");

        // Product search karo
        products.searchProduct("Top");
        Assert.assertTrue(products.isSearchResultsVisible(), "'SEARCHED PRODUCTS' visible nahi hai");
        Assert.assertTrue(products.getProductCount() > 0, "Search se related saare products visible nahi hai");
    }

    // Test Case 11: Cart page pe Subscription verify karo
    @Test(description = "TC11: Verify Subscription in Cart page")
    public void testVerifySubscriptionInCartPage() {
        // Home page check karo
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page visible nahi hai");

        // Cart page pe jao
        CartPage cart = home.clickCart();

        // Footer tak scroll karo
        home.scrollToBottom();

        // Subscription text check karo
        Assert.assertTrue(cart.isSubscriptionVisible(), "'SUBSCRIPTION' text visible nahi hai");

        // Email enter karo aur subscribe karo
        cart.enterSubscriptionEmail("test@example.com");
        cart.clickSubscribe();

        // Success message check karo
        Assert.assertTrue(cart.isSubscriptionSuccessVisible(), "Success message visible nahi hai");
        Assert.assertEquals(cart.getSubscriptionSuccessMessage(), "You have been successfully subscribed!", "Success message match nahi kar raha");
    }
    // Test Case 8: All Products aur product detail page verify karo
    @Test(description = "TC8: Verify All Products and product detail page")
    public void testViewProductDetails() {
        // Home page check karo
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page visible nahi hai");

        // Products page pe jao
        ProductsPage products = home.clickProducts();
        Assert.assertTrue(products.isProductsPageLoaded(), "ALL PRODUCTS page pe nahi gaya");

        // Products list visible hai check karo
        Assert.assertTrue(products.getProductCount() > 0, "Products list visible nahi hai");

        // First product ka detail page kholo
        ProductDetailPage detail = products.clickViewProduct(1);

        // Saare product details check karo
        Assert.assertNotNull(detail.getProductName(), "Product name visible nahi hai");
        Assert.assertNotNull(detail.getProductCategory(), "Product category visible nahi hai");
        Assert.assertNotNull(detail.getProductPrice(), "Product price visible nahi hai");
        Assert.assertNotNull(detail.getAvailability(), "Product availability visible nahi hai");
        Assert.assertNotNull(detail.getProductCondition(), "Product condition visible nahi hai");
        Assert.assertNotNull(detail.getProductBrand(), "Product brand visible nahi hai");
    }

    // Test Case 12: Cart me Products add karo
    @Test(description = "TC12: Add Products in Cart")
    public void testAddProductToCart() {
        // Home page check karo
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page visible nahi hai");

        // Products page pe jao
        ProductsPage products = home.clickProducts();

        // First product ko cart me add karo
        products.clickAddToCart(1);
        products.clickContinueShopping();

        // Second product ko cart me add karo
        products.clickAddToCart(2);
        CartPage cart = products.clickViewCartFromModal();

        // Check karo ki dono products cart me add hue
        Assert.assertEquals(cart.getCartProductCount(), 2, "Dono products cart me add nahi hue");

        // Prices, quantities aur totals check karo
        java.util.List<String> prices = cart.getCartPrices();
        java.util.List<String> quantities = cart.getCartQuantities();
        java.util.List<String> totals = cart.getCartTotals();

        Assert.assertEquals(prices.size(), 2, "Dono products ke prices display nahi ho rahe");
        Assert.assertEquals(quantities.size(), 2, "Dono products ki quantities display nahi ho rahi");
        Assert.assertEquals(totals.size(), 2, "Dono products ke totals display nahi ho rahe");

        // Quantity 1 hai check karo for each product
        Assert.assertTrue(quantities.stream().allMatch(q -> q.equals("1")), "Har product ki quantity 1 nahi hai");
    }

    // Test Case 13: Cart me Product quantity verify karo
    @Test(description = "TC13: Verify Product quantity in Cart")
    public void testVerifyProductQuantityInCart() {
        // Home page check karo
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page visible nahi hai");

        // Products page se first product ka detail page kholo
        ProductsPage products = home.clickProducts();
        ProductDetailPage detail = products.clickViewProduct(1);

        // Check karo ki product detail page open hua
        Assert.assertNotNull(detail.getProductName(), "Product detail page open nahi hua");

        // Quantity ko 4 set karo
        detail.setQuantity("4");

        // 'Add to cart' button click karo
        detail.clickAddToCart();

        // 'View Cart' button click karo
        CartPage cart = detail.clickViewCartFromModal();

        // Check karo ki product cart me add hua
        Assert.assertEquals(cart.getCartProductCount(), 1, "Product cart me add nahi hua");
        java.util.List<String> quantities = cart.getCartQuantities();
        Assert.assertTrue(quantities.size() > 0, "Cart me quantity information nahi mili");

        String actualQuantity = quantities.get(0).trim();
        System.out.println("Debug: Actual quantity in cart: '" + actualQuantity + "'");

        Assert.assertTrue(actualQuantity.contains("4"), "Cart me quantity 4 nahi hai. Actual found: " + actualQuantity);
    }

    // Test Case 14: Checkout ke time Register karo aur Order place karo
    @Test(description = "TC14: Place Order: Register while Checkout")
    public void testCheckoutAndPlaceOrder() {
        // Step 1: Home page check karo
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page visible nahi hai");

        // Step 2: Products ko cart me add karo
        ProductsPage products = home.clickProducts();
        products.clickAddToCart(1);
        products.clickContinueShopping();

        // Step 3: Cart page pe jao
        CartPage cart = home.clickCart();
        Assert.assertTrue(cart.getCartProductCount() > 0, "Cart page display nahi hua");

        // Step 4: Checkout pe jao
        CheckoutPage checkout = cart.proceedToCheckout();

        // Step 5: Register/Login button click karo
        LoginPage login = checkout.clickRegisterLogin();

        // Step 6: Naya account create karo
        String email = getUniqueEmail();
        RegisterPage reg = login.signup("TestUser", email);
        reg.selectTitle("Mr")
           .enterPassword("Test@12345")
           .selectDateOfBirth("10", "5", "1995")
           .selectNewsletter()
           .selectOffers()
           .fillAddressDetails("Test", "User", "Test Corp", "123 Main St", "United States",
               "California", "Los Angeles", "90001", "9876543210");

        AccountCreatedPage created = reg.clickCreateAccount();

        // Step 7: Account creation verify karo
        Assert.assertTrue(created.isAccountCreated(), "'ACCOUNT CREATED!' visible nahi hai");
        HomePage loggedIn = created.clickContinue();

        // Step 8: Login verify karo
        Assert.assertTrue(loggedIn.isLoggedIn(), "'Logged in as username' visible nahi hai");

        // Step 9: Cart page pe wapas jao
        cart = loggedIn.clickCart();

        // Step 10: Checkout pe jao
        checkout = cart.proceedToCheckout();

        // Step 11: Address details aur order review check karo
        Assert.assertTrue(checkout.isDeliveryAddressVisible(), "Delivery address visible nahi hai");
        Assert.assertTrue(checkout.getOrderItemCount() > 0, "Order items review nahi ho rahe");

        // Step 12: Order comment add karo aur Place Order karo
        PaymentPage payment = checkout.enterOrderComment("Test order comment").clickPlaceOrder();

        // Step 13: Payment details enter karo
        payment.enterCardDetails("Test User", "1234567890123456", "123", "12", "2025");
        payment.clickPayAndConfirm();

        // Step 14: Success message verify karo
        Assert.assertTrue(payment.isOrderPlacedSuccessfully(), "Order success message visible nahi hai");
        Assert.assertEquals(payment.getOrderSuccessMessage(), "Your order has been placed successfully!", "Success message match nahi kar raha");

        // Step 15: Account delete karo
        AccountDeletedPage deleted = loggedIn.clickDeleteAccount();
        Assert.assertTrue(deleted.isAccountDeleted(), "'ACCOUNT DELETED!' visible nahi hai");
        deleted.clickContinue();
    }

    @Test(description = "TC15: Place Order: Register before Checkout")
    public void testPlaceOrderRegisterBeforeCheckout() {
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page not visible");

        // Click 'Signup / Login' button
        LoginPage login = home.clickSignupLogin();

        // Fill all details in Signup and create account
        String email = getUniqueEmail();
        RegisterPage reg = login.signup("TestUser", email);
        reg.selectTitle("Mr").enterPassword("Test@12345")
           .selectDateOfBirth("10", "5", "1995").selectNewsletter().selectOffers()
           .fillAddressDetails("Test", "User", "Test Corp", "123 Main St", "United States",
               "California", "Los Angeles", "90001", "9876543210");

        AccountCreatedPage created = reg.clickCreateAccount();

        // Verify 'ACCOUNT CREATED!' and click 'Continue' button
        Assert.assertTrue(created.isAccountCreated(), "'ACCOUNT CREATED!' not visible");
        HomePage loggedIn = created.clickContinue();

        // Verify 'Logged in as username' at top
        Assert.assertTrue(loggedIn.isLoggedIn(), "'Logged in as username' not visible");

        // Add products to cart
        ProductsPage products = loggedIn.clickProducts();
        products.clickAddToCart(1);
        products.clickContinueShopping();

        // Click 'Cart' button
        CartPage cart = loggedIn.clickCart();

        // Verify that cart page is displayed
        Assert.assertTrue(cart.getCartProductCount() > 0, "Cart page not displayed");

        // Click Proceed To Checkout
        CheckoutPage checkout = cart.proceedToCheckout();

        // Verify Address Details and Review Your Order
        Assert.assertTrue(checkout.isDeliveryAddressVisible(), "Address Details not visible");
        Assert.assertTrue(checkout.getOrderItemCount() > 0, "Order items not reviewed");

        // Enter description in comment text area and click 'Place Order'
        PaymentPage payment = checkout.enterOrderComment("Test order comment").clickPlaceOrder();

        // Enter payment details
        payment.enterCardDetails("Test User", "1234567890123456", "123", "12", "2025");

        // Click 'Pay and Confirm Order' button
        payment.clickPayAndConfirm();

        // Verify success message 'Your order has been placed successfully!'
        Assert.assertTrue(payment.isOrderPlacedSuccessfully(), "Success message not visible");
        Assert.assertEquals(payment.getOrderSuccessMessage(), "Your order has been placed successfully!", "Success message mismatch");

        // Click 'Delete Account' button
        AccountDeletedPage deleted = loggedIn.clickDeleteAccount();

        // Verify 'ACCOUNT DELETED!' and click 'Continue' button
        Assert.assertTrue(deleted.isAccountDeleted(), "'ACCOUNT DELETED!' not visible");
        deleted.clickContinue();
    }

    @Test(description = "TC16: Place Order: Login before Checkout")
    public void testPlaceOrderLoginBeforeCheckout() {
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page not visible");

        // Click 'Signup / Login' button
        LoginPage login = home.clickSignupLogin();

        // Fill email, password and click 'Login' button
        HomePage loggedIn = login.login("testuser@mail.com", "Test@12345");

        // Verify 'Logged in as username' at top
        Assert.assertTrue(loggedIn.isLoggedIn(), "'Logged in as username' not visible");

        // Add products to cart
        ProductsPage products = loggedIn.clickProducts();
        products.clickAddToCart(1);
        products.clickContinueShopping();

        // Click 'Cart' button
        CartPage cart = loggedIn.clickCart();

        // Verify that cart page is displayed
        Assert.assertTrue(cart.getCartProductCount() > 0, "Cart page not displayed");

        // Click Proceed To Checkout
        CheckoutPage checkout = cart.proceedToCheckout();

        // Verify Address Details and Review Your Order
        Assert.assertTrue(checkout.isDeliveryAddressVisible(), "Address Details not visible");
        Assert.assertTrue(checkout.getOrderItemCount() > 0, "Order items not reviewed");

        // Enter description in comment text area and click 'Place Order'
        PaymentPage payment = checkout.enterOrderComment("Test order comment").clickPlaceOrder();

        // Enter payment details: Name on Card, Card Number, CVC, Expiration date
        payment.enterCardDetails("Test User", "1234567890123456", "123", "12", "2025");

        // Click 'Pay and Confirm Order' button
        payment.clickPayAndConfirm();

        // Verify success message 'Your order has been placed successfully!'
        Assert.assertTrue(payment.isOrderPlacedSuccessfully(), "Success message not visible");
        Assert.assertEquals(payment.getOrderSuccessMessage(), "Your order has been placed successfully!", "Success message mismatch");

        // Click 'Delete Account' button
        AccountDeletedPage deleted = loggedIn.clickDeleteAccount();

        // Verify 'ACCOUNT DELETED!' and click 'Continue' button
        Assert.assertTrue(deleted.isAccountDeleted(), "'ACCOUNT DELETED!' not visible");
        deleted.clickContinue();
    }

    @Test(description = "TC17: Remove Products From Cart")
    public void testRemoveProductsFromCart() {
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page not visible");

        // Add products to cart
        ProductsPage products = home.clickProducts();
        products.clickAddToCart(1);
        products.clickContinueShopping();
        products.clickAddToCart(2);
        products.clickContinueShopping();

        // Click 'Cart' button
        CartPage cart = home.clickCart();

        // Verify that cart page is displayed
        Assert.assertTrue(cart.getCartProductCount() > 0, "Cart page not displayed");

        // Get initial product count
        int initialCount = cart.getCartProductCount();
        Assert.assertTrue(initialCount >= 2, "At least 2 products should be in cart");

        // Click 'X' button corresponding to particular product (remove first product)
        cart.removeProduct(1);

        // Verify that product is removed from the cart
        int finalCount = cart.getCartProductCount();
        Assert.assertEquals(finalCount, initialCount - 1, "Product was not removed from cart");
    }

    @Test(description = "TC18: View Category Products")
    public void testViewCategoryProducts() {
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page not visible");

        // Verify that categories are visible on left side bar
        Assert.assertTrue(home.areCategoriesVisible(), "Categories not visible on left side bar");

        // Click on 'Women' category
        home.clickWomenCategory();

        // Click on any category link under 'Women' category, for example: Dress
        ProductsPage womenDressPage = home.clickWomenDressCategory();

        // Verify that category page is displayed and confirm text 'WOMEN - DRESS PRODUCTS'
        Assert.assertTrue(womenDressPage.getProductCount() > 0, "Category page not displayed");
        Assert.assertTrue(womenDressPage.getCategoryTitle().contains("WOMEN - DRESS PRODUCTS"), "Category title mismatch");

        // On left side bar, click on any sub-category link of 'Men' category
        home.clickMenCategory();
        ProductsPage menTshirtsPage = home.clickMenTshirtsCategory();

        // Verify that user is navigated to that category page
        Assert.assertTrue(menTshirtsPage.getProductCount() > 0, "Men category page not displayed");
        Assert.assertEquals(menTshirtsPage.getCategoryTitle(), "MEN - TSHIRTS PRODUCTS", "Men category title mismatch");
    }

    @Test(description = "TC19: View & Cart Brand Products")
    public void testViewCartBrandProducts() {
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page not visible");

        // Click on 'Products' button
        ProductsPage products = home.clickProducts();
        Assert.assertTrue(products.isProductsPageLoaded(), "Products page not loaded");

        // Verify that Brands are visible on left side bar
        Assert.assertTrue(products.areBrandsVisible(), "Brands not visible on left side bar");

        // Click on any brand name (Polo)
        products.clickPoloBrand();

        // Verify that user is navigated to brand page and brand products are displayed
        Assert.assertTrue(products.getProductCount() > 0, "Brand products not displayed");
        Assert.assertEquals(products.getBrandTitle(), "BRAND - POLO PRODUCTS", "Brand title mismatch");

        // On left side bar, click on any other brand link (H&M)
        products.clickHmBrand();

        // Verify that user is navigated to that brand page and can see products
        Assert.assertTrue(products.getProductCount() > 0, "H&M brand products not displayed");
        Assert.assertEquals(products.getBrandTitle(), "BRAND - H&M PRODUCTS", "H&M brand title mismatch");
    }

    @Test(description = "TC20: Search Products and Verify Cart After Login")
    public void testSearchProductsAndVerifyCartAfterLogin() {
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page not visible");

        // Click on 'Products' button
        ProductsPage products = home.clickProducts();

        // Verify user is navigated to ALL PRODUCTS page successfully
        Assert.assertTrue(products.isProductsPageLoaded(), "User not navigated to ALL PRODUCTS page");

        // Enter product name in search input and click search button
        products.searchProduct("Top");

        // Verify 'SEARCHED PRODUCTS' is visible
        Assert.assertTrue(products.isSearchResultsVisible(), "'SEARCHED PRODUCTS' not visible");

        // Verify all the products related to search are visible
        Assert.assertTrue(products.getProductCount() > 0, "All products related to search not visible");

        // Add those products to cart (add first 2 products)
        products.clickAddToCart(1);
        products.clickContinueShopping();
        products.clickAddToCart(2);
        products.clickContinueShopping();

        // Click 'Cart' button and verify that products are visible in cart
        CartPage cart = home.clickCart();
        Assert.assertTrue(cart.getCartProductCount() >= 2, "Products not visible in cart");

        // Click 'Signup / Login' button and submit login details
        LoginPage login = home.clickSignupLogin();
        HomePage loggedIn = login.login("testuser@mail.com", "Test@12345");

        // Again, go to Cart page
        cart = loggedIn.clickCart();

        // Verify that those products are visible in cart after login as well
        Assert.assertTrue(cart.getCartProductCount() >= 2, "Products not visible in cart after login");
    }

    @Test(description = "TC21: Add review on product")
    public void testAddReviewOnProduct() {
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page not visible");

        // Click on 'Products' button
        ProductsPage products = home.clickProducts();

        // Verify user is navigated to ALL PRODUCTS page successfully
        Assert.assertTrue(products.isProductsPageLoaded(), "User not navigated to ALL PRODUCTS page");

        // Click on 'View Product' button
        ProductDetailPage productDetail = products.clickViewProduct(1);

        // Verify 'Write Your Review' is visible
        Assert.assertTrue(productDetail.isWriteReviewVisible(), "'Write Your Review' not visible");

        // Enter name, email and review
        productDetail.enterReviewName("Test User")
                    .enterReviewEmail("test@example.com")
                    .enterReviewText("This is a great product! Highly recommended.");

        // Click 'Submit' button
        productDetail.clickSubmitReview();

        // Verify success message 'Thank you for your review.'
        Assert.assertTrue(productDetail.isReviewSuccessVisible(), "Success message not visible");
        Assert.assertEquals(productDetail.getReviewSuccessMessage(), "Thank you for your review.", "Success message mismatch");
    }

    @Test(description = "TC22: Add to cart from Recommended items")
    public void testAddToCartFromRecommendedItems() {
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page not visible");

        // Scroll to bottom of page
        home.scrollToBottom();

        // Verify 'RECOMMENDED ITEMS' are visible
        Assert.assertTrue(home.areRecommendedItemsVisible(), "'RECOMMENDED ITEMS' not visible");

        // Click on 'Add To Cart' on Recommended product
        home.clickRecommendedAddToCart();

        // Click on 'View Cart' button
        CartPage cart = home.clickRecommendedViewCart();

        // Verify that product is displayed in cart page
        Assert.assertTrue(cart.getCartProductCount() > 0, "Product not displayed in cart page");
    }

    @Test(description = "TC23: Verify address details in checkout page")
    public void testVerifyAddressDetailsInCheckoutPage() {
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page not visible");

        // Click 'Signup / Login' button
        LoginPage login = home.clickSignupLogin();

        // Fill all details in Signup and create account
        String email = getUniqueEmail();
        RegisterPage reg = login.signup("TestUser", email);
        reg.selectTitle("Mr").enterPassword("Test@12345")
           .selectDateOfBirth("10", "5", "1995").selectNewsletter().selectOffers()
           .fillAddressDetails("Test", "User", "Test Corp", "123 Main St", "United States",
               "California", "Los Angeles", "90001", "9876543210");

        AccountCreatedPage created = reg.clickCreateAccount();

        // Verify 'ACCOUNT CREATED!' and click 'Continue' button
        Assert.assertTrue(created.isAccountCreated(), "'ACCOUNT CREATED!' not visible");
        HomePage loggedIn = created.clickContinue();

        // Verify ' Logged in as username' at top
        Assert.assertTrue(loggedIn.isLoggedIn(), "'Logged in as username' not visible");

        // Add products to cart
        ProductsPage products = loggedIn.clickProducts();
        products.clickAddToCart(1);
        products.clickContinueShopping();

        // Click 'Cart' button
        CartPage cart = loggedIn.clickCart();

        // Verify that cart page is displayed
        Assert.assertTrue(cart.getCartProductCount() > 0, "Cart page not displayed");

        // Click Proceed To Checkout
        CheckoutPage checkout = cart.proceedToCheckout();

        // Verify that the delivery address is same address filled at the time registration of account
        Assert.assertTrue(checkout.isDeliveryAddressVisible(), "Delivery address not visible");
        String deliveryAddress = checkout.getDeliveryAddress();
        Assert.assertTrue(deliveryAddress.contains("Test User"), "Delivery address name mismatch");
        Assert.assertTrue(deliveryAddress.contains("Test Corp"), "Delivery address company mismatch");
        Assert.assertTrue(deliveryAddress.contains("123 Main St"), "Delivery address street mismatch");
        Assert.assertTrue(deliveryAddress.contains("Los Angeles"), "Delivery address city mismatch");

        // Verify that the billing address is same address filled at the time registration of account
        Assert.assertTrue(checkout.isBillingAddressVisible(), "Billing address not visible");
        String billingAddress = checkout.getBillingAddress();
        Assert.assertTrue(billingAddress.contains("Test User"), "Billing address name mismatch");
        Assert.assertTrue(billingAddress.contains("Test Corp"), "Billing address company mismatch");
        Assert.assertTrue(billingAddress.contains("123 Main St"), "Billing address street mismatch");
        Assert.assertTrue(billingAddress.contains("Los Angeles"), "Billing address city mismatch");

        // Click 'Delete Account' button
        AccountDeletedPage deleted = loggedIn.clickDeleteAccount();

        // Verify 'ACCOUNT DELETED!' and click 'Continue' button
        Assert.assertTrue(deleted.isAccountDeleted(), "'ACCOUNT DELETED!' not visible");
        deleted.clickContinue();
    }

    @Test(description = "TC24: Download Invoice after purchase order")
    public void testDownloadInvoiceAfterPurchaseOrder() {
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page not visible");

        // Add products to cart
        ProductsPage products = home.clickProducts();
        products.clickAddToCart(1);
        products.clickContinueShopping();

        // Click 'Cart' button
        CartPage cart = home.clickCart();

        // Verify that cart page is displayed
        Assert.assertTrue(cart.getCartProductCount() > 0, "Cart page not displayed");

        // Click Proceed To Checkout
        CheckoutPage checkout = cart.proceedToCheckout();

        // Click 'Register / Login' button
        LoginPage login = checkout.clickRegisterLogin();

        // Fill all details in Signup and create account
        String email = getUniqueEmail();
        RegisterPage reg = login.signup("TestUser", email);
        reg.selectTitle("Mr").enterPassword("Test@12345")
           .selectDateOfBirth("10", "5", "1995").selectNewsletter().selectOffers()
           .fillAddressDetails("Test", "User", "Test Corp", "123 Main St", "United States",
               "California", "Los Angeles", "90001", "9876543210");

        AccountCreatedPage created = reg.clickCreateAccount();

        // Verify 'ACCOUNT CREATED!' and click 'Continue' button
        Assert.assertTrue(created.isAccountCreated(), "'ACCOUNT CREATED!' not visible");
        HomePage loggedIn = created.clickContinue();

        // Verify ' Logged in as username' at top
        Assert.assertTrue(loggedIn.isLoggedIn(), "'Logged in as username' not visible");

        // Click 'Cart' button
        cart = loggedIn.clickCart();

        // Click 'Proceed To Checkout' button
        checkout = cart.proceedToCheckout();

        // Verify Address Details and Review Your Order
        Assert.assertTrue(checkout.isDeliveryAddressVisible(), "Address Details not visible");
        Assert.assertTrue(checkout.getOrderItemCount() > 0, "Order items not reviewed");

        // Enter description in comment text area and click 'Place Order'
        PaymentPage payment = checkout.enterOrderComment("Test order comment").clickPlaceOrder();

        // Enter payment details: Name on Card, Card Number, CVC, Expiration date
        payment.enterCardDetails("Test User", "1234567890123456", "123", "12", "2025");

        // Click 'Pay and Confirm Order' button
        payment.clickPayAndConfirm();

        // Verify success message 'Your order has been placed successfully!'
        Assert.assertTrue(payment.isOrderPlacedSuccessfully(), "Success message not visible");
        Assert.assertEquals(payment.getOrderSuccessMessage(), "Your order has been placed successfully!", "Success message mismatch");

        // Click 'Download Invoice' button and verify invoice is downloaded successfully.
        payment.clickDownloadInvoice();
        Assert.assertTrue(payment.isInvoiceDownloaded(), "Invoice not downloaded successfully");

        // Click 'Continue' button
        loggedIn = payment.clickContinue();

        // Click 'Delete Account' button
        AccountDeletedPage deleted = loggedIn.clickDeleteAccount();

        // Verify 'ACCOUNT DELETED!' and click 'Continue' button
        Assert.assertTrue(deleted.isAccountDeleted(), "'ACCOUNT DELETED!' not visible");
        deleted.clickContinue();
    }

    @Test(description = "TC25: Verify Scroll Up using 'Arrow' button and Scroll Down functionality")
    public void testScrollUpUsingArrowButton() {
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page not visible");

        // Scroll down page to bottom
        home.scrollToBottom();

        // Verify 'SUBSCRIPTION' is visible
        Assert.assertTrue(home.isSubscriptionVisible(), "'SUBSCRIPTION' not visible");

        // Click on arrow at bottom right side to move upward
        home.clickScrollUpArrow();

        // Verify that page is scrolled up and 'Full-Fledged practice website for Automation Engineers' text is visible on screen
        Assert.assertTrue(home.isFullFledgedTextVisible(), "'Full-Fledged practice website for Automation Engineers' text not visible");
    }

    @Test(description = "TC26: Verify Scroll Up without 'Arrow' button and Scroll Down functionality")
    public void testScrollUpWithoutArrowButton() {
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page not visible");

        // Scroll down page to bottom
        home.scrollToBottom();

        // Verify 'SUBSCRIPTION' is visible
        Assert.assertTrue(home.isSubscriptionVisible(), "'SUBSCRIPTION' not visible");

        // Scroll up page to top
        home.scrollToTop();

        // Verify that page is scrolled up and 'Full-Fledged practice website for Automation Engineers' text is visible on screen
        Assert.assertTrue(home.isFullFledgedTextVisible(), "'Full-Fledged practice website for Automation Engineers' text not visible");
    }
}
