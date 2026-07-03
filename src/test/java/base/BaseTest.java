package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.AccountCreatedPage;
import pages.AccountDeletedPage;
import pages.HomePage;
import pages.LoginPage;
import pages.RegisterPage;

public class BaseTest {

    protected WebDriver driver;
    protected Properties config;

    public WebDriver getDriver() {
        return driver;
    }

    protected String getUniqueEmail(String prefix) {
        return prefix + System.currentTimeMillis() + "@mail.com";
    }

    protected HomePage registerAndLoginUser(String fullName, String email, String password) {
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isHomePageVisible(), "Home page not visible");

        RegisterPage reg = home.clickSignupLogin().signup(fullName, email);
        Assert.assertTrue(reg.isEnterAccountInfoVisible(), "Account info form not visible");

        reg.selectTitle("Mr")
           .enterPassword(password)
           .selectDateOfBirth("10", "5", "1995")
           .selectNewsletter()
           .selectOffers()
           .fillAddressDetails("Auto", "User", "Test Corp", "123 Main St", "United States",
               "California", "Los Angeles", "90001", "9876543210");

        AccountCreatedPage created = reg.clickCreateAccount();
        Assert.assertTrue(created.isAccountCreated(), "Account created message not shown");

        return created.clickContinue();
    }

    protected HomePage loginUser(String email, String password) {
        HomePage home = new HomePage(driver);
        LoginPage loginPage = home.clickSignupLogin();
        Assert.assertTrue(loginPage.isLoginPageLoaded(), "'Login to your account' not visible");
        return loginPage.login(email, password);
    }

    protected AccountDeletedPage deleteUserAccount(HomePage loggedInHomePage) {
        AccountDeletedPage deleted = loggedInHomePage.clickDeleteAccount();
        Assert.assertTrue(deleted.isAccountDeleted(), "'ACCOUNT DELETED!' not visible");
        return deleted;
    }

    @BeforeMethod
    public void setUp() throws IOException {
        config = loadConfig();
        String browser = config.getProperty("browser", "chrome").toLowerCase();

        switch (browser) {

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;

            default:
                WebDriverManager.chromedriver().setup();

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--window-size=1920,1080");

                driver = new ChromeDriver(options);
        }

        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(Long.parseLong(config.getProperty("implicitWait", "10"))));

        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(Long.parseLong(config.getProperty("pageLoadTimeout", "30"))));

        driver.get(config.getProperty("baseUrl"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private Properties loadConfig() throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            props.load(fis);
        }
        return props;
    }
}