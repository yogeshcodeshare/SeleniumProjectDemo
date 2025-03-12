package com.qa.projectNameLUMA.pages;

import com.qa.projectNameLUMA.constants.AppConstant;
import com.qa.projectNameLUMA.utils.ElementUtil;
import com.qa.projectNameLUMA.utils.TimeUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class LoginPage {
    private final WebDriver driver;
    private final ElementUtil eleUtils;  // To handle common element operations.

    /**
     * To initialize the driver required a public Login Page constructor.
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        eleUtils = new ElementUtil(driver);
    }

    /******* page locators: By Locator ***********/
    private final By forgotPwdLink = By.linkText("Forgot Your Password?");
    private final By SignInBtn = By.xpath("(//span[contains(text(),'Sign In')])[2]");
    private final By email = By.name("login[username]");
    private final By password = By.name("login[password]");


    /**********  public page actions / method **************/
    @Step("getting login page title value...")
    public String getCustomerLoginPageTitle(){
        eleUtils.isPageLoaded(TimeUtil.DEFAULT_TIME);
        String title = eleUtils.waitForTitleToBe(AppConstant.LOGIN_PAGE_TITLE, TimeUtil.DEFAULT_TIME);
        System.out.println("login Page Title : " +title);
        return title;
    }

    @Step("getting login page URL...")
    public String getLoginPageURL(){
        eleUtils.isPageLoaded(TimeUtil.DEFAULT_TIME);
        String URL = eleUtils.waitForURLContains(AppConstant.LOGIN_PAGE_FRACTION_URL, TimeUtil.DEFAULT_TIME);
        System.out.println("Login Page URL : " +URL);
        return URL;
    }

    @Step("getting the state of forgot pwd link exist...")
    public boolean checkForgotPwdLinkExist() {
        return eleUtils.doIsDisplayed(forgotPwdLink, TimeUtil.DEFAULT_TIME);
    }

    @Step("login to application with username: {0} and password: {1}")
    public HomePage doLogin(String signInEmailID, String pwd) {
        System.out.println("user creds : " + signInEmailID + ":" + pwd);
        eleUtils.doSendKeys(email, signInEmailID, TimeUtil.DEFAULT_MEDIUM_TIME);
        eleUtils.doSendKeys(password, pwd);
        eleUtils.doClick(SignInBtn);
        eleUtils.isPageLoaded(TimeUtil.DEFAULT_TIME);
        return new HomePage(driver);//Page Chaining Using TDD (Test Driven Development Principle)
    }

}
