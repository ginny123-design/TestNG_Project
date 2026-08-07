package com.example.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<>();

    public static ExtentReports getInstance() {
        if (extent == null) {
            synchronized (ExtentReportManager.class) {
                if (extent == null) {
                    String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                    String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport_" + timestamp + ".html";

                    File reportDir = new File(System.getProperty("user.dir") + "/test-output");
                    if (!reportDir.exists()) {
                        reportDir.mkdirs();
                    }

                    ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
                    sparkReporter.config().setDocumentTitle("Test Automation Execution Report");
                    sparkReporter.config().setReportName("TestNG Excel Data Driven Test Report");
                    sparkReporter.config().setTheme(Theme.DARK);
                    sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");

                    extent = new ExtentReports();
                    extent.attachReporter(sparkReporter);
                    extent.setSystemInfo("Operating System", System.getProperty("os.name"));
                    extent.setSystemInfo("Java Version", System.getProperty("java.version"));
                    extent.setSystemInfo("User", System.getProperty("user.name"));
                    extent.setSystemInfo("Environment", "QA Automation");
                }
            }
        }
        return extent;
    }

    public static ExtentTest createTest(String testName, String description) {
        ExtentTest test = getInstance().createTest(testName, description);
        testThreadLocal.set(test);
        return test;
    }

    public static ExtentTest getTest() {
        ExtentTest test = testThreadLocal.get();
        if (test == null) {
            test = createTest("Test Execution", "Auto-initialized ExtentTest logger");
        }
        return test;
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
