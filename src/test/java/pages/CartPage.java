package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private final By cartProducts = By.cssSelector("#cart_info_table tbody tr");
    private final By proceedToCheckoutButton = By.cssSelector(".col-sm-6 a.btn.btn-default.check_out");
    private final By subscriptionText = By.xpath("//h2[text()='Subscription']");
    private final By subscribeEmail = By.cssSelector("input#susbscribe_email");
    private final By subscribeButton = By.cssSelector("button#subscribe");
    private final By subscriptionSuccess = By.cssSelector(".alert-success");
    private final By cartPrices = By.cssSelector("#cart_info_table tbody tr td:nth-child(4)");
    private final By cartQuantities = By.cssSelector("#cart_info_table tbody tr td:nth-child(5)");
    private final By cartTotals = By.cssSelector("#cart_info_table tbody tr td:nth-child(6)");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int getCartProductCount() {
        return driver.findElements(cartProducts).size();
    }

    public CheckoutPage proceedToCheckout() {
        click(proceedToCheckoutButton);
        return new CheckoutPage(driver);
    }

    public boolean isCartEmpty() {
        return driver.findElements(cartProducts).isEmpty();
    }

    public java.util.List<String> getCartPrices() {
        return driver.findElements(cartPrices).stream().map(e -> e.getText()).collect(java.util.stream.Collectors.toList());
    }

    public java.util.List<String> getCartQuantities() {
        return driver.findElements(cartQuantities).stream().map(e -> e.getText()).collect(java.util.stream.Collectors.toList());
    }

    public java.util.List<String> getCartTotals() {
        return driver.findElements(cartTotals).stream().map(e -> e.getText()).collect(java.util.stream.Collectors.toList());
    }

    public boolean isSubscriptionVisible() { return isDisplayed(subscriptionText); }
    public CartPage enterSubscriptionEmail(String email) { type(subscribeEmail, email); return this; }
    public CartPage clickSubscribe() { click(subscribeButton); return this; }
    public boolean isSubscriptionSuccessVisible() { return isDisplayed(subscriptionSuccess); }
    public String getSubscriptionSuccessMessage() { return getText(subscriptionSuccess); }

    public CartPage removeProduct(int index) {
        // Click the 'X' button for the product at the specified index (1-based)
        By removeButton = By.xpath("//tbody/tr[" + index + "]//a[@class='cart_quantity_delete']");
        click(removeButton);
        return this;
    }
}
