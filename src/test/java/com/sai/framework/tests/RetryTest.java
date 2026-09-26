package com.sai.framework.tests;

import com.sai.framework.listeners.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RetryTest {

    private int attempt = 0;

    @DataProvider(name = "retryData", parallel = true)
    public Object[][] retryData() {
        return new Object[][] {
                {"Dataset 1"},
                {"Dataset 2"},
                {"Dataset 3"}
        };
    }

    @Test(dataProvider = "retryData", retryAnalyzer = RetryAnalyzer.class)
    public void retryDemo(String dataset){

        System.out.println(
                dataset + " | Thread: "
                        + Thread.currentThread().threadId()
        );

        Assert.fail("Intentional failure");

    }

}
