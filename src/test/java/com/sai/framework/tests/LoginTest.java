package com.sai.framework.tests;

import com.sai.framework.base.BaseTest;
import com.sai.framework.config.ConfigReader;
import com.sai.framework.pages.LoginPage;
import com.sai.framework.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void verifySuccessfulLogin(){

        LoginPage loginPage = new LoginPage(driver);

        ProductsPage productsPage = loginPage.login(ConfigReader.getUsername(),ConfigReader.getPassword());

        Assert.assertTrue(productsPage.isProductDisplayed("Sauce Labs Backpack"));
    }
}
