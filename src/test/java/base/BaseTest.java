package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    protected WebDriver driver;
    protected Properties config;

    public WebDriver getDriver() {
        return driver;
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
                driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(
            Duration.ofSeconds(Long.parseLong(config.getProperty("implicitWait", "10"))));
        driver.manage().timeouts().pageLoadTimeout(
            Duration.ofSeconds(Long.parseLong(config.getProperty("pageLoadTimeout", "30"))));
        driver.get(config.getProperty("baseUrl"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            // Wait karo taaki user dekh sake kya ho raha hai browser me
            try {
                Thread.sleep(3000); // 3 second wait before closing browser
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            driver.quit();
        }
    }

    private Properties loadConfig() throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(
            System.getProperty("user.dir") + "/src/test/resources/config.properties")) {
            props.load(fis);
        }
        return props;
    }
}
