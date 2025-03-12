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

@Epic("Epic 300: Design LUMA Application registration page")
@Story("US 301: Registration page features")
@Feature("F33: Feature registration page")
//@Listeners({TestAllureListener.class}) // this Listener will generate the allure report class wise.

public class RegistrationTest extends BaseTest {


    @BeforeClass
    @Description("Setup method to navigate to the registration page.")
    @Owner("QA Yogesh Mohite")
    public void regSetup() {
        RegPage = homePage.navigateToRegisterPage();
    }


    @Description("checking Registration page title test")
    @Severity(SeverityLevel.MINOR)
    @Owner("QA Yogesh Mohite")
    @Test(priority = 1)
    public void newRegistrationFormTitleTest(){
        String actTitle = RegPage.getRegistrationPageTitle();
        Assert.assertEquals(actTitle, AppConstant.REGISTRATION_PAGE_TITLE, AppError.REGISTRATION_PAGE_NOT_FOUND);
    }

    @Description("Test Case: Verify user registration. This test checks if a user can register successfully using data from the provided data sheet.")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("QA Yogesh Mohite")
    @Test(priority = 2)
    public void createNewCustomerAccountTest(){
        RegPage.doCreateNewCustomerAccount(StringUtil.getRandomName(),StringUtil.getRandomName(),StringUtil.getRandomEmailID(),"Test@123");
        String actAccountCreateMessage = RegPage.getNewCustomerAccountCreateMessage();
        Assert.assertTrue(actAccountCreateMessage.contains(AppConstant.ACCOUNT_CREATE_SUCCESS_MESSAGE), AppError.MY_ACCOUNT_PAGE_NOT_FOUND );
    }

}
