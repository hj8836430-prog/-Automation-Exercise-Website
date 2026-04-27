package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegisterPage extends BasePage {

    private final By mrRadio = By.id("id_gender1");
    private final By mrsRadio = By.id("id_gender2");
    private final By passwordField = By.id("password");
    private final By dobDay = By.id("days");
    private final By dobMonth = By.id("months");
    private final By dobYear = By.id("years");
    private final By newsletterCheckbox = By.id("newsletter");
    private final By offersCheckbox = By.id("optin");
    private final By enterAccountInfoHeading = By.xpath("//b[text()='Enter Account Information']");

    private final By firstNameField = By.id("first_name");
    private final By lastNameField = By.id("last_name");
    private final By companyField = By.id("company");
    private final By addressField = By.id("address1");
    private final By countryDropdown = By.id("country");
    private final By stateField = By.id("state");
    private final By cityField = By.id("city");
    private final By zipcodeField = By.id("zipcode");
    private final By mobileField = By.id("mobile_number");
    private final By createAccountButton = By.cssSelector("button[data-qa='create-account']");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public RegisterPage selectTitle(String title) {
        click(title.equalsIgnoreCase("Mr") ? mrRadio : mrsRadio);
        return this;
    }

    public RegisterPage enterPassword(String password) {
        type(passwordField, password);
        return this;
    }

    public RegisterPage selectDateOfBirth(String day, String month, String year) {
        selectByValue(dobDay, day);
        selectByValue(dobMonth, month);
        selectByValue(dobYear, year);
        return this;
    }

    public RegisterPage selectNewsletter() {
        toggleCheckbox(newsletterCheckbox);
        return this;
    }

    public RegisterPage selectOffers() {
        toggleCheckbox(offersCheckbox);
        return this;
    }

    public boolean isEnterAccountInfoVisible() {
        return isDisplayed(enterAccountInfoHeading);
    }

    public RegisterPage fillAddressDetails(String firstName, String lastName, String company,
            String address, String country, String state, String city, String zipcode, String mobile) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(companyField, company);
        type(addressField, address);
        selectByVisibleText(countryDropdown, country);
        type(stateField, state);
        type(cityField, city);
        type(zipcodeField, zipcode);
        type(mobileField, mobile);
        return this;
    }

    public AccountCreatedPage clickCreateAccount() {
        click(createAccountButton);
        return new AccountCreatedPage(driver);
    }

    private void toggleCheckbox(By locator) {
        WebElement checkbox = driver.findElement(locator);
        if (!checkbox.isSelected()) {
            try {
                checkbox.click();
            } catch (Exception e) {
                // Fallback to JavaScript click for ad overlays
                jsClick(checkbox);
            }
        }
    }
}
