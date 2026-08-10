package com.sai.framework.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;

public final class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverFactory() {

    }


    public static void initializeDriver(String browser) {
        if (browser == null || browser.isBlank())
            throw new IllegalArgumentException("Browser name cannot be null or blank.");

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();

                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("profile.password_manager_leak_detection", false);

                options.setExperimentalOption("prefs", prefs);

                driver.set(new ChromeDriver(options));
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
