package com.sai.framework.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.sai.framework.config.ReportConfig;

public class ExtentManager {

    private static ExtentReports extentReports;

    private ExtentManager(){}

    public static ExtentReports getInstance(){

        if(extentReports == null){

            extentReports = new ExtentReports();

            if(ReportConfig.isHtmlEnabled()){

                String reportPath = System.getProperty("user.dir")+"/reports/AutomationReport.html";

                ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

                extentReports.attachReporter(sparkReporter);

            }

        }
        return extentReports;
    }
}
