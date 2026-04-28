package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AccountDeletedPage extends BasePage {

    private final By accountDeletedText = By.cssSelector("h2[data-qa='account-deleted']");
    private final By accountDeletedTextAlt = By.xpath("//h2[normalize-space()='ACCOUNT DELETED!']");
    private final By continueButton = By.cssSelector("a[data-qa='continue-button']");

    public AccountDeletedPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAccountDeleted() {
        try {
            // Wait for element to be visible with multiple locator strategies
            wait.until(driver -> {
                try {
                    WebElement element = driver.findElement(accountDeletedText);
                    if (element.isDisplayed()) return true;
                } catch (Exception e) {}

                try {
                    WebElement altElement = driver.findElement(accountDeletedTextAlt);
                    if (altElement.isDisplayed()) return true;
                } catch (Exception e) {}

                // Try additional locator
                try {
                    WebElement xpathElement = driver.findElement(By.xpath("//h2[contains(text(),'ACCOUNT DELETED')]"));
                    if (xpathElement.isDisplayed()) return true;
                } catch (Exception e) {}

                return false;
            });
            return true;
        } catch (Exception e) {
            return false;
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
