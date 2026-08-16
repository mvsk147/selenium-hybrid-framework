package com.sai.framework.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HighlightUtils {

    private HighlightUtils(){}

    public static void highlight(WebDriver driver, WebElement element){

        JavascriptExecutor js = (JavascriptExecutor) driver;

        String originalStyle = element.getAttribute("style");
        try {

            js.executeScript("arguments[0].setAttribute('style',arguments[1]);", element,originalStyle+
                    "border: 3px solid red;");
            Thread.sleep(200);

            js.executeScript("arguments[0].setAttribute('style',arguments[1]);",element,originalStyle);
        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }

    }
}
