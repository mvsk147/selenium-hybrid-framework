package com.sai.framework.base;

import com.sai.framework.config.ConfigReader;
import com.sai.framework.loggers.FrameworkLogger;
import com.sai.framework.utils.HighlightUtils;
import com.sai.framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public abstract class BasePage {

    protected final WebDriver driver;

    protected final Actions actions;

    protected final JavascriptExecutor js;

    protected final long explicitWait;

    protected BasePage(WebDriver driver){

        this.driver = driver;
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor)driver;
        this.explicitWait = ConfigReader.getExplicitWait();
    }

    protected WebElement getVisibleElement(By locator){

        WebElement element = WaitUtils.waitForVisibility(driver, locator, explicitWait);
        HighlightUtils.highlight(driver,element);

        return element;
    }

    protected void hover(By locator){

        try {

            FrameworkLogger.info(getClass(), "attempting to hover on: "+locator);

            WebElement element = getVisibleElement(locator);
            actions.moveToElement(element).perform();

            FrameworkLogger.info(getClass(), "successfully hovered on: "+locator);
        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"Failed to hover on locator "+locator,e);
            throw e;
        }

    }

    protected void doubleClick(By locator){

        try{

            FrameworkLogger.info(getClass(),"attempting to double click on "+locator);

            WebElement element = getVisibleElement(locator);
            actions.doubleClick(element).perform();

            FrameworkLogger.info(getClass(),"successfully performed double click on "+locator);

        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"Failed to double click on "+locator,e);
            throw e;
        }

    }

    protected void rightClick(By locator){

        try{
            FrameworkLogger.info(getClass(),"attempting to right click on "+locator);

            WebElement element = getVisibleElement(locator);
            actions.contextClick(element).perform();

            FrameworkLogger.info(getClass(),"successfully performed right click on "+locator);
        } catch (Exception e) {
            FrameworkLogger.error(getClass(),"failed to right click on "+locator,e);
            throw e;
        }

    }

    protected void dragAndDrop(By source, By target){

        try{
            FrameworkLogger.info(getClass(),"attempting drag element from: "+source+" to: "+target);

            WebElement sourceElement = getVisibleElement(source);
            WebElement targetElement = getVisibleElement(target);

            actions.dragAndDrop(sourceElement, targetElement).perform();

            FrameworkLogger.info(getClass(),"successfully dragged element from: "+source+" to: "+target);
        } catch (Exception e) {
            FrameworkLogger.error(getClass(),"failed to drag and drop at "+target, e);
            throw e;
        }

    }

    protected void clickAndHold(By locator){

        try{
            FrameworkLogger.info(getClass(),"attempting click and hold on "+locator);

            WebElement element = getVisibleElement(locator);

            actions.clickAndHold(element).perform();

            FrameworkLogger.info(getClass(),"successfully click and hold the "+locator);

        } catch (Exception e) {
            FrameworkLogger.error(getClass(),"failed to click and hold "+locator, e);
            throw e;
        }

    }

    protected void release(By locator){

        try{
            FrameworkLogger.info(getClass(),"attempting to release the "+locator);

            WebElement element = getVisibleElement(locator);

            actions.release(element).perform();

            FrameworkLogger.info(getClass(),"successfully released the "+locator);

        } catch (Exception e) {
            FrameworkLogger.error(getClass(),"failed to release the "+locator, e);
            throw e;
        }

    }

    protected void jsClick(By locator){

        try {

            FrameworkLogger.info(getClass(), "attempting js click on " + locator);

            WebElement element = getVisibleElement(locator);
            js.executeScript("arguments[0].click();", element);

            FrameworkLogger.info(getClass(), "successfully clicked on " + locator);
        } catch (Exception e) {
            FrameworkLogger.error(getClass(),"failed to click on "+locator,e);
            throw e;
        }

    }

    protected void scrollIntoView(By locator){
        try {

            FrameworkLogger.info(getClass(), "attempting to scroll into view on" + locator);

            WebElement element = getVisibleElement(locator);
            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth',block:'center'});", element);

            FrameworkLogger.info(getClass(), "successfully scrolled into view " + locator);

        } catch (Exception e) {
            FrameworkLogger.error(getClass(),"failed to scroll into view on "+locator,e);
            throw e;
        }
    }

    protected void scrollToTop(){

        try {

            FrameworkLogger.info(getClass(), "attempting to scroll to top");

            js.executeScript("window.scrollTo(0,0);");

            FrameworkLogger.info(getClass(), "successfully scrolled to top");

        } catch (Exception e) {
            FrameworkLogger.error(getClass(),"failed to scroll to top",e);
            throw e;
        }
    }

    protected void scrollToBottom(){

        try {

            FrameworkLogger.info(getClass(), "attempting to scroll to bottom");

            js.executeScript("window.scrollTo(0,document.body.scrollHeight);");

            FrameworkLogger.info(getClass(), "successfully scrolled to bottom");

        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to scroll to bottom",e);
            throw e;
        }
    }

    protected void scrollBy(int x, int y){

        try {

            FrameworkLogger.info(getClass(), "attempting to scroll ");

            js.executeScript("window.scrollTo(arguments[0],arguments[1]);",x,y);

            FrameworkLogger.info(getClass(), "successfully scrolled");

        } catch (Exception e) {
            FrameworkLogger.error(getClass(),"failed to scroll",e);
            throw e;
        }
    }

    protected String getPageTitle(){

        try {

            FrameworkLogger.info(getClass(), "Retrieving page title");
            String title = driver.getTitle();
            FrameworkLogger.info(getClass(), "successfully retrieved the page title");

            return title;
        } catch (Exception e) {
            FrameworkLogger.error(getClass(),"failed to get page title",e);
            throw e;
        }
    }


    protected void type(By locator, String text){

        try {

            FrameworkLogger.info(getClass(),"Typing into: "+locator);

            WebElement element = WaitUtils.waitForVisibility(driver, locator, explicitWait);
            element.sendKeys(text);

            FrameworkLogger.info(getClass(),"Successfully entered text");
        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"Failed to type into:"+locator,e);
            throw e;
        }

    }

    protected void click(By locator){

        try {

            FrameworkLogger.info(getClass(), "Attempting to click: "+locator);

            WebElement element = getVisibleElement(locator);
            element.click();

            FrameworkLogger.info(getClass(), "Successfully clicked: " + locator);

        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"Failed to click: "+locator, e);
            throw e;
        }
    }

    protected void clearAndType(By locator, String text){
        try{
            FrameworkLogger.info(getClass(),"Clearing and typing into:"+locator);

            WebElement element = getVisibleElement(locator);
            element.clear();
            element.sendKeys(text);

            FrameworkLogger.info(getClass(),"Successfully entered text");
        } catch (Exception e) {
            FrameworkLogger.error(getClass(),"Failed to clear and type into "+ locator, e);
            throw e;
        }

        }

    protected String getText(By locator){

        try {

            FrameworkLogger.info(getClass(),"Getting text from:"+locator);

            WebElement element = getVisibleElement(locator);
            String text = element.getText();

            FrameworkLogger.info(getClass(),"Retrieved text successfully");

            return text;

        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"Failed to get text from "+locator,e);
            throw e;
        }

    }

    protected boolean isDisplayed(By locator){
        try {

            WebElement element = WaitUtils.waitForVisibility(driver, locator, explicitWait);
            return element.isDisplayed();
        } catch (Exception e) {

            FrameworkLogger.error(getClass(), "Element not displayed: "+locator,e);
            throw e;
        }
    }

}
