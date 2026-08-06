package com.sai.framework.utils;

import com.sai.framework.base.BasePage;
import com.sai.framework.driver.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ScreenshotUtils {

    private ScreenshotUtils(){}

    public static String takeScreenshot(String screenshotName){
        WebDriver driver = DriverFactory.getDriver();
        File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String destination = System.getProperty("user.dir")+"/screenshots/"+screenshotName+"_"+timestamp+".png";

        try {
            FileUtils.copyFile(source, new File(destination));
        } catch (IOException e) {
            throw new RuntimeException("Unable to save screenshot.",e);
        }

        return destination;

    }
}
