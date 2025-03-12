package com.qa.projectNameLUMA.pages;

import com.qa.projectNameLUMA.utils.ElementUtil;
import com.qa.projectNameLUMA.utils.TimeUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private final WebDriver driver;
    private final ElementUtil eleUtils;  // To handle common element operations.

    /**
     * To initialize the driver required a public Login Page constructor.
     */
    public HomePage(WebDriver driver) {
        this.driver = driver;
        eleUtils = new ElementUtil(driver);
    }

    /******* page locators: By Locator ***********/
    private final By WelcomeLogin = By.xpath("//span[@class='logged-in']");
    private final By searchField = By.xpath("//input[@id='search']");
    private final By searchBtn = By.xpath("//button[@class='action search']");
    private final By SignInHomePageButton = By.xpath("(//a[contains(text(),'Sign In')])[1]");
    private final By createAnAccountButton = By.xpath("//a[@class='action create primary']");



    /**********  public page actions / method **************/
    @Step("goto login page.")
    public LoginPage gotoCustomerLoginPage(){
        eleUtils.isPageLoaded(TimeUtil.DEFAULT_TIME);
        eleUtils.isElementClickable(SignInHomePageButton,TimeUtil.DEFAULT_TIME);
        eleUtils.doClick(SignInHomePageButton, TimeUtil.DEFAULT_TIME);
        eleUtils.isPageLoaded(TimeUtil.DEFAULT_TIME);
        return new LoginPage(driver);
    }

    @Step("Get Login Welcome Message on Home page.")
    public String getCustomerLoginWelcomeMessage(){
        String welcomeMessage = eleUtils.doGetText(WelcomeLogin, TimeUtil.DEFAULT_TIME);
        System.out.println("Customer Login Message on Home page : " + welcomeMessage);
        return welcomeMessage;
    }

    @Step("Check is the Search Field Exist on home page")
    public boolean isSearchExist(){
        return eleUtils.doIsDisplayed(searchField, TimeUtil.DEFAULT_TIME);
    }

    public SearchResultPage doSearch(String searchKey){
        System.out.println("Searching For : " +searchKey);
        if(isSearchExist()){
            eleUtils.doClick(searchField);
            eleUtils.doSendKeys(searchField, searchKey, TimeUtil.DEFAULT_TIME);
            eleUtils.doClick(searchBtn);
            return new SearchResultPage(driver); //Page Chaining Using TDD (Test Driven Development Principle)
        } else {
            System.out.println("Search field is not present on this Page.");
            return null;
        }
    }

    @Step("navigating to register page...")
    public RegistrationPage navigateToRegisterPage() {
        eleUtils.isPageLoaded(TimeUtil.DEFAULT_TIME);
        eleUtils.isElementClickable(SignInHomePageButton,TimeUtil.DEFAULT_TIME);
        eleUtils.doClick(SignInHomePageButton, TimeUtil.DEFAULT_TIME);
        eleUtils.isPageLoaded(TimeUtil.DEFAULT_TIME);
        eleUtils.doClick(createAnAccountButton, TimeUtil.DEFAULT_TIME);
        eleUtils.isPageLoaded(TimeUtil.DEFAULT_TIME);
        return new RegistrationPage(driver); //Page Chaining Using TDD (Test Driven Development Principle)
    }


}
