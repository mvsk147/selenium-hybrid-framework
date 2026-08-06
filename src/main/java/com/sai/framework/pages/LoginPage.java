package com.sai.framework.pages;

import com.sai.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By txtUsername = By.id("user-name");
    private final By txtPassword = By.id("password");
    private final By btnLogin = By.id("login-button");


    public LoginPage(WebDriver driver){

        super(driver);

    }


    public LoginPage enterUsername(String username){
        clearAndType(txtUsername,username);
        return this;
   }

   public LoginPage enterPassword(String password){
       clearAndType(txtPassword,password);
       return this;
   }

   public ProductsPage clickLogin(){
       click(btnLogin);
       return new ProductsPage(driver);
   }

   public ProductsPage login(String username, String password){

        return enterUsername(username).enterPassword(password).clickLogin();
   }


}
