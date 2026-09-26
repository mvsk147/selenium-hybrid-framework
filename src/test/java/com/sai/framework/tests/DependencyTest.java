package com.sai.framework.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DependencyTest {

    @DataProvider(name = "users")
    public Object[][] users(){
        return new Object[][]{
                {"user1"},
                {"user2"},
                {"user3"}
        };
    }

    @Test
    public void loginTest(){
        System.out.println("login test");
        Assert.assertTrue(false);
    }

    @Test(dataProvider = "users", dependsOnMethods = "loginTest")
    public void productTest(String username){
        System.out.println("product: "+username);
    }

    @Test(dependsOnMethods = "loginTest")
    public void checkoutTest(){
        System.out.println("check out test");
    }



}
