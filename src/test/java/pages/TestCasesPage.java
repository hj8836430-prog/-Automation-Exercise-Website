package pages;

import org.openqa.selenium.WebDriver;

public class TestCasesPage extends BasePage {

    public TestCasesPage(WebDriver driver) {
        super(driver);
    }

    public boolean isTestCasesPageVisible() {
        // Check if URL contains /test_cases or check for a heading
        return getCurrentUrl().contains("/test_cases");
    }
}