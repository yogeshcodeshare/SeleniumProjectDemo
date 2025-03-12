package com.qa.projectNameLUMA.tests;

import com.qa.projectNameLUMA.base.BaseTest;
import com.qa.projectNameLUMA.constants.AppConstant;
import com.qa.projectNameLUMA.errors.AppError;
import com.qa.projectNameLUMA.listeners.TestAllureListener;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Epic("Epic 200: Design LUMA Application with Login Workflow")
@Story("US 201: Design login page for LUMA Application")
@Feature("F20: Feature login page")
//@Listeners({TestAllureListener.class}) // this Listener will generate the allure report class wise.

public class LoginTest extends BaseTest {

    @BeforeClass
    public void loginPageSetUp(){
        loginPage = homePage.gotoCustomerLoginPage();
    }

    @Description("checking login page title test")
    @Severity(SeverityLevel.MINOR)
    @Owner("QA Yogesh Mohite")
    @Test (priority = 1)
    public void customerLoginPageTitleTest(){
        String actTitle = loginPage.getCustomerLoginPageTitle();
        Assert.assertEquals(actTitle, AppConstant.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
    }

    @Description("checking login page url ----")
    @Severity(SeverityLevel.NORMAL)
    @Owner("QA Yogesh Mohite")
    @Test (priority = 2)
    public void customerLoginPageURLTest(){
        String actURL = loginPage.getLoginPageURL();
        Assert.assertTrue(actURL.contains("/customer/account/login/"), AppError.URL_NOT_FOUND);
    }

    @Description("checking forgot password link exist on the login page ----")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("QA Yogesh Mohite")
    @Test(priority = 3)
    public void forgotPwdLinkExistTest() {
        Assert.assertTrue(loginPage.checkForgotPwdLinkExist(), AppError.ELEMENT_NOT_FOUND);
    }


//    @Description("checking user is able to login to app successfully ----")
//    @Severity(SeverityLevel.BLOCKER)
//    @Owner("QA Yogesh Mohite")
//    @Test(priority = 4, enabled = false)
//    public void loginTest() {
//        homePage = loginPage.doLogin(prop.getProperty("LoginEmailID"), prop.getProperty("Password"));
//        Assert.assertTrue(homePage.getCustomerLoginWelcomeMessage().contains("Welcome"), AppError.CUSTOMER_NOT_LOGIN);
//    }

}
