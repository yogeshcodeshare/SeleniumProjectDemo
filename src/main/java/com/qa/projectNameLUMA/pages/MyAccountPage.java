package com.qa.projectNameLUMA.pages;

import com.qa.projectNameLUMA.constants.AppConstant;
import com.qa.projectNameLUMA.utils.ElementUtil;
import com.qa.projectNameLUMA.utils.TimeUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class MyAccountPage {

    private final WebDriver driver;
    private final ElementUtil eleUtils;  // To handle common element operations.

    /**
     * To initialize the driver required a public Login Page constructor.
     */
    public MyAccountPage(WebDriver driver) {
        this.driver = driver;
        eleUtils = new ElementUtil(driver);
    }

    /******* page locators: By Locator ***********/
    private final By MyAccountNavList = By.xpath("//ul[@class='nav items']/li");
    private final By MyAccountBtn = By.xpath("(//button[@class='action switch'])[1]");
    private final By SignOut = By.xpath("(//a[text()='Sign Out'])[1]");

    @Step("getting Account page title value...")
    public String getAccPageTitle() {
        String title = eleUtils.waitForTitleToBe(AppConstant.ACCOUNTS_PAGE_TITLE, TimeUtil.DEFAULT_TIME);
        System.out.println("Acc page title : " + title);
        return title;
    }

    @Step("getting Account page URL...")
    public String getAccPageURL(){
        eleUtils.isPageLoaded(TimeUtil.DEFAULT_TIME);
        String URL = eleUtils.waitForURLContains(AppConstant.Account_PAGE_FRACTION_URL, TimeUtil.DEFAULT_TIME);
        System.out.println("Account Page URL : " +URL);
        return URL;
    }

    @Step("Check Sign Out Link is exist or not on Account page.")
    public boolean isSignOutLinkExist(){
        eleUtils.doClick(MyAccountBtn, TimeUtil.DEFAULT_TIME);
        return eleUtils.doIsDisplayed(SignOut,TimeUtil.DEFAULT_TIME);
    }

    @Step("Get Account Header List.")
    public List<String> getAccountNavigationHeaders(){
        List<String> headersList = eleUtils.getElementsTextList(MyAccountNavList);
        return headersList;
    }
}
