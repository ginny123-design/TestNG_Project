package com.example.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        if (driver == null) return null;
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            String destDir = System.getProperty("user.dir") + "/test-output/screenshots/";
            File dir = new File(destDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String destPath = destDir + screenshotName + "_" + timestamp + ".png";
            File destination = new File(destPath);
            FileUtils.copyFile(source, destination);
            return destPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
