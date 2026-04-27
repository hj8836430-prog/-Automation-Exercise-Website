# Automation Exercise Selenium Framework

Ye ek simple Selenium WebDriver framework hai jo AutomationExercise.com website ke liye test cases run karta hai.

## Project Structure (समझने के लिए)

```
src/
├── test/
│   ├── java/
│   │   ├── base/           # Base classes (fundamental classes)
│   │   │   └── BaseTest.java    # Browser setup aur teardown
│   │   ├── pages/          # Page Object Model classes
│   │   │   ├── BasePage.java    # Common Selenium methods
│   │   │   ├── HomePage.java    # Home page ke elements aur actions
│   │   │   ├── LoginPage.java   # Login/Signup page
│   │   │   └── ... (aur page classes)
│   │   └── tests/          # Actual test classes
│   │       ├── LoginTest.java   # Login related tests
│   │       ├── ProductsTest.java # Product related tests
│   │       └── ...
│   └── resources/
│       └── config.properties   # Configuration settings
```

## Key Concepts (महत्वपूर्ण Concepts)

### 1. Page Object Model (POM)
- Har page ke liye alag class hai
- Page ke saare elements aur actions ek jagah
- Code maintain karna easy hota hai

### 2. Base Classes
- `BasePage`: Common Selenium methods (click, type, wait, etc.)
- `BaseTest`: Browser setup, config load, test lifecycle

### 3. Test Structure
- `@Test` annotation se test methods mark ki gayi hai
- `Assert` se results verify karte hai
- Step-by-step comments add ki gayi hai

## Configuration (कॉन्फ़िगरेशन)

`config.properties` file me settings:
```
browser=chrome
baseUrl=http://automationexercise.com
implicitWait=10
pageLoadTimeout=30
```

## Test Cases Run Karne Ke Liye

### Saare tests run karo:
```bash
mvn test
```

### Specific test class run karo:
```bash
mvn test -Dtest=ProductsTest
```

### Specific test method run karo:
```bash
mvn test -Dtest=ProductsTest#testNavigateToProductsPage
```

## Simple Test Example

```java
@Test(description = "Simple test example")
public void simpleTest() {
    // 1. Home page check karo
    HomePage home = new HomePage(driver);
    Assert.assertTrue(home.isHomePageVisible(), "Home page nahi dikha");

    // 2. Products page pe jao
    ProductsPage products = home.clickProducts();
    Assert.assertTrue(products.isProductsPageLoaded(), "Products page nahi load hua");

    // 3. Check karo ki products hai
    Assert.assertTrue(products.getProductCount() > 0, "Products nahi hai");
}
```

## Browser Support
- Chrome (default)
- Firefox
- Edge

Browser change karne ke liye `config.properties` me `browser=firefox` set karo.

## Understanding the Flow (Flow समझें)

1. **BaseTest** browser kholta hai aur website pe jata hai
2. **Page classes** website ke elements ko control karte hai
3. **Test classes** actual test logic contain karte hai
4. **Assertions** check karte hai ki expected result mila ya nahi

Ye framework simple aur understandable banaya gaya hai taaki beginners easily samajh sake!