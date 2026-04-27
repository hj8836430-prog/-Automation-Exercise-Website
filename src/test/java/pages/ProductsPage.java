package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public int getProductCount() { return driver.findElements(productsList).size(); }
    public boolean isProductsPageLoaded() { return isDisplayed(productsPageHeading); }
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
        By addToCartBtnLocator = By.cssSelector(
            ".features_items .product-image-wrapper:nth-child(" + index + ") button[data-qa='add-to-cart']");
        try {
            // First try the specific product
            click(addToCartBtnLocator);
        } catch (Exception e) {
            try {
                // Fallback: try clicking the first add to cart button visible
                click(By.cssSelector("button[data-qa='add-to-cart']"));
            } catch (Exception e2) {
                // Last resort: try JavaScript click
                WebElement element = driver.findElement(By.cssSelector("button[data-qa='add-to-cart']"));
                jsClick(element);
            }
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
