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

@Epic("Epic 500: Design open cart product info page")
@Story("US 501: Product information page features")
@Feature("F52: Feature product info page")
//@Listeners({TestAllureListener.class}) // this Listener will generate the allure report class wise.

public class ProductInfoPageTest extends BaseTest {

    @DataProvider
    public Object[][] getProductData() {
        return new Object[][] {
                {"bag", "Push It Messenger Bag"},
                {"backpack", "Crown Summit Backpack"},
                {"duffle", "Overnight Duffle"}
        };
    }

    @Description("Test Case: Verify the product header. This test ensures that the product header matches the expected product name.")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProvider = "getProductData", priority = 1)
    public void productHeaderTest(String searchKey, String productName) {
        searchResultPage = homePage.doSearch(searchKey);
        productInfoPage = searchResultPage.selectProduct(productName);
        Assert.assertEquals(productInfoPage.getProductHeader(), productName, AppError.HEADER_NOT_FOUND);
    }

    @DataProvider
    public Object[][] getProductImageSheetData() {
        return new Object[][]{
                {"bag", "Push It Messenger Bag", 1},
                {"duffle", "Overnight Duffle", 1},
                {"Watch", "Dash Digital Watch", 1}
        };
    }
    @Description("Test Case: Verify the number of product images. This test checks that the number of images for each product matches the expected count using data from a sheet.")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProvider = "getProductImageSheetData", priority = 2)
    public void productImagesCountTest(String searchKey, String productName, int imagesCount) {
        searchResultPage = homePage.doSearch(searchKey);
        productInfoPage = searchResultPage.selectProduct(productName);
        Assert.assertEquals(productInfoPage.getProductImagesCount(), imagesCount, AppError.IMAGES_COUNT_MISMATCHED);
    }
}
