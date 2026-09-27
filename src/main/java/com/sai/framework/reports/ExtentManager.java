package com.sai.framework.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extentReports;

    private ExtentManager(){}

    public static ExtentReports getInstance(){

        if(extentReports == null){

            String reportPath = System.getProperty("user.dir")+"/reports/AutomationReport.html";

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
        }
        return extentReports;
    }
}
