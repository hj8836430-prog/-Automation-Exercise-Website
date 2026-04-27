package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By loginEmail = By.cssSelector("input[data-qa='login-email']");
    private final By loginPassword = By.cssSelector("input[data-qa='login-password']");
    private final By loginButton = By.cssSelector("button[data-qa='login-button']");
    private final By loginErrorMsg = By.cssSelector("form[action='/login'] p");
    private final By loginHeading = By.xpath("//h2[text()='Login to your account']");

    private final By signupName = By.cssSelector("input[data-qa='signup-name']");
    private final By signupEmail = By.cssSelector("input[data-qa='signup-email']");
    private final By signupButton = By.cssSelector("button[data-qa='signup-button']");
    private final By signupErrorMsg = By.cssSelector("form[action='/signup'] p");
    private final By signupHeading = By.xpath("//h2[text()='New User Signup!']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public HomePage login(String email, String password) {
        type(loginEmail, email);
        type(loginPassword, password);
        click(loginButton);
        return new HomePage(driver);
    }

    public RegisterPage signup(String name, String email) {
        type(signupName, name);
        type(signupEmail, email);
        click(signupButton);
        return new RegisterPage(driver);
    }

    public boolean isLoginPageLoaded() { return isDisplayed(loginHeading); }
    public boolean isNewUserSignupVisible() { return isDisplayed(signupHeading); }
    public String getLoginErrorMessage() { return getText(loginErrorMsg); }
    public String getSignupErrorMessage() { return getText(signupErrorMsg); }
}
