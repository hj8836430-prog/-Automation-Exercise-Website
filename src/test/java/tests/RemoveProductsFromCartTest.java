package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import base.BaseTest;

public class RemoveProductsFromCartTest extends BaseTest {

    @Test
    public void testRemoveProductsFromCart() {
        HomePage homePage = new HomePage(driver);

        // 1. Launch browser and Navigate to url 'http://automationexercise.com' (Handled by BaseTest)

        // 2. Verify that home page is visible successfully
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");

        // 3. Add products to cart
        ProductsPage productsPage = homePage.clickProducts();
        productsPage.clickAddToCart(1);
        productsPage.clickContinueShopping();
        productsPage.clickAddToCart(2);

        // 4. Click 'Cart' button
        CartPage cartPage = homePage.clickCart();

        // 5. Verify that cart page is displayed
        Assert.assertTrue(!cartPage.isCartEmpty(), "Cart should not be empty");
        int initialCount = cartPage.getCartProductCount();
        Assert.assertTrue(initialCount > 0, "Cart should contain products before removal");

        // 6. Click 'X' button corresponding to particular product (removing the first product)
        cartPage.removeProduct(1);

        // 7. Verify that product is removed from the cart
        int finalCount = cartPage.getCartProductCount();
        Assert.assertEquals(finalCount, initialCount - 1, "Product count should decrease by 1 after removing a product");
    }
}
