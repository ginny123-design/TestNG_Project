package com.example.tests;

import com.example.listeners.TestListener;
import com.example.utils.ExcelUtils;
import com.example.utils.ExtentReportManager;
import com.example.utils.ScreenshotUtils;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;

@Listeners(TestListener.class)
public class DataDrivenTest {

    private WebDriver driver;
    private static final String TARGET_URL = "https://practicetestautomation.com/practice-test-login/";

    @BeforeMethod
    public void setUp() {
        // Read -Dheadless property from CLI (defaults to false for headed GUI mode)
        String headlessProperty = System.getProperty("headless", "false");
        boolean isHeadless = Boolean.parseBoolean(headlessProperty);

        ChromeOptions options = new ChromeOptions();
        if (isHeadless) {
            options.addArguments("--headless=new");
        } else {
            options.addArguments("--start-maximized");
        }
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @DataProvider(name = "webLoginData")
    public Object[][] getLoginData() {
        String excelFilePath = System.getProperty("user.dir") + "/src/test/resources/testdata.xlsx";
        File file = new File(excelFilePath);
        if (!file.exists()) {
            throw new RuntimeException("Excel data file not found at path: " + excelFilePath);
        }

        ExcelUtils excelUtils = new ExcelUtils(excelFilePath);
        return excelUtils.getSheetData("LoginTests");
    }

    @Test(dataProvider = "webLoginData", description = "Web Automation: Validate Login functionality using Excel test data")
    public void testWebLogin(String username, String password, String expectedOutcome, String testDescription) throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Navigating to URL: " + TARGET_URL);
        driver.get(TARGET_URL);

        String headlessProperty = System.getProperty("headless", "false");
        boolean isHeadless = Boolean.parseBoolean(headlessProperty);
        if (!isHeadless) {
            Thread.sleep(1000);
        }

        ExtentReportManager.getTest().log(Status.INFO, "Entering Username from Excel: " + username);
        WebElement usernameInput = driver.findElement(By.id("username"));
        usernameInput.clear();
        usernameInput.sendKeys(username);
        if (!isHeadless) Thread.sleep(1000);

        ExtentReportManager.getTest().log(Status.INFO, "Entering Password from Excel: " + password);
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.clear();
        passwordInput.sendKeys(password);
        if (!isHeadless) Thread.sleep(1000);

        ExtentReportManager.getTest().log(Status.INFO, "Clicking Submit Button...");
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        if (!isHeadless) Thread.sleep(1500);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        if ("Success".equalsIgnoreCase(expectedOutcome)) {
            ExtentReportManager.getTest().log(Status.INFO, "Validating successful login landing page...");
            wait.until(ExpectedConditions.urlContains("logged-in-successfully"));

            WebElement successHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("post-title")));
            Assert.assertTrue(successHeader.getText().contains("Logged In Successfully"), "Success header missing!");

            String screenshotPath = ScreenshotUtils.captureScreenshot(driver, "Login_Success_" + username);
            ExtentReportManager.getTest().log(Status.PASS, "Successfully logged in for user: " + username,
                    com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());

        } else {
            ExtentReportManager.getTest().log(Status.INFO, "Validating login error message for expected outcome: " + expectedOutcome);
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
            Assert.assertTrue(errorMessage.isDisplayed(), "Error message element is not displayed!");

            String screenshotPath = ScreenshotUtils.captureScreenshot(driver, "Login_Error_" + username);
            ExtentReportManager.getTest().log(Status.PASS, "Expected login error verified successfully: " + errorMessage.getText(),
                    com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        }
        if (!isHeadless) Thread.sleep(1000);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
