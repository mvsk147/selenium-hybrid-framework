package com.sai.framework.base;

import com.sai.framework.config.ConfigReader;
import com.sai.framework.loggers.FrameworkLogger;
import com.sai.framework.utils.HighlightUtils;
import com.sai.framework.utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class BasePage {

    protected final WebDriver driver;

    protected final Actions actions;

    protected final JavascriptExecutor js;

    protected final long explicitWait;

    protected final String parentWindow;

    protected BasePage(WebDriver driver){

        this.driver = driver;
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor)driver;
        this.explicitWait = ConfigReader.getExplicitWait();
        this.parentWindow = driver.getWindowHandle();
    }

    /*
        helper method
     */

    protected WebElement getVisibleElement(By locator){

        WebElement element = WaitUtils.waitForVisibility(driver, locator, explicitWait);
        HighlightUtils.highlight(driver,element);

        return element;
    }

    /*
        Mouse click methods
     */

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

    /*
        Java Script executor methods
     */

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


    /*
        element action methods
     */

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

    /*
        Dropdown / Select methods
     */

    private Select getSelect(By locator){

        WebElement element = getVisibleElement(locator);

        return new Select(element);
    }

    protected void selectByVisibleText(By locator, String text){

        try{

            FrameworkLogger.info(getClass(),"attempting to select '"+text+"' from "+locator);

            Select select = getSelect(locator);
            select.selectByVisibleText(text);

            FrameworkLogger.info(getClass(),"successfully selected '"+text+"' from "+locator);
        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to select '"+text+"' "+locator, e);
            throw e;
        }
    }

    protected void selectByValue(By locator, String value){

        try{

            FrameworkLogger.info(getClass(),"attempting to select '"+value+"' from "+locator);

            Select select = getSelect(locator);
            select.selectByValue(value);

            FrameworkLogger.info(getClass(),"successfully selected '"+value+"' from "+locator);
        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to select value '"+value+"' from "+locator, e);
            throw e;
        }
    }

    protected void selectByIndex(By locator, int index){

        try{

            FrameworkLogger.info(getClass(),"attempting to select '"+index+"' from "+locator);

            Select select = getSelect(locator);
            select.selectByIndex(index);

            FrameworkLogger.info(getClass(),"successfully selected '"+index+"' from "+locator);
        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to select index '"+index+"' from "+locator, e);
            throw e;
        }
    }


    /*

     */

    protected String getSelectedOption(By locator){

        try{

            FrameworkLogger.info(getClass(),"Retrieving selected option from "+locator);

            String selectedOption = getSelect(locator).getFirstSelectedOption().getText();

            FrameworkLogger.info(getClass(),"successfully retrieved selected option from "+locator);

            return selectedOption;

        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"Failed to retrieve selected option from "+locator,e);
            throw e;
        }
    }

    protected List<String> getAllOptions(By locator){

        try {

            FrameworkLogger.info(getClass(), "Retrieving all dropdown options from " + locator);

            List<String> options = new ArrayList<>();

            for (WebElement element : getSelect(locator).getOptions()) {

                options.add(element.getText());
            }

            FrameworkLogger.info(getClass(), "Retrieved " + options.size() + " options");

            return options;
        } catch (Exception e) {
            FrameworkLogger.error(getClass(),"Failed to retrieve dropdown options from "+locator,e);
            throw e;
        }

    }

    protected boolean isMultiple(By locator){

        try{

            FrameworkLogger.info(getClass(), "attempting to check the multiple options at " + locator);

            boolean status = getSelect(locator).isMultiple();

            FrameworkLogger.info(getClass(), "Dropdown multiple selection status " + status);

            return status;
        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"Failed to check multiple options at "+locator,e);
            throw e;
        }
    }

    protected void deselectAll(By locator){
        try{

            FrameworkLogger.info(getClass(), "attempting to deselect all options in " + locator);

            if(!isMultiple(locator)){
                throw new UnsupportedOperationException("Dropdown is not a multi select: "+locator);
            }

            getSelect(locator).deselectAll();

            FrameworkLogger.info(getClass(), "successfully deselected all options at " + locator);

        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"Failed to deselect options at "+locator,e);
            throw e;
        }
    }


    protected void deselectByVisibleText(By locator, String text){

        try{

            FrameworkLogger.info(getClass(),"attempting to deselect text '"+text+"' from "+locator);

            Select select = getSelect(locator);
            select.deselectByVisibleText(text);

            FrameworkLogger.info(getClass(),"successfully deselected text '"+text+"' from "+locator);
        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to deselect text '"+text+"' "+locator, e);
            throw e;
        }
    }

    protected void deselectByValue(By locator, String value){

        try{

            FrameworkLogger.info(getClass(),"attempting to deselect value '"+value+"' from "+locator);

            Select select = getSelect(locator);
            select.deselectByValue(value);

            FrameworkLogger.info(getClass(),"successfully deselected value '"+value+"' from "+locator);
        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to deselect value '"+value+"' from "+locator, e);
            throw e;
        }
    }

    protected void deselectByIndex(By locator, int index){

        try{

            FrameworkLogger.info(getClass(),"attempting to deselect index '"+index+"' from "+locator);

            Select select = getSelect(locator);
            select.deselectByIndex(index);

            FrameworkLogger.info(getClass(),"successfully deselected index '"+index+"' from "+locator);
        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to deselect index '"+index+"' from "+locator, e);
            throw e;
        }
    }


    /*
        Alerts methods
     */

    private Alert getAlert(){

        return driver.switchTo().alert();
    }

    protected void acceptAlert(){

        try{

            FrameworkLogger.info(getClass(),"attempting to accept the alert");

            Alert alert = getAlert();
            alert.accept();

            FrameworkLogger.info(getClass(),"successfully accepted the alert");

        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to accept the alert",e);
            throw e;
        }
    }

    protected void dismissAlert(){

        try{

            FrameworkLogger.info(getClass(),"attempting to dismiss the alert");

            Alert alert = getAlert();
            alert.dismiss();

            FrameworkLogger.info(getClass(),"successfully dismissed the alert");

        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to dismiss the alert",e);
            throw e;
        }
    }

    protected String getAlertText(){

        try{

            FrameworkLogger.info(getClass(),"attempting to retrieve the alert text");

            Alert alert = getAlert();
            String text = alert.getText();

            FrameworkLogger.info(getClass(),"Alert text: "+text);

            return text;

        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to retrieve the alert text",e);
            throw e;
        }
    }

    protected void sendKeysToAlert(String text){

        try{

            FrameworkLogger.info(getClass(),"attempting to send the text to alert");

            Alert alert = getAlert();
            alert.sendKeys(text);

            FrameworkLogger.info(getClass(),"successfully sent the text to alert");

        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to send the text to alert",e);
            throw e;
        }

    }

    protected boolean isAlertPresent(){

        try{

            Alert alert = getAlert();

            return true;

        } catch (NoAlertPresentException e) {

           return false;
        }

    }

    /*
        Window handles
     */

    private Set<String> getWindowHandles(){

        return driver.getWindowHandles();

    }

    protected int getWindowCount(){

        try {

            FrameworkLogger.info(getClass(),"attempting to get window count");

            int count = getWindowHandles().size();

            FrameworkLogger.info(getClass(),"successfully retrieved the count: "+count);

            return count;

        } catch (Exception e) {
            FrameworkLogger.error(getClass(),"failed to get the count",e);
            throw e;
        }
    }

    protected void closeCurrentWindow(){

        try {

            FrameworkLogger.info(getClass(),"trying to close the current window");

            driver.close();

            FrameworkLogger.info(getClass(),"successfully closed the current window");

        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to close the current window",e);
            throw e;
        }

    }

    protected void switchToWindow(int index){

        try{

            FrameworkLogger.info(getClass(),"attempting to switch to window");

            List<String> windows = new ArrayList<>(getWindowHandles());
            driver.switchTo().window(windows.get(index));

            FrameworkLogger.info(getClass(),"successfully switched to new window");


        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to switch to new window ",e);
            throw e;
        }

    }

    protected void switchToWindow(String title){

        boolean switched = false;

        try{

            FrameworkLogger.info(getClass(),"attempting to switch to window");

            for (String window : getWindowHandles()) {

                driver.switchTo().window(window);
                if (getPageTitle().equalsIgnoreCase(title)) {
                    switched = true;
                    break;
                }
            }

            if(!switched){
                throw new NoSuchWindowException("No window found with title: "+title);
            }

            FrameworkLogger.info(getClass(),"successfully switched to new window");


        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to switch to new window ",e);
            throw e;
        }

    }


    protected void switchToParentWindow(){

        try{

            FrameworkLogger.info(getClass(),"attempting to switch to parent window");

            driver.switchTo().window(parentWindow);

            FrameworkLogger.info(getClass(),"successfully switched to parent window");


        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to switch to parent window ",e);
            throw e;
        }
    }

    /*
        Frames handling
     */

    protected void switchToFrame(int index){

        try{

            FrameworkLogger.info(getClass(),"attempting to switch to frame at index: "+index);

            driver.switchTo().frame(index);

            FrameworkLogger.info(getClass(),"successfully switched to frame");


        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to switch to frame ",e);
            throw e;
        }

    }

    protected void switchToFrame(String nameOrId){

        try{

            FrameworkLogger.info(getClass(),"attempting to switch to frame: "+nameOrId);

            driver.switchTo().frame(nameOrId);

            FrameworkLogger.info(getClass(),"successfully switched to frame");


        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to switch to frame ",e);
            throw e;
        }

    }

    protected void switchToFrame(By locator){

        try{

            FrameworkLogger.info(getClass(),"attempting to switch to frame: "+locator);

            WebElement frame = getVisibleElement(locator);

            driver.switchTo().frame(frame);

            FrameworkLogger.info(getClass(),"successfully switched to frame");


        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to switch to frame ",e);
            throw e;
        }

    }

    protected void switchToParentFrame(){

        try{

            FrameworkLogger.info(getClass(),"attempting to switch to parent frame");

            driver.switchTo().parentFrame();

            FrameworkLogger.info(getClass(),"successfully switched to parent frame");


        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to switch to parent frame ",e);
            throw e;
        }

    }

    protected void switchToDefaultContent(){

        try{

            FrameworkLogger.info(getClass(),"attempting to switch to default content");

            driver.switchTo().defaultContent();

            FrameworkLogger.info(getClass(),"successfully switched to default content");


        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to switch to default content ",e);
            throw e;
        }

    }

    /*
        File handling
     */

    protected void uploadFile(By locator, String filePath){

        try{

            FrameworkLogger.info(getClass(),"attempting to upload the file");

            WebElement element = getVisibleElement(locator);

            element.sendKeys(filePath);

            FrameworkLogger.info(getClass(),"successfully uploaded the file");


        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to upload the file ",e);
            throw e;
        }

    }

    protected boolean isFileDownloaded(String fileName){

        try{

            FrameworkLogger.info(getClass(),"checking whether file '"+fileName+"' is downloaded!");

            String downloadPath = System.getProperty("user.home")+File.separator+"Downloads";

            File file = new File(downloadPath,fileName);
            boolean status = file.exists();

            FrameworkLogger.info(getClass(),"file download status for '"+fileName+"': "+status);

            return status;


        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to check downloaded file '"+fileName+"'",e);
            throw e;
        }

    }

    protected void waitForFileDownload(String fileName) {

        try{

            FrameworkLogger.info(getClass(),"waiting for file '"+fileName+"' to be downloaded");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
            wait.until(driver -> isFileDownloaded(fileName));

            FrameworkLogger.info(getClass(),"file '"+fileName+"' downloaded successfully");


        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to download file '"+fileName+"' within the expected time  ",e);
            throw e;
        }

    }


    /*
        Web table handling
     */

    protected int getRowCount(By tableLocator){

        try{

            FrameworkLogger.info(getClass(),"attempting to get row count");

            WebElement table = getVisibleElement(tableLocator);
            List<WebElement> rows = table.findElements(By.tagName("tr"));

            FrameworkLogger.info(getClass(), "row count retrieved successfully");
            return rows.size();


        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to retrieve the row count",e);
            throw e;
        }



    }


    protected int getColumnCount(By tableLocator){

        try{

            FrameworkLogger.info(getClass(),"attempting to get column count");

            WebElement table = getVisibleElement(tableLocator);
            List<WebElement> rows = table.findElements(By.tagName("tr"));

            WebElement firstRow = rows.get(0);
            List<WebElement> columns = firstRow.findElements(By.tagName("td"));


            FrameworkLogger.info(getClass(), "column count retrieved successfully");
            return columns.size();


        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to retrieve the column count",e);
            throw e;
        }



    }


    protected String getCellText(By tableLocator, int row, int column){

        try{

            FrameworkLogger.info(getClass(),"attempting to get cell text");

            WebElement table = getVisibleElement(tableLocator);
            List<WebElement> rows = table.findElements(By.tagName("tr"));

            WebElement selectedRow = rows.get(row-1);
            List<WebElement> columns = selectedRow.findElements(By.tagName("td"));

            WebElement cell = columns.get(column-1);
            String text = cell.getText();

            FrameworkLogger.info(getClass(), "cell text retrieved successfully");
            return text;


        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to retrieve the cell text",e);
            throw e;
        }


    }


    protected List<String> getRowData(By tableLocator, int row){

        try{

            FrameworkLogger.info(getClass(),"attempting to get row data");

            WebElement table = getVisibleElement(tableLocator);
            List<WebElement> rows = table.findElements(By.tagName("tr"));

            WebElement selectedRow = rows.get(row-1);
            List<WebElement> columns = selectedRow.findElements(By.tagName("td"));
            List<String> rowData = new ArrayList<>();
            for(WebElement element : columns){
                rowData.add(element.getText());
            }

            FrameworkLogger.info(getClass(), "row data retrieved successfully");
            return rowData;


        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to retrieve the row data",e);
            throw e;
        }

    }

    protected List<List<String>> getTableData(By tableLocator){

        try{

            FrameworkLogger.info(getClass(),"attempting to get table data");

            WebElement table = getVisibleElement(tableLocator);
            List<WebElement> rows = table.findElements(By.tagName("tr"));

            List<List<String>> tableData = new ArrayList<>();

            for (int i=1; i<= rows.size();i++){
                List<String> rowData = getRowData(tableLocator,i);
                tableData.add(rowData);
            }


            FrameworkLogger.info(getClass(), "table data retrieved successfully");
            return tableData;


        } catch (Exception e) {

            FrameworkLogger.error(getClass(),"failed to retrieve the table data",e);
            throw e;
        }

    }

    /*
       Dynamic locators handling
     */

    protected By createXpath(String template, String value){


            FrameworkLogger.info(getClass(),"creating dynamic xpath");

            By locator = By.xpath(String.format(template,value));

            return locator;

    }


    protected By createXpath(String template, String value1, String value2){


        FrameworkLogger.info(getClass(),"creating dynamic xpath");

        By locator = By.xpath(String.format(template,value1, value2));

        return locator;

    }


    protected By createCssSelector(String template, String value){


        FrameworkLogger.info(getClass(),"creating dynamic css selector");

        By locator = By.cssSelector(String.format(template,value));

        return locator;

    }



}
