package com.sai.framework.pages;

import com.sai.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage extends BasePage {

    private final By lblProductsTitle = By.xpath("//span[text()='Products']");

    public ProductsPage(WebDriver driver){
        super(driver);
    }

    public String getPageTitle(){
        return getText(lblProductsTitle);
    }
}
