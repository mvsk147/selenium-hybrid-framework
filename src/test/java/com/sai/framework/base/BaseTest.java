package com.sai.framework.base;

import com.sai.framework.config.ConfigReader;
import com.sai.framework.driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp(){

        DriverFactory.initializeDriver(ConfigReader.getBrowser());
        driver = DriverFactory.getDriver();

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getPageLoadTimeout()));
        driver.get(ConfigReader.getUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        DriverFactory.quitDriver();
    }
}