package com.qa.projectNameLUMA.pages;

import com.qa.projectNameLUMA.utils.ElementUtil;
import com.qa.projectNameLUMA.utils.TimeUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class SearchResultPage {
    private final WebDriver driver;
    private final ElementUtil eleUtils;  // To handle common element operations.

    /**
     * To initialize the driver required a public Login Page constructor.
     */
    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
        eleUtils = new ElementUtil(driver);
    }
    /******* page locators: By Locator ***********/
    private final By searchResult = By.xpath("//li[@class='item product product-item']");



    /**********  public page actions / method **************/

    @Step("get Search Result Count")
    public int getSearchResultCount(){
        List<String> resultsList = eleUtils.getElementsTextList(searchResult);
        int resultCount = resultsList.size();
        System.out.println("product search count = " +resultCount);
        return resultCount;
    }

    @Step("Select the Product")
    public ProductInfoPage selectProduct(String ProductName){
        eleUtils.doClick(By.linkText(ProductName), TimeUtil.DEFAULT_TIME);
        return new ProductInfoPage(driver);
    }
}
