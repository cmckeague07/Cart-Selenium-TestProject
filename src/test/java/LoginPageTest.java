import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginPageTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Set the path of the ChromeDriver executable if needed
        String chromeDriverPath = System.getenv("CHROME_DRIVER_PATH"); // Set this in your CI environment if needed
        if (chromeDriverPath != null) {
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        } else {
            System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win64\\chromedriver-win64\\chromedriver.exe"); // Path for CI environment
        }

        driver = new ChromeDriver(); // Corrected to use the instance variable
        driver.manage().window().maximize(); // Maximize the browser window
        driver.get("https://www.saucedemo.com/");
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

    @AfterEach
    public void tearDown() {
        // Close the browser after the test is done
        if (driver != null) {
            driver.quit(); // Closes all browser windows and safely ends the session
        }
    }
}
