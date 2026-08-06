package com.sai.framework.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public final class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverFactory() {

    }


    public static void initializeDriver(String browser) {
        if (browser == null || browser.isBlank())
            throw new IllegalArgumentException("Browser name cannot be null or blank.");

        switch (browser.toLowerCase()) {
            case "chrome":
                driver.set(new ChromeDriver());
                break;
            case "firefox":
                driver.set(new FirefoxDriver());
                break;
            case "edge":
                driver.set(new EdgeDriver());
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser + ". Supported browsers are: chrome, edge, firefox");
        }
    }

    public static WebDriver getDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver == null)
            throw new IllegalStateException("Webdriver is not initialized. Call initializeDriver() before using the getDriver().");

        return webDriver;
    }

    public static void quitDriver(){
        WebDriver webDriver = driver.get();
        if(webDriver!=null)
            webDriver.quit();
        driver.remove();
    }


}
