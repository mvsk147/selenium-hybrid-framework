package com.sai.framework.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.sai.framework.config.ConfigReader;
import com.sai.framework.config.ReportConfig;
import com.sai.framework.loggers.FrameworkLogger;
import com.sai.framework.reports.ExtentManager;
import com.sai.framework.reports.ExtentTestManager;
import com.sai.framework.utils.ScreenshotUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TestListener implements ITestListener {

    private static final Map<String, Integer> datasetIndexes = new ConcurrentHashMap<>();
    private static final AtomicInteger datasetCounter = new AtomicInteger(0);
    private static final Map<String, AtomicInteger> datasetCounters = new ConcurrentHashMap<>();
    private static final Map<String, ExtentTest> extentTests = new ConcurrentHashMap<>();

    private String createTestKey(ITestResult result){
//        String browser = result.getTestContext().getCurrentXmlTest().getParameter("browser");
        String browser = ConfigReader.getBrowser();
        String testName = result.getMethod().getMethodName();
        String parameters = Arrays.deepToString(result.getParameters());

        return browser + "|" + testName + "|" + parameters;
    }

    private int getDatasetIndex(ITestResult result){

//        String browser = result.getTestContext().getCurrentXmlTest().getParameter("browser");
        String browser = ConfigReader.getBrowser();
        String testName = result.getMethod().getMethodName();
        String testKey = browser + "|" + testName;
        String datasetKey = testKey + "|" + Arrays.deepToString(result.getParameters());

        Integer existingIndex = datasetIndexes.get(datasetKey);

        if(existingIndex != null){
            return existingIndex;
        }

        AtomicInteger counter = datasetCounters.computeIfAbsent(testKey, key -> new AtomicInteger(0));

        int newIndex = counter.incrementAndGet();
        Integer previous = datasetIndexes.putIfAbsent(datasetKey, newIndex);

        return previous != null ? previous : newIndex;

    }

    private void removeExtentTest(ITestResult result) {

        String testKey = createTestKey(result);

        extentTests.remove(testKey);
    }

    @Override
    public void onStart(ITestContext context) {

        if(ReportConfig.isHtmlEnabled()) {

            ExtentManager.getInstance();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {

        if(!ReportConfig.isHtmlEnabled()){
            return;
        }

        String testKey = createTestKey(result);
        ExtentTest test = extentTests.computeIfAbsent(
                testKey,
                key -> {
                    String testName = result.getMethod().getMethodName();
                    int dataIndex = getDatasetIndex(result);

//                    String browser = result.getTestContext().getCurrentXmlTest().getParameter("browser");
                    String browser = ConfigReader.getBrowser();
                    String reportName = browser+" | "+ testName+" | Dataset "+dataIndex;
                    return ExtentManager.getInstance().createTest(reportName);

                }

        );

        ExtentTestManager.setTest(test);

    }

    @Override
    public void onTestSuccess(ITestResult result) {

        if(!ReportConfig.isHtmlEnabled()){
            return;
        }

        ExtentTest test = ExtentTestManager.getTest();

        if(test != null){
            test.log(Status.PASS, "Test Passed");

            String screenshotPath = ScreenshotUtils.takeScreenshot(result.getMethod().getMethodName());
            if(screenshotPath != null){
                try{
                    test.addScreenCaptureFromPath(screenshotPath);
                } catch (Exception e){
                    FrameworkLogger.error(TestListener.class,"Unable to attach successful test screenshot to extent report",e);
                }
            }
        } else {
            FrameworkLogger.warn(TestListener.class, "Extent test is not available for successful test: "+result.getMethod().getMethodName());
        }

        removeExtentTest(result);
        ExtentTestManager.unload();

    }

    @Override
    public void onTestFailure(ITestResult result) {

        if(!ReportConfig.isHtmlEnabled()){
            return;
        }

        if (result.wasRetried()) {
            return;
        }

        ExtentTest test = ExtentTestManager.getTest();

        if (test != null) {

            test.fail(result.getThrowable());

            String screenshotPath =
                    ScreenshotUtils.takeScreenshot(
                            result.getMethod().getMethodName()
                    );

            if (screenshotPath != null) {

                try {
                    test.addScreenCaptureFromPath(screenshotPath);

                } catch (Exception e) {

                    FrameworkLogger.error(
                            TestListener.class,
                            "Unable to attach screenshot to Extent Report",
                            e
                    );
                }
            }

        } else {

            FrameworkLogger.error(
                    TestListener.class,
                    "ExtentTest is not available for failed test: "
                            + result.getMethod().getMethodName(),
                    result.getThrowable()
            );
        }

        removeExtentTest(result);
        ExtentTestManager.unload();
    }


    @Override
    public void onTestSkipped(ITestResult result) {

        if(!ReportConfig.isHtmlEnabled()){
            return;
        }

        if(result.wasRetried()){
            return;
        }

        ExtentTest test = ExtentTestManager.getTest();

        if(test != null){
            test.skip("Test Skipped");
        }

        removeExtentTest(result);
        ExtentTestManager.unload();


    }

    @Override
    public void onFinish(ITestContext context) {

        if(ReportConfig.isHtmlEnabled()) {

            ExtentManager.getInstance().flush();
        }

    }
}
