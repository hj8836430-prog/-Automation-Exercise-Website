package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailPage extends BasePage {

    private final By productName = By.cssSelector(".product-information h2");
    private final By productCategory = By.xpath("//p[contains(text(),'Category:')]");
    private final By productPrice = By.cssSelector(".product-information span span");
    private final By productAvailability = By.xpath("//p[contains(text(),'Availability:')]");
    private final By productCondition = By.xpath("//p[contains(text(),'Condition:')]");
    private final By productBrand = By.xpath("//p[contains(text(),'Brand:')]");
    private final By addToCartButton = By.cssSelector("button.cart");
    private final By quantityField = By.id("quantity");
    private final By viewCartBtn = By.cssSelector(".modal-body a[href='/view_cart']");
    private final By writeReviewHeading = By.xpath("//a[text()='Write Your Review']");
    private final By reviewName = By.id("name");
    private final By reviewEmail = By.id("email");
    private final By reviewText = By.id("review");
    private final By reviewSubmit = By.id("button-review");
    private final By reviewSuccess = By.cssSelector(".alert-success");

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    public String getProductName() {
        return getText(productName);
    }

    public String getProductCategory() {
        return getText(productCategory);
    }

    public String getProductPrice() {
        return getText(productPrice);
    }

    public String getAvailability() {
        return getText(productAvailability);
    }

    public String getProductCondition() {
        return getText(productCondition);
    }

    public String getProductBrand() {
        return getText(productBrand);
    }

    public ProductDetailPage setQuantity(String quantity) {
        type(quantityField, quantity);
        return this;
    }

    public void clickAddToCart() {
        click(addToCartButton);
    }

    public CartPage clickViewCartFromModal() {
        click(viewCartBtn);
        return new CartPage(driver);
    }

    public boolean isWriteReviewVisible() { return isDisplayed(writeReviewHeading); }
    public ProductDetailPage enterReviewName(String name) { type(reviewName, name); return this; }
    public ProductDetailPage enterReviewEmail(String email) { type(reviewEmail, email); return this; }
    public ProductDetailPage enterReviewText(String review) { type(reviewText, review); return this; }
    public ProductDetailPage clickSubmitReview() { click(reviewSubmit); return this; }
    public boolean isReviewSuccessVisible() { return isDisplayed(reviewSuccess); }
    public String getReviewSuccessMessage() { return getText(reviewSuccess); }
}
