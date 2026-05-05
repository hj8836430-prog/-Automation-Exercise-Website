package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductsPage extends BasePage {

    private final By productsList = By.cssSelector(".features_items .product-image-wrapper");
    private final By searchInput = By.id("search_product");
    private final By searchButton = By.id("submit_search");
    private final By productsPageHeading = By.xpath("//h2[contains(text(),'Product') or contains(text(),'All') or contains(text(),'product')]");
    private final By searchedProductsHeading = By.xpath("//h2[text()='Searched Products']");
    private final By addToCartBtn = By.cssSelector("button[data-qa='add-to-cart']");
    private final By continueShoppingBtn = By.cssSelector(".modal-footer .btn-success");
    private final By viewCartBtn = By.cssSelector(".modal-body a[href='/view_cart']");
    private final By categoryTitle = By.cssSelector(".title.text-center");
    private final By brandsSection = By.cssSelector(".brands_products");
    private final By poloBrand = By.xpath("//div[@class='brands_products']//a[@href='/brand_products/Polo']");
    private final By hmBrand = By.xpath("//div[@class='brands_products']//a[@href='/brand_products/H&M']");
    private final By brandTitle = By.cssSelector(".title.text-center");
    private final By featuresItems = By.cssSelector(".features_items");

    public ProductsPage(WebDriver driver) {
        super(driver);
        // Handle any ad overlay on page load
        handleAdOverlay();
    }

    private void handleAdOverlay() {
        try {
            By adCloseBtn = By.cssSelector("[aria-label='Close ad'], .ad-close, #dismiss-button, .google-ad-close, .close-button");
            if (isDisplayed(adCloseBtn)) {
                click(adCloseBtn);
                Thread.sleep(500);
            }
        } catch (Exception e) {
            // Ad overlay not present or couldn't be closed
        }
    }

    public int getProductCount() {
        try {
            return driver.findElements(productsList).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isProductsPageLoaded() {
        // Check for features_items container or page heading
        try {
            if (isDisplayed(featuresItems)) {
                return true;
            }
        } catch (Exception e) {}
        return isDisplayed(productsPageHeading);
    }
    public boolean isSearchResultsVisible() { return isDisplayed(searchedProductsHeading); }

    public ProductsPage searchProduct(String productName) {
        type(searchInput, productName);
        click(searchButton);
        return this;
    }

    public ProductDetailPage clickViewProduct(int index) {
        By viewProductLink = By.cssSelector(
            ".features_items .product-image-wrapper:nth-child(" + index + ") a[href*='product_details']");
        click(viewProductLink);
        return new ProductDetailPage(driver);
    }

    public ProductsPage clickAddToCart(int index) {
        // Use a CSS selector to find all add-to-cart buttons
        By addToCartBtnLocator = By.cssSelector("button[data-qa='add-to-cart']");

        try {
            // Find all matching buttons
            java.util.List<<WebElementWebElement> buttons = driver.findElements(addToCartBtnLocator);
            if (buttons.isEmpty()) {
                throw new org.openqa.selenium.NoSuchElementException("No add-to-cart buttons found");
            }

            // Click the button at the specified index (1-based)
            int actualIndex = Math.min(index - 1, buttons.size() - 1);
            jsClick(buttons.get(actualIndex));
        } catch (Exception e) {
            throw e;
        }
        return this;
    }

    public ProductsPage clickContinueShopping() {
        click(continueShoppingBtn);
        return this;
    }

    public CartPage clickViewCartFromModal() {
        click(viewCartBtn);
        return new CartPage(driver);
    }

    public String getCategoryTitle() { return getText(categoryTitle); }
    public boolean areBrandsVisible() { return isDisplayed(brandsSection); }
    public ProductsPage clickPoloBrand() { click(poloBrand); return this; }
    public ProductsPage clickHmBrand() { click(hmBrand); return this; }
    public String getBrandTitle() { return getText(brandTitle); }
}
