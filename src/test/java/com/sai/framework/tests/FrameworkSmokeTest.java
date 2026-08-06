package com.sai.framework.tests;

import com.sai.framework.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FrameworkSmokeTest extends BaseTest {

    @Test(enabled = false)
    public void verifyApplicationTitle(){
        String actualTitle = driver.getTitle();

        Assert.assertEquals(actualTitle,"Swag Labs","Application title is incorrect.");
    }

}
