import org.example.BrowserUtility;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class PerformanceTest {

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
    public void testSearchPagePerformance() {
        WebDriver driver = BrowserUtility.getDriver();
        driver.get("https://www.saucedemo.com/");

        // Perform login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Confirm login successful and navigate to inventory page
        String currentUrl = driver.getCurrentUrl();
        assert currentUrl.contains("inventory") : "Login failed or incorrect page! Current URL: " + currentUrl;

        // Measure performance: Use JavaScript to get page load time
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long loadTime = (Long) js.executeScript(
                "return window.performance.timing.loadEventEnd - window.performance.timing.navigationStart;"
        );

        System.out.println("Page Load Time: " + loadTime + " ms");

        // Assert that the load time is within acceptable limits
        assert loadTime < 2000 : "Page load time exceeded acceptable threshold!";

    }

    @Test
    public void testInteractionPerformance() {
        WebDriver driver = BrowserUtility.getDriver();
        driver.get("https://www.saucedemo.com/");

        // Perform login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Confirm login successful
        String currentUrl = driver.getCurrentUrl();
        assert currentUrl.contains("inventory") : "Login failed or incorrect page! Current URL: " + currentUrl;

        // Start the timer
        long startTime = System.currentTimeMillis();

        // Perform an interaction (e.g., adding a product to the cart)
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        // Wait for dynamic content (e.g., cart icon count) to load
        driver.findElement(By.className("shopping_cart_badge"));

        // End the timer
        long endTime = System.currentTimeMillis();
        long interactionTime = endTime - startTime;

        System.out.println("Interaction Time: " + interactionTime + " ms");

        // Assert that the interaction time is within acceptable limits
        assert interactionTime < 1000 : "Interaction time exceeded acceptable threshold!";
    }

}