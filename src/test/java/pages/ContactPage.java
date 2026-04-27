package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ContactPage extends BasePage {

    private final By getInTouchHeading = By.xpath("//h2[text()='Get In Touch']");
    private final By nameField = By.cssSelector("input[data-qa='name']");
    private final By emailField = By.cssSelector("input[data-qa='email']");
    private final By subjectField = By.cssSelector("input[data-qa='subject']");
    private final By messageField = By.cssSelector("textarea[data-qa='message']");
    private final By uploadFile = By.cssSelector("input[name='upload_file']");
    private final By submitButton = By.cssSelector("input[data-qa='submit-button']");
    private final By successMessage = By.cssSelector("div.status.alert.alert-success");
    private final By homeButton = By.cssSelector("a.btn-success");

    public ContactPage(WebDriver driver) {
        super(driver);
    }

    public boolean isGetInTouchVisible() {
        return isDisplayed(getInTouchHeading);
    }

    public ContactPage enterName(String name) {
        type(nameField, name);
        return this;
    }

    public ContactPage enterEmail(String email) {
        type(emailField, email);
        return this;
    }

    public ContactPage enterSubject(String subject) {
        type(subjectField, subject);
        return this;
    }

    public ContactPage enterMessage(String message) {
        type(messageField, message);
        return this;
    }

    public ContactPage uploadFile(String filePath) {
        driver.findElement(uploadFile).sendKeys(filePath);
        return this;
    }

    public ContactPage submitForm() {
        click(submitButton);
        // Handle alert
        driver.switchTo().alert().accept();
        return this;
    }

    public boolean isSuccessMessageVisible() {
        return isDisplayed(successMessage);
    }

    public String getSuccessMessage() {
        return getText(successMessage);
    }

    public HomePage clickHome() {
        click(homeButton);
        return new HomePage(driver);
    }
}