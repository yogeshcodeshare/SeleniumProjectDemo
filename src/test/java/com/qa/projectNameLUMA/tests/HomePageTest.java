package com.qa.projectNameLUMA.tests;

import com.qa.projectNameLUMA.base.BaseTest;
import com.qa.projectNameLUMA.errors.AppError;
import com.qa.projectNameLUMA.listeners.TestAllureListener;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Epic("Epic 100: Design LUMA Application Home page")
@Story("US 101: Home page features")
@Feature("F11: Feature Home page")
//@Listeners({TestAllureListener.class}) // this Listener will generate the allure report class wise.

public class HomePageTest extends BaseTest {

    @DataProvider
    public Object [][] getSearchData(){
        return new Object[][]{
                {"bag", 8},
                {"backpack", 6},
                {"duffle", 2}
        };
    }
    @Description("Search product with searchKey")
    @Severity(SeverityLevel.MINOR)
    @Owner("QA Yogesh Mohite")
    @Test(dataProvider = "getSearchData")
    public void searchTest(String searchKey, int resultCount) {
        searchResultPage = homePage.doSearch(searchKey);
        Assert.assertEquals(searchResultPage.getSearchResultCount(), resultCount, AppError.RESULT_COUNT_MISMATCH);
    }



}
