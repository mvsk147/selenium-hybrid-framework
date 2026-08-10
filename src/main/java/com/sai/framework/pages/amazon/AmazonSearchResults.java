package com.sai.framework.pages.amazon;

import com.sai.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AmazonSearchResults extends BasePage {

    private final By resultsContainer = By.xpath("//div/h2[text()='Results']");
    private static final String PRODUCT = "//div[@data-component-type='s-search-result']//span[contains(text(),'%s')]";

    public AmazonSearchResults(WebDriver driver) {
        super(driver);
    }

    public boolean isSearchResultsDisplayed(){
        return isDisplayed(resultsContainer);
    }

    public void clickProduct(String productName){
        By productLocator = createXpath(PRODUCT, productName);
        click(productLocator);
    }



}
