# Cart-Selenium-TestProject

## Table of Contents
- [Introduction](#introduction)
- [Project Structure](#project-structure)
- [Key Features](#key-features)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [Test Execution](#test-execution)
- [Test Examples](#test-examples)
- [Future Enhancements](#future-enhancements)

---

## Introduction
**Cart-Selenium-TestProject** is a Selenium-based test automation framework designed for end-to-end UI testing of an open source e-commerce platform. It automates key workflows such as logging in, interacting with inventory, adding items to the cart, and performance validations. The project also includes data-driven testing for dynamic scenarios.

---

## Project Structure
```
Cart-Selenium-TestProject/
│
├── src/main/java/org/example
│   ├── Main.java             # Main execution class (if required)
├── src/test/java
│   ├── CartTest.java         # Tests for cart functionality
│   ├── LoginPageTest.java    # Tests for login functionality
│   ├── PerformanceTest.java  # Performance validation tests
│   ├── TestSuiteExecute.java # Test suite execution
│
├── utilities
│   ├── BrowserUtility.java   # Utility class for WebDriver setup
│   ├── DataUtility.java      # Utility class for handling CSV data
│
├── data.xlsx                 # Sample data for data-driven tests
├── pom.xml                   # Maven dependencies
```

---

## Key Features
1. **End-to-End Testing**:
   - Automates login, search, and cart functionalities.
   - Validates workflows and error handling.

2. **Data-Driven Testing**:
   - Uses `data.csv` for parameterized test scenarios.

3. **Performance Validation**:
   - Measures page load time and interaction times to ensure optimal performance.

4. **Reusable Utilities**:
   - **`BrowserUtility`**: Simplifies WebDriver management.
   - **`DataUtility`**: Enables dynamic data handling through CSV input.

5. **Test Suite Execution**:
   - Comprehensive suite execution using JUnit `@Suite`.

---

## Technologies Used
- **Programming Language**: Java
- **Test Framework**: JUnit 4.13.2
- **Browser Automation**: Selenium WebDriver 4.20.0
- **Dependency Management**: Maven
- **Data-Driven Tests**: CSV (handled via `DataUtility`)

---

## Setup Instructions
### Prerequisites
- Java 11+ installed.
- Maven installed and added to your PATH.
- Google Chrome installed (latest version preferred).

### Steps
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd Cart-Selenium-TestProject
   ```
2. Install dependencies:
   ```bash
   mvn clean install
   ```
3. Update the ChromeDriver path if necessary in `BrowserUtility.java`.

---

## Test Execution
### Run All Tests
Use Maven to execute the test suite:
```bash
mvn test
```



---

## Test Examples

### Example 1: Login Test
Validates the login functionality with various credentials:
```java
@Test
public void testValidLogin() {
    driver.findElement(By.id("user-name")).sendKeys("standard_user");
    driver.findElement(By.id("password")).sendKeys("secret_sauce");
    driver.findElement(By.id("login-button")).click();

    assert driver.getCurrentUrl().contains("inventory");
}
```

### Example 2: Interaction Performance Test
Measures the time taken to add an item to the cart:
```java
@Test
public void testInteractionPerformance() {
    long startTime = System.currentTimeMillis();
    driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
    driver.findElement(By.className("shopping_cart_badge"));
    long endTime = System.currentTimeMillis();

    assert endTime - startTime < 1000;
}
```

---

Sample Test Execution Output:

![image](https://github.com/user-attachments/assets/cb2864ef-e61d-4136-8e3b-ff0a4dd5650f)
