package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import base.BaseTest;

public class ViewCategoryProductsTest extends BaseTest {

    @Test
    public void testViewCategoryProducts() {
        HomePage homePage = new HomePage(driver);

        // 2. Verify that categories are visible on left side bar
        Assert.assertTrue(homePage.areCategoriesVisible(), "Categories should be visible on the left side bar");

        // 3. Click on 'Women' category
        homePage.clickWomenCategory();

        // 4. Click on any category link under 'Women' category, for example: Tops
        ProductsPage productsPage = homePage.clickWomenTopsCategory();

        // 5. Verify that category page is displayed and confirm text 'WOMEN - TOPS PRODUCTS'
        Assert.assertTrue(productsPage.isProductsPageLoaded(), "Products page should be loaded");
        Assert.assertEquals(productsPage.getCategoryTitle(), "WOMEN - TOPS PRODUCTS", "Category title should be 'WOMEN - TOPS PRODUCTS'");

        // 6. On left side bar, click on any sub-category link of 'Men' category
        // Using T-shirts as the sub-category for Men
        productsPage = homePage.clickMenTshirtsCategory();

        // 7. Verify that user is navigated to that category page
        Assert.assertTrue(productsPage.isProductsPageLoaded(), "Men's category page should be loaded");
        Assert.assertTrue(productsPage.getCategoryTitle().contains("MEN"), "Category title should contain 'MEN'");
    }
}
