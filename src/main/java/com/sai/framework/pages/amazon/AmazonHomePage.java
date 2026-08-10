package com.sai.framework.pages.amazon;

import com.sai.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AmazonHomePage extends BasePage {

    private final By searchBox = By.id("twotabsearchtextbox");
    private final By searchButton = By.id("nav-search-submit-button");

    public AmazonHomePage(WebDriver driver) {

        super(driver);
    }

    public void search(String product){

        type(searchBox,product);
        click(searchButton);
    }


}
