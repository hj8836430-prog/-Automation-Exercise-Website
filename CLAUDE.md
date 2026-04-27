# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
# Run all tests
mvn test

# Run a specific test class
mvn test -Dtest=RegisterTest
mvn test -Dtest=LoginTest
mvn test -Dtest=ProductsTest

# Run a specific test method
mvn test -Dtest=RegisterTest#testRegisterNewUserAndDeleteAccount

# Clean and rebuild
mvn clean install
```

## Architecture Overview

**Framework:** Selenium WebDriver + TestNG automation framework for testing https://www.automationexercise.com

**Structure:**
- `src/test/java/base/` - `BaseTest` provides `@BeforeMethod`/`@AfterMethod` for WebDriver lifecycle (Chrome/Firefox/Edge via WebDriverManager)
- `src/test/java/pages/` - Page Object Model classes for each page (HomePage, LoginPage, RegisterPage, ProductsPage, etc.)
- `src/test/java/tests/` - TestNG test classes with `@Test` annotations
- `src/test/java/listeners/` - `TestListener` implements ITestListener for ExtentReports integration
- `src/test/java/utils/` - `ExtentReportManager` for HTML report generation
- `src/test/resources/config.properties` - Configuration (browser, baseUrl, timeouts)

**Key Patterns:**
- Page classes extend `BasePage` which provides common Selenium utilities (waitForVisible, waitForClickable, click with JavaScript fallback for ads, type, scrollToElement)
- Tests extend `BaseTest` to inherit WebDriver setup/teardown
- Test flow: HomePage → LoginPage → RegisterPage → AccountCreatedPage → HomePage (logged in) → AccountDeletedPage
- Reports generated at `test-output/ExtentReport.html`

**Configuration:**
- Browser selection via `config.properties` (default: chrome)
- Implicit wait: 10s, Page load timeout: 30s
- WebDriverManager auto-downloads browser drivers
