package com.sai.framework.tests;

import com.sai.framework.base.BaseTest;
import com.sai.framework.config.ConfigReader;
import com.sai.framework.dataproviders.TestDataProvider;
import com.sai.framework.pages.LoginPage;
import com.sai.framework.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "loginData", dataProviderClass = TestDataProvider.class)
    public void verifySuccessfulLogin(String username, String password){

        LoginPage loginPage = new LoginPage(driver);

//        ProductsPage productsPage = loginPage.login(ConfigReader.getUsername(),ConfigReader.getPassword());

        ProductsPage productsPage = loginPage.login(username,password);

        Assert.assertTrue(productsPage.isProductDisplayed("Sauce Labs Backpack"));
    }
}
