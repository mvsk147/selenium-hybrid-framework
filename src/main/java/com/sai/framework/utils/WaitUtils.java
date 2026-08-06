package com.sai.framework.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class WaitUtils {

    private WaitUtils(){}

    private static WebDriverWait createWait(WebDriver driver, long timeoutInSeconds){
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }

    public static WebElement waitForVisibility(WebDriver driver, By locator, long timeoutInSeconds){

        return createWait(driver,timeoutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));

    }

    public static WebElement waitForClickable(WebDriver driver, By locator, long timeoutInSeconds){

        return createWait(driver,timeoutInSeconds).until(ExpectedConditions.elementToBeClickable(locator));
    }
}
