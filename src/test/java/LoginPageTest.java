import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.example.BrowserUtility;
import org.example.DataUtility;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Test;
import java.time.Duration;
import java.util.List;

public class LoginPageTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = BrowserUtility.getDriver(); // Use BrowserUtils to get the WebDriver
        driver.get("https://www.saucedemo.com/"); // Navigate to the test site
    }

    @After
    public void tearDown() {
        BrowserUtility.quitDriver(); // Use BrowserUtils to quit the WebDriver
    }

    @Test
    public void testLogin(){
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        WebElement productTitle = driver.findElement(By.className("title"));
        assertTrue(productTitle.isDisplayed(), "Login Failed: Products header is not displayed");
    }

    @Test
    public void testLoginUsernameNegative(){
        WebElement usernameFieldNegative = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameFieldNegative.sendKeys("standard_user123456");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        // Assert that the error message is displayed
        String expectedMessage = "Epic sadface: Username and password do not match any user in this service";
        WebElement errorMessage = driver.findElement(By.xpath("//h3[contains(text(),'Epic sadface: Username and password do not match any user in this service')]"));
        String actualMessage = errorMessage.getText();

        assertEquals(expectedMessage, actualMessage, "Error message does not match expected message.");
    }

    @Test
    public void testLoginPasswordNegative(){
        WebElement usernameFieldNegative = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameFieldNegative.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce12345");
        loginButton.click();

        // Assert that the error message is displayed
        String expectedMessage = "Epic sadface: Username and password do not match any user in this service";
        WebElement errorMessage = driver.findElement(By.xpath("//h3[contains(text(),'Epic sadface: Username and password do not match any user in this service')]"));
        String actualMessage = errorMessage.getText();

        assertEquals(expectedMessage, actualMessage, "Error message does not match expected message.");
    }

    @Test
    public void testLoginWithMultipleData() {
        WebDriver driver = BrowserUtility.getDriver();
        driver.get("https://www.saucedemo.com/");

        // Use DataUtility to read data from CSV
        String filePath = "src/test/resources/data.csv"; // Ensure the file path is correct
        List<String[]> testData = DataUtility.readCSV(filePath);

        // Loop through each set of credentials
        for (String[] credentials : testData) {
            String username = credentials[0];
            String password = credentials[1];

            // Add explicit waits for dynamic elements
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
            WebElement passwordField = driver.findElement(By.id("password"));
            WebElement loginButton = driver.findElement(By.id("login-button"));

            // Perform login
            usernameField.clear(); // Clear the input field
            usernameField.sendKeys(username);
            passwordField.clear();
            passwordField.sendKeys(password);
            loginButton.click();

            // Validate login (e.g., URL change or error message)
            WebElement errorMessage = driver.findElement(By.cssSelector(".error-message-container"));
            if (errorMessage.isDisplayed()) {
                System.out.println("Login failed for: " + username);
            } else {
                System.out.println("Login successful for: " + username);
            }

            // Navigate back to reset the state for the next iteration
            driver.navigate().refresh();
        }
    }


}
