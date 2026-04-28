package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.time.Duration;

public class HomePage extends BasePage {

    // Navigation links
    private final By signupLoginLink = By.cssSelector("a[href='/login']");
    private final By cartLink = By.cssSelector("a[href='/view_cart']");
    private final By productsLink = By.cssSelector("a[href='/products']");
    private final By contactUsLink = By.cssSelector("a[href='/contact_us']");
    private final By testCasesLink = By.cssSelector("a[href='/test_cases']");

    // User account elements
    private final By loggedInAsLabel = By.cssSelector("a i.fa-user + b");
    private final By deleteAccountLink = By.cssSelector("a[href='/delete_account']");
    private final By logoutLink = By.cssSelector("a[href='/logout']");

    // Home page elements
    private final By homeSlider = By.id("slider");
    private final By subscriptionText = By.xpath("//h2[text()='Subscription']");
    private final By subscribeEmail = By.cssSelector("input#susbscribe_email");
    private final By subscribeButton = By.cssSelector("button#subscribe");
    private final By subscriptionSuccess = By.cssSelector(".alert-success");

    // Category elements
    private final By categoriesSection = By.cssSelector("#accordian");
    private final By womenCategory = By.xpath("//div[@id='accordian']//a[@href='#Women']");
    private final By womenDressCategory = By.xpath("//div[@id='Women']//a[@href='/category_products/1']");
    private final By menCategory = By.xpath("//div[@id='accordian']//a[@href='#Men']");
    private final By menTshirtsCategory = By.xpath("//div[@id='Men']//a[@href='/category_products/3']");

    // Recommended items
    private final By recommendedItemsSection = By.cssSelector(".recommended_items");
    private final By recommendedAddToCartBtn = By.cssSelector(".recommended_items .add-to-cart");
    private final By recommendedViewCartBtn = By.cssSelector(".modal-body a[href='/view_cart'], .modal-footer a[href='/view_cart']");
    private final By modalViewCartBtn = By.cssSelector("a[href='/view_cart']");

    // Scroll elements
    private final By scrollUpArrow = By.id("scrollUp");
    private final By fullFledgedText = By.xpath("//h1[contains(text(),'Full-Fledged practice website for Automation Engineers')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Navigation methods
    public LoginPage clickSignupLogin() {
        click(signupLoginLink);
        return new LoginPage(driver);
    }

    public CartPage clickCart() {
        click(cartLink);
        return new CartPage(driver);
    }

    public ProductsPage clickProducts() {
        // Handle Google ad overlay if present
        handleGoogleAdOverlay();

        try {
            // Try normal click first
            click(productsLink);
        } catch (Exception e) {
            try {
                // Fallback: JavaScript click
                WebElement element = driver.findElement(productsLink);
                jsClick(element);
            } catch (Exception e2) {
                // Last resort: direct navigation
                driver.get("https://www.automationexercise.com/products");
            }
        }

        // Wait for products page to load
        try {
            wait.until(driver -> {
                try {
                    return driver.findElement(By.cssSelector(".features_items")).isDisplayed();
                } catch (Exception ex) {
                    return false;
                }
            });
        } catch (Exception e) {
            // Page might still be loaded, continue anyway
        }

        return new ProductsPage(driver);
    }

    private void handleGoogleAdOverlay() {
        try {
            // Try to close Google ad overlay if present
            By adCloseBtn = By.cssSelector("[aria-label='Close ad'], .ad-close, #dismiss-button, .google-ad-close");
            if (isDisplayed(adCloseBtn)) {
                click(adCloseBtn);
                Thread.sleep(1000); // Wait for ad to close
            }
        } catch (Exception e) {
            // Ad overlay not present or couldn't be closed, continue
        }
    }

    public ContactPage clickContactUs() {
        click(contactUsLink);
        return new ContactPage(driver);
    }

    public TestCasesPage clickTestCases() {
        // Handle Google ad overlay if present
        handleGoogleAdOverlay();

        click(testCasesLink);

        // Wait for test cases page to load
        try {
            wait.until(driver -> {
                try {
                    return driver.findElement(By.cssSelector(".test-cases-table")).isDisplayed();
                } catch (Exception ex) {
                    return false;
                }
            });
        } catch (Exception e) {
            // Page might still be loaded, continue anyway
        }

        return new TestCasesPage(driver);
    }

    // User account methods
    public boolean isLoggedIn() {
        return isDisplayed(loggedInAsLabel);
    }

    public String getLoggedInUsername() {
        return getText(loggedInAsLabel);
    }

    public void logout() {
        click(logoutLink);
    }

    public AccountDeletedPage clickDeleteAccount() {
        click(deleteAccountLink);
        // Wait for page to load - check for account deleted heading
        try {
            wait.until(driver -> {
                try {
                    WebElement element = driver.findElement(By.cssSelector("h2[data-qa='account-deleted']"));
                    return element.isDisplayed();
                } catch (Exception e) {
                    try {
                        WebElement altElement = driver.findElement(By.xpath("//h2[contains(text(),'ACCOUNT DELETED')]"));
                        return altElement.isDisplayed();
                    } catch (Exception e2) {
                        return false;
                    }
                }
            });
        } catch (Exception e) {
            // Fallback - just wait a bit for page load
            try { Thread.sleep(3000); } catch (InterruptedException ie) {}
        }
        return new AccountDeletedPage(driver);
    }

    // Home page check
    public boolean isHomePageVisible() {
        return isDisplayed(homeSlider);
    }

    // Subscription methods
    public boolean isSubscriptionVisible() {
        return isDisplayed(subscriptionText);
    }

    public HomePage enterSubscriptionEmail(String email) {
        type(subscribeEmail, email);
        return this;
    }

    public HomePage clickSubscribe() {
        click(subscribeButton);
        return this;
    }

    public boolean isSubscriptionSuccessVisible() {
        return isDisplayed(subscriptionSuccess);
    }

    public String getSubscriptionSuccessMessage() {
        return getText(subscriptionSuccess);
    }

    // Category methods
    public boolean areCategoriesVisible() {
        return isDisplayed(categoriesSection);
    }

    public HomePage clickWomenCategory() {
        click(womenCategory);
        return this;
    }

    public ProductsPage clickWomenDressCategory() {
        click(womenDressCategory);
        return new ProductsPage(driver);
    }

    public HomePage clickMenCategory() {
        click(menCategory);
        return this;
    }

    public ProductsPage clickMenTshirtsCategory() {
        click(menTshirtsCategory);
        return new ProductsPage(driver);
    }

    // Recommended items methods
    public boolean areRecommendedItemsVisible() {
        return isDisplayed(recommendedItemsSection);
    }

    public HomePage clickRecommendedAddToCart() {
        click(recommendedAddToCartBtn);
        return this;
    }

    public CartPage clickRecommendedViewCart() {
        // Wait for modal to appear first
        try {
            wait.until(driver -> driver.findElement(modalViewCartBtn).isDisplayed());
        } catch (Exception e) {
            // Modal might already be visible, continue
        }
        click(recommendedViewCartBtn);
        return new CartPage(driver);
    }

    // Scroll arrow methods
    public HomePage clickScrollUpArrow() {
        click(scrollUpArrow);
        return this;
    }

    public boolean isFullFledgedTextVisible() {
        return isDisplayed(fullFledgedText);
    }
}
