package com.qa.projectNameLUMA.pages;

import com.qa.projectNameLUMA.constants.AppConstant;
import com.qa.projectNameLUMA.utils.ElementUtil;
import com.qa.projectNameLUMA.utils.TimeUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage {
    private final WebDriver driver;
    private final ElementUtil eleUtils;  // To handle common element operations.

    /**
     * To initialize the driver required a public Registration Page constructor.
     */
    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        eleUtils = new ElementUtil(driver);
    }

    /******* page locators: By Locator ***********/

    private final By firstName = By.id("firstname");
    private final By lastName = By.id("lastname");
    private final By email = By.id("email_address");
    private final By password = By.id("password");
    private final By confirmPassword = By.id("password-confirmation");
    private final By createAccountButton = By.xpath("//button[@class='action submit primary']");
    private final By createAccountSuccessMessage = By.xpath("//div[contains(text(),'Thank you for registering with Main Website Store.')]");
    private final By contactInfo = By.xpath("(//div[@class='box-content']//p)[1]");
    private final By LumaLogo = By.xpath("//a[@class='logo']");
    /**********  public page actions / method **************/

    @Step("Getting Registration page title value...")
    public String getRegistrationPageTitle(){
        String title = eleUtils.waitForTitleToBe(AppConstant.REGISTRATION_PAGE_TITLE, TimeUtil.DEFAULT_TIME);
        System.out.println("Registration Page Title : " +title);
        return title;
    }

    @Step("Create a New Customer Account")
    public MyAccountPage doCreateNewCustomerAccount(String firstName,String lastName, String email, String password){
        eleUtils.doSendKeys(this.firstName,firstName, TimeUtil.DEFAULT_TIME );// If locator name and value name is same.
        eleUtils.doSendKeys(this.lastName, lastName,TimeUtil.DEFAULT_TIME);//then use "this." for differentiate the locator.
        eleUtils.doSendKeys(this.email,email,TimeUtil.DEFAULT_TIME );
        eleUtils.doSendKeys(this.password, password, TimeUtil.DEFAULT_TIME );
        eleUtils.doSendKeys(confirmPassword, password, TimeUtil.DEFAULT_TIME);
        eleUtils.isElementClickable(createAccountButton, TimeUtil.DEFAULT_TIME);
        eleUtils.doClick(createAccountButton, TimeUtil.DEFAULT_TIME);

        String successMessage = eleUtils.waitForElementPresence(createAccountSuccessMessage, TimeUtil.DEFAULT_TIME).getText();
        System.out.println(successMessage);

        String contactInformation = eleUtils.waitForElementPresence(contactInfo, TimeUtil.DEFAULT_TIME).getText();
        System.out.println("Account Contact Information :" +contactInformation);

        return new MyAccountPage(driver);//Page Chaining Using TDD (Test Driven Development Principle)
    }


    @Step("get New Customer Account Create Message")
    public String getNewCustomerAccountCreateMessage(){
        eleUtils.isPageLoaded(TimeUtil.DEFAULT_TIME);
        return eleUtils.doGetText(createAccountSuccessMessage, TimeUtil.DEFAULT_TIME);
    }

    @Step("create Customer And Goto Home Page")
    public void createCustomerAndGotoHomePage(String firstNameValue,String lastNameValue, String emailID, String passwordValue){
        doCreateNewCustomerAccount(firstNameValue,lastNameValue,emailID,passwordValue);
        eleUtils.doClick(LumaLogo);
        eleUtils.isPageLoaded(TimeUtil.DEFAULT_TIME);
    }








}
