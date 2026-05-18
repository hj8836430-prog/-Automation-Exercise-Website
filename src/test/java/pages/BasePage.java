package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Simple 10 second wait
    }

    // Wait for element and click
    protected void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        try {
            element.click();
            return;
        } catch (Exception firstException) {
            // Retry with JavaScript click if default click fails
            try {
                WebElement freshElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                jsClick(freshElement);
                return;
            } catch (Exception jsException) {
                try {
                    WebElement freshElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                    new org.openqa.selenium.interactions.Actions(driver)
                            .moveToElement(freshElement)
                            .click()
                            .perform();
                    return;
                } catch (Exception actionsException) {
                    throw firstException;
                }
            }
        }
    }

    // Wait for element and type text
    protected void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    // Get text from element
    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    // Check if element is visible
    protected boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Hover over an element
    protected void hoverOver(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
        actions.moveToElement(element).perform();
    }

    // JavaScript click (for stubborn elements)
    protected void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // Get current URL
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // Select dropdown by value
    protected void selectByValue(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(element);
        select.selectByValue(value);
    }

    // Select dropdown by visible text
    protected void selectByVisibleText(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(element);
        select.selectByVisibleText(text);
    }

    // Scroll to bottom of page
    public void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    // Scroll to top of page
    public void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }
}
