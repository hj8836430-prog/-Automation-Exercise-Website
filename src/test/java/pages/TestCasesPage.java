package pages;

import org.openqa.selenium.WebDriver;

public class TestCasesPage extends BasePage {

    private final By testCasesHeading = By.xpath("//h2[contains(text(),'Test Cases') or contains(text(),'TEST CASES')]");
    private final By testCasesTable = By.cssSelector(".test-cases-table, table.table, .table.table-bordered");

    public TestCasesPage(WebDriver driver) {
        super(driver);
    }

    public boolean isTestCasesPageVisible() {
        // Check if URL contains /test_cases
        if (getCurrentUrl().contains("/test_cases")) {
            return true;
        }

        // Check for heading element
        try {
            if (isDisplayed(testCasesHeading)) {
                return true;
            }
        } catch (Exception e) {}

        // Check for test cases table
        try {
            if (isDisplayed(testCasesTable)) {
                return true;
            }
        } catch (Exception e) {}

        // Check for any table with test case rows
        try {
            WebElement table = driver.findElement(By.tagName("table"));
            return table.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}