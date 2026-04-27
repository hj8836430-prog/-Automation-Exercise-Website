package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private final By deliveryAddress = By.id("address_delivery");
    private final By billingAddress = By.id("address_invoice");
    private final By cartItemsTable = By.cssSelector("#cart_info tbody tr");
    private final By commentBox = By.cssSelector("textarea.form-control");
    private final By placeOrderButton = By.cssSelector("a.btn.btn-default.check_out");
    private final By registerLoginButton = By.cssSelector("a[href='/login']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDeliveryAddressVisible() {
        return isDisplayed(deliveryAddress);
    }

    public boolean isBillingAddressVisible() {
        return isDisplayed(billingAddress);
    }

    public String getDeliveryAddress() {
        return getText(deliveryAddress);
    }

    public String getBillingAddress() {
        return getText(billingAddress);
    }

    public int getOrderItemCount() {
        return driver.findElements(cartItemsTable).size();
    }

    public CheckoutPage enterOrderComment(String comment) {
        type(commentBox, comment);
        return this;
    }

    public LoginPage clickRegisterLogin() {
        click(registerLoginButton);
        return new LoginPage(driver);
    }

    public PaymentPage clickPlaceOrder() {
        click(placeOrderButton);
        return new PaymentPage(driver);
    }
}
