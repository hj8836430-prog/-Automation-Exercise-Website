# Automation Exercise Selenium Framework

This is a simple Selenium WebDriver framework that runs test cases for the AutomationExercise.com website.

## Project Structure

```
src/
├── test/
│   ├── java/
│   │   ├── base/           # Base classes (fundamental classes)
│   │   │   └── BaseTest.java    # Browser setup and teardown
│   │   ├── pages/          # Page Object Model classes
│   │   │   ├── BasePage.java    # Common Selenium methods
│   │   │   ├── HomePage.java    # Home page elements and actions
│   │   │   ├── LoginPage.java   # Login/Signup page
│   │   │   └── ... (other page classes)
│   │   └── tests/          # Actual test classes
│   │       ├── LoginTest.java   # Login related tests
│   │       ├── ProductsTest.java # Product related tests
│   │       └── ...
│   └── resources/
│       └── config.properties   # Configuration settings
```

## Key Concepts

### 1. Page Object Model (POM)
- Separate class for each page
- All page elements and actions in one place
- Easy to maintain code

### 2. Base Classes
- `BasePage`: Common Selenium methods (click, type, wait, etc.)
- `BaseTest`: Browser setup, config load, test lifecycle

### 3. Test Structure
- Test methods marked with `@Test` annotation
- Results verified using `Assert`
- Step-by-step comments added

## Configuration

Settings in `config.properties` file:
```
browser=chrome
baseUrl=http://automationexercise.com
implicitWait=10
pageLoadTimeout=30
```

## Running Test Cases

### Run all tests:
```bash
mvn test
```

### Run a specific test class:
```bash
mvn test -Dtest=ProductsTest
```

### Run a specific test method:
```bash
mvn test -Dtest=ProductsTest#testNavigateToProductsPage
```

## Simple Test Example

```java
@Test(description = "Simple test example")
public void simpleTest() {
    // 1. Check home page
    HomePage home = new HomePage(driver);
    Assert.assertTrue(home.isHomePageVisible(), "Home page not visible");

    // 2. Navigate to products page
    ProductsPage products = home.clickProducts();
    Assert.assertTrue(products.isProductsPageLoaded(), "Products page not loaded");

    // 3. Check if products exist
    Assert.assertTrue(products.getProductCount() > 0, "No products found");
}
```

## Browser Support
- Chrome (default)
- Firefox
- Edge

To change browser, set `browser=firefox` in `config.properties`.

## Understanding the Flow

1. **BaseTest** opens the browser and navigates to the website
2. **Page classes** control the website elements
3. **Test classes** contain the actual test logic
4. **Assertions** verify if the expected result is obtained

This framework is designed to be simple and understandable so beginners can easily understand it!
