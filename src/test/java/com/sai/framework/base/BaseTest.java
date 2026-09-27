package com.sai.framework.base;

import com.sai.framework.config.ConfigReader;
import com.sai.framework.driver.DriverFactory;
import com.sai.framework.loggers.FrameworkLogger;
import org.apache.commons.math3.special.BesselJ;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.time.Duration;

public class BaseTest {

     protected WebDriver getDriver(){
         return DriverFactory.getDriver();
     }

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(String browser){

        System.out.println("==========SETUP STARTED===========");

//        DriverFactory.initializeDriver(ConfigReader.getBrowser());
        DriverFactory.initializeDriver(browser);
        System.out.println(
                "DRIVER BEFORE TEST | Thread: "
                        + Thread.currentThread().threadId()
                        + " | Driver: "
                        + DriverFactory.getDriver()
        );
        WebDriver driver = getDriver();

        FrameworkLogger.info(getClass(),"Thread: "+Thread.currentThread().threadId()
                +" | Driver: "+driver+" | Test: "+getClass().getSimpleName());

        FrameworkLogger.info(BaseTest.class,"Environment: "+ConfigReader.getEnvironment());

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getPageLoadTimeout()));
        driver.get(ConfigReader.getUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){

        FrameworkLogger.info(getClass(),"Thread: "+ Thread.currentThread().threadId()
                +" | Test: "+getClass().getSimpleName());

        DriverFactory.quitDriver();
    }
}