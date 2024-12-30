package org.example;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
public class BrowserUtility {
    private static WebDriver driver;

    // Method to initialize the WebDriver
    public static WebDriver getDriver() {
        if (driver == null) {
            String chromeDriverPath = System.getenv("CHROME_DRIVER_PATH"); // Check for CI environment variable
            if (chromeDriverPath != null) {
                System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            } else {
                System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win64\\chromedriver-win64\\chromedriver.exe");
            }

            driver = new ChromeDriver(); // Initialize ChromeDriver
            driver.manage().window().maximize(); // Maximize browser window
        }
        return driver;
    }

    // Method to quit the WebDriver
    public static void quitDriver() {
        if (driver != null) {
            driver.quit(); // Closes all browser windows and ends the session
            driver = null; // Reset driver instance to avoid reuse
        }
    }

}
