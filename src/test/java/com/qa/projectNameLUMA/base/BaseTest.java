package com.qa.projectNameLUMA.base;

import com.qa.projectNameLUMA.factory.DriverFactory;
import com.qa.projectNameLUMA.listeners.TestAllureListener;
import com.qa.projectNameLUMA.pages.*;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.Properties;

public class BaseTest {
    DriverFactory df; //variable
    WebDriver driver;
    protected Properties prop;
    protected RegistrationPage RegPage;
    protected LoginPage loginPage;
    protected MyAccountPage accPage;
    protected HomePage homePage;
    protected SearchResultPage searchResultPage;
    protected ProductInfoPage productInfoPage;

    @Step("SetUp Method to Initialize the Browser : {0}")
    @Parameters({"browser", "browserVersion", "testName"})
    @BeforeTest
    public void setUp(@Optional String browserName,@Optional String browserVersion,@Optional String testName){
        df = new DriverFactory(); //object creation
        prop = df.initProp();
        // Now Parameter browser is directly given in browserName but due to config browser conflict arrives.
        // So we need to update the browser.
        if(browserName!= null){
            // this will update the config browser with the Runner.xml file browser parameter
            prop.setProperty("browser",browserName);
            prop.setProperty("browserVersion", browserVersion);
            prop.setProperty("testName", testName);
        }
        driver = df.intiDriver(prop); //call by value from prop object.
        homePage = new HomePage(driver);
    }

    @Step("Close Browser")
    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
