import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CartTest {

    private WebDriver driver;

    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();

        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Open Sauce Demo website
        driver.get("https://www.saucedemo.com/");

        // Login with valid credentials
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
    }

    @Test
    public void testAddToCartFunctionality() {
        // Add a product to the cart
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        // Verify that the cart count has updated
        WebElement cartCount = driver.findElement(By.className("shopping_cart_badge"));
        Assert.assertEquals("1", cartCount.getText());

        // Go to the cart
        driver.findElement(By.className("shopping_cart_link")).click();

        // Verify that the correct product is in the cart
        WebElement productName = driver.findElement(By.className("inventory_item_name"));
        Assert.assertEquals("Sauce Labs Backpack", productName.getText());

        // Remove the product from the cart
        driver.findElement(By.id("remove-sauce-labs-backpack")).click();

        // Verify that the cart count is now 0
        Assert.assertEquals(0, driver.findElements(By.className("shopping_cart_badge")).size());
    }

    @Test
    public void testCartCheckoutFunctionality() {
        // Add a product to the cart
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();

        // Go to the cart
        driver.findElement(By.className("shopping_cart_link")).click();

        // Navigate to checkout page
        driver.findElement(By.id("checkout")).click();

        //Enter Checkout Information
        WebElement firstName = driver.findElement(By.id("first-name"));
        WebElement lastName = driver.findElement(By.id("last-name"));
        WebElement postalCode = driver.findElement(By.id("postal-code"));

        firstName.sendKeys("Conor");
        lastName.sendKeys("McKeague");
        postalCode.sendKeys("BT46");

        driver.findElement(By.id("continue")).click();

        //Verify correct total
        // Locate all price elements in the cart
        List<WebElement> priceElements = driver.findElements(By.cssSelector(".inventory_item_price"));

        double cartPrice = 0.0;

        // Iterate through each price element and sum the prices
        for (WebElement priceElement : priceElements) {
            // Get the price text, remove "$" sign, and parse it as double
            String priceText = priceElement.getText().replace("$", "");
            double price = Double.parseDouble(priceText);
            cartPrice += price;
        }

        // Locate the displayed subtotal element
        WebElement subtotalElement = driver.findElement(By.cssSelector(".summary_subtotal_label"));

        // Extract the subtotal text and convert it to a double
        String subtotalText = subtotalElement.getText().replace("Item total: $", "");
        double displayedSubtotal = Double.parseDouble(subtotalText);

        // Assert that the calculated total matches the displayed subtotal
        Assert.assertEquals("The subtotal matches the calculated total price.", cartPrice, displayedSubtotal, 0.01);
        System.out.println("Expected Total: " + displayedSubtotal + " Actual: " + cartPrice);

        driver.findElement(By.id("finish")).click();
        String actualMessage = driver.findElement(By.className("complete-header")).getText();
        String expectedMessage = "Thank you for your order!";
        Assert.assertEquals("Checkout Completed Message Received ", expectedMessage, actualMessage);

    }

    @After
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
