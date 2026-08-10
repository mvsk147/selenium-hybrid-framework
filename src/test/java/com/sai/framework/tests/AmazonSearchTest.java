package com.sai.framework.tests;

import com.sai.framework.base.BaseTest;
import com.sai.framework.pages.amazon.AmazonHomePage;
import com.sai.framework.pages.amazon.AmazonSearchResults;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AmazonSearchTest extends BaseTest {

    @Test
    public void verifyAmazonSearch(){
        AmazonHomePage homePage = new AmazonHomePage(driver);

        AmazonSearchResults resultsPage = new AmazonSearchResults(driver);
        homePage.search("hp laptops");
        resultsPage.clickProduct("OmniBook");
        Assert.assertTrue(resultsPage.isSearchResultsDisplayed());
    }
}
