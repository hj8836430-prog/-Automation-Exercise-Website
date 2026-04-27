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
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
        } catch (ElementClickInterceptedException e) {
            // If click fails, use JavaScript click (for ads/modals)
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            // Fallback: try JavaScript click directly
            try {
                WebElement element = driver.findElement(locator);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            } catch (Exception e2) {
                throw e;
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
}
