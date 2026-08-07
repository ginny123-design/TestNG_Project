package com.example.listeners;

import com.example.utils.ExtentReportManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        System.out.println("=== Starting Test Suite Execution: " + context.getName() + " ===");
        ExtentReportManager.getInstance();
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        Object[] params = result.getParameters();

        StringBuilder paramString = new StringBuilder();
        if (params != null && params.length > 0) {
            paramString.append(" [Data: ");
            for (int i = 0; i < params.length; i++) {
                paramString.append(params[i]);
                if (i < params.length - 1) paramString.append(", ");
            }
            paramString.append("]");
        }

        ExtentTest test = ExtentReportManager.createTest(testName + paramString.toString(), description);
        test.log(Status.INFO, "Test execution started for: " + testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.PASS, "Test PASSED: " + result.getMethod().getMethodName());
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.FAIL, "Test FAILED: " + result.getMethod().getMethodName());
            test.log(Status.FAIL, result.getThrowable());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.SKIP, "Test SKIPPED: " + result.getMethod().getMethodName());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("=== Finished Test Suite Execution: " + context.getName() + " ===");
        ExtentReportManager.flush();
    }
}
