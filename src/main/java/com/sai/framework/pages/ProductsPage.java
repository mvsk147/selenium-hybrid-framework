package com.sai.framework.pages;

import com.sai.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage extends BasePage {

    private final By lblProductsTitle = By.xpath("//span[text()='Products']");
    private static final String PRODUCT_NAME = "//div[@class='inventory_item_name ' and text()='%s']";

    public ProductsPage(WebDriver driver){
        super(driver);
    }

    public String getPageTitle(){
        return getText(lblProductsTitle);
    }

    public boolean isProductDisplayed(String productName){
        By productLocator = createXpath(PRODUCT_NAME, productName);
        return isDisplayed(productLocator);
    }
}
