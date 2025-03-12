package com.qa.projectNameLUMA.pages;

import com.qa.projectNameLUMA.utils.ElementUtil;
import com.qa.projectNameLUMA.utils.TimeUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductInfoPage {
    private final WebDriver driver;
    private final ElementUtil eleUtils;  // To handle common element operations.

    /**
     * To initialize the driver required a public Login Page constructor.
     */
    public ProductInfoPage(WebDriver driver) {
        this.driver = driver;
        eleUtils = new ElementUtil(driver);
    }
    /******* page locators: By Locator ***********/
    private final By productHeaderName = By.xpath("//h1[@class='page-title']/span");
    private final By productImages = By.xpath("//img[@class='fotorama__img']");


    /**********  public page actions / method **************/

    @Step("Capture Product Header name.")
    public String getProductHeader(){
        String productName = eleUtils.doGetText(productHeaderName, TimeUtil.DEFAULT_TIME);
        System.out.println("Product name is " +productName);
        return productName;
    }

    @Step("Capture Product Image Count.")
    public int getProductImagesCount() {
        String productName = eleUtils.doGetText(productHeaderName, TimeUtil.DEFAULT_TIME);
        int imagesCount =
                eleUtils.waitForVisibilityOfElementsLocated(productImages, TimeUtil.DEFAULT_MEDIUM_TIME).size();
        System.out.println(productName+" total images == " + imagesCount);
        return imagesCount;
    }

}
