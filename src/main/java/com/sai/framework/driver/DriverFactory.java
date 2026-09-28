package com.sai.framework.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

public final class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();



    private DriverFactory() {}

    public static void initializeDriver(String browser) {

        if (browser == null || browser.isBlank())
            throw new IllegalArgumentException("Browser name cannot be null or blank.");

        boolean headless = Boolean.parseBoolean(System.getProperty("headless","false"));

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();

                if(headless){
                    chromeOptions.addArguments("--headless=new");
                }

                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("profile.password_manager_leak_detection", false);

                chromeOptions.setExperimentalOption("prefs", prefs);

                driver.set(new ChromeDriver(chromeOptions));
                System.out.println(
                        "DRIVER CREATED | Thread: "
                                + Thread.currentThread().threadId()
                                + " | Driver: "
                                + driver.get()
                );
                break;

            case "firefox":

                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (headless) {
                    firefoxOptions.addArguments("-headless");
                }

                firefoxOptions.setBinary(
                        "C:\\Users\\sai_jenkins\\.cache\\selenium\\firefox\\win64\\156.0.1\\firefox.exe"
                );

                driver.set(new FirefoxDriver(firefoxOptions));
                break;

            case "edge":

                EdgeOptions edgeOptions = new EdgeOptions();

                if (headless) {
                    edgeOptions.addArguments("--headless=new");
                }

                driver.set(new EdgeDriver(edgeOptions));
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
