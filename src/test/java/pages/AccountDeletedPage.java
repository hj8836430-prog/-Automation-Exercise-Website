package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountDeletedPage extends BasePage {

    private final By accountDeletedText = By.cssSelector("h2[data-qa='account-deleted']");
    private final By accountDeletedTextAlt = By.xpath("//h2[normalize-space()='ACCOUNT DELETED!']");
    private final By continueButton = By.cssSelector("a[data-qa='continue-button']");

    public AccountDeletedPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAccountDeleted() {
        try {
            // Wait for element to be visible
            wait.until(driver -> {
                return driver.findElement(accountDeletedText).isDisplayed();
            });
            return true;
        } catch (Exception e) {
            // Fallback to alt locator
            try {
                return driver.findElement(accountDeletedTextAlt).isDisplayed();
            } catch (Exception e2) {
                return false;
            }
        }
    }

    public String getAccountDeletedText() {
        return getText(accountDeletedText);
    }

    public HomePage clickContinue() {
        click(continueButton);
        return new HomePage(driver);
    }
}
