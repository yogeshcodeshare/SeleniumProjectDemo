package com.qa.projectNameLUMA.tests;

import com.qa.projectNameLUMA.base.BaseTest;
import com.qa.projectNameLUMA.constants.AppConstant;
import com.qa.projectNameLUMA.errors.AppError;
import com.qa.projectNameLUMA.listeners.TestAllureListener;
import com.qa.projectNameLUMA.utils.StringUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Epic 400: Design LUMA Application My Account page")
@Story("US 401: Account page features")
@Feature("F42: Feature Account page")

//@Listeners({TestAllureListener.class}) // this Listener will generate the allure report class wise.
public class AccountPageTest extends BaseTest {

    @BeforeClass
    @Description("Setup method to navigate to the My Account page.")
    @Owner("QA Yogesh Mohite")
    public void accPageSetup() {
        RegPage = homePage.navigateToRegisterPage();
    }

    @Description("checking Account page title test")
    @Severity(SeverityLevel.MINOR)
    @Owner("QA Yogesh Mohite")
    @Test(priority = 1)
    public void accPageTitleTest(){
        accPage = RegPage.doCreateNewCustomerAccount(StringUtil.getRandomName(),StringUtil.getRandomName(),StringUtil.getRandomEmailID(),"Test@123");
        Assert.assertEquals(accPage.getAccPageTitle(), AppConstant.ACCOUNTS_PAGE_TITLE, AppError.MY_ACCOUNT_PAGE_NOT_FOUND);
    }

    @Description("checking Account page url ----")
    @Severity(SeverityLevel.NORMAL)
    @Owner("QA Yogesh Mohite")
    @Test(priority = 2)
    public void accPageURLTest(){
        Assert.assertTrue(accPage.getAccPageURL().contains(AppConstant.Account_PAGE_FRACTION_URL), AppError.URL_NOT_FOUND);
    }

    @Description("checking Account Page header exist on the Account page ----")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("QA Yogesh Mohite")
    @Test(priority = 3)
    public void accPageHeaderTest(){
        List<String> accPageHeadersList = accPage.getAccountNavigationHeaders();
        Assert.assertEquals(accPageHeadersList, AppConstant.ACCOUNT_PAGE_HEADER_LIST, AppError.LIST_IS_NOT_MATCH);
    }


}
