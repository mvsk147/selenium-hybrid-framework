package com.sai.framework.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.sai.framework.loggers.FrameworkLogger;
import com.sai.framework.reports.ExtentManager;
import com.sai.framework.reports.ExtentTestManager;
import com.sai.framework.utils.ScreenshotUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {

        ExtentManager.getInstance();
    }

    @Override
    public void onTestStart(ITestResult result) {

        ExtentTestManager.setTest(ExtentManager.getInstance().createTest(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        ExtentTestManager.getTest().log(Status.PASS,"Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        String screenshotPath = ScreenshotUtils.takeScreenshot(result.getMethod().getMethodName());

        ExtentTestManager.getTest().fail(result.getThrowable());

        try{

            ExtentTestManager.getTest().addScreenCaptureFromPath(screenshotPath);
        } catch (Exception e) {

            FrameworkLogger.error(TestListener.class,"Unable to attach screenshot to Extent Report",e);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        ExtentTestManager.getTest().log(Status.SKIP,"Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {

        ExtentManager.getInstance().flush();
        ExtentTestManager.unload();
    }
}
