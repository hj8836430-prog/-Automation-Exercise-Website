package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountCreatedPage extends BasePage {

    private final By accountCreatedText = By.cssSelector("h2[data-qa='account-created']");
    private final By continueButton = By.cssSelector("a[data-qa='continue-button']");

    public AccountCreatedPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAccountCreated() {
        return isDisplayed(accountCreatedText);
    }

    public String getAccountCreatedText() {
        return getText(accountCreatedText);
    }

    public HomePage clickContinue() {
        click(continueButton);
        return new HomePage(driver);
    }
}
