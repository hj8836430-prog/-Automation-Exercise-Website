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
    private final By cartPrices = By.cssSelector("#cart_info_table tbody tr td:nth-child(3)");
    private final By cartQuantities = By.cssSelector("#cart_info_table tbody tr td:nth-child(4) button");
    private final By cartTotals = By.cssSelector("#cart_info_table tbody tr td:nth-child(5)");

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
        // Get quantity values from cart - try multiple selectors
        try {
            java.util.List<String> quantities = driver.findElements(cartQuantities).stream()
                .map(e -> {
                    String text = e.getText().trim();
                    // Extract just the number if it contains "+"  or "-"
                    if (text.isEmpty() && e.getAttribute("value") != null) {
                        return e.getAttribute("value").trim();
                    }
                    return text;
                })
                .collect(java.util.stream.Collectors.toList());
            return quantities;
        } catch (Exception e) {
            // Fallback: try to find quantity by alternative method
            return driver.findElements(By.cssSelector("#cart_info_table tbody tr td:nth-child(4)")).stream()
                .map(el -> el.getText().trim())
                .filter(text -> !text.isEmpty())
                .collect(java.util.stream.Collectors.toList());
        }
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
        By removeButton = By.xpath("(//a[@class='cart_quantity_delete'])[" + index + "]");
        click(removeButton);
        try {
            Thread.sleep(1000); // Wait for row to be removed
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return this;
    }
}
