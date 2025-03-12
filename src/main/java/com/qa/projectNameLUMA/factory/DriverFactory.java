package com.qa.projectNameLUMA.factory;

import com.qa.projectNameLUMA.constants.AppConstant;
import com.qa.projectNameLUMA.errors.AppError;
import com.qa.projectNameLUMA.exceptions.BrowserException;
import com.qa.projectNameLUMA.exceptions.FrameworkException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class DriverFactory {

    WebDriver driver;
    Properties prop;
    OptionsManager optionsManager;//object reference variable.

    public static String highlight;// create a highlight variable.

    //Create a ThreadLocal driver object.
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();


    /**
     * This is an initialization method for the browser driver.
     * @param prop = prop will load and fetch the different properties from conf. file.
     */
    public WebDriver intiDriver(Properties prop){

        String browserName = prop.getProperty("browser");

        System.out.println("Browser name is " +browserName);
        highlight= prop.getProperty("highlight");

        optionsManager = new OptionsManager(prop);

        switch (browserName.toLowerCase().trim()){
            case "chrome":
                //driver = new ChromeDriver();
                //initialization of Local copy of driver
                tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));//option manager will give option for headless/incognito mode
                break;
            case "firefox":
                //driver = new FirefoxDriver();
                //initialization of Local copy of driver
                tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));//option manager will give option for headless/incognito mode
                break;
            case "edge":
                //driver = new EdgeDriver();
                //initialization of Local copy of driver
                tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));//option manager will give option for headless/InPrivate mode
                break;
            case "safari":
                //driver = new SafariDriver();
                tlDriver.set(new SafariDriver());//initialization of Local copy of driver
                break;
            default:
                System.out.println("Invalid browser name. Please provide valid browser name");
                throw new BrowserException(AppError.BROWSER_NOT_FOUND);
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        getDriver().get(prop.getProperty("URL"));

        return getDriver();
    }

    //Now set the local copy of driver is Done.then we go for get driver whenever required.

    /**
     * Get the local thread copy of the driver
     * @return local driver is return
     */
    public static WebDriver getDriver(){
        return tlDriver.get();
    }


    /**
     * This method is used the properties from the properties file.
     * it is load the different properties from conf prop file
     * @return : this return properties (prop)
     */
    public Properties initProp(){
        prop = new Properties();
        FileInputStream ip = null;
        //MultiEnvironment SetUp
        //EnvName is given from CMD using command:
        // mvn clean install -Denv="qa"
        String envName = System.getProperty("env");
        //if anyone pass no any envName
        if (envName == null){
            System.out.println("envName is not given, Hence running it on QA environment");
            try {
                ip = new FileInputStream(AppConstant.CONFIG_QA_FILE_PATH);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                switch (envName.trim().toLowerCase()) {
                    case "qa":
                        ip = new FileInputStream(AppConstant.CONFIG_QA_FILE_PATH);
                        break;
                    case "stage":
                        ip = new FileInputStream(AppConstant.CONFIG_STAGE_FILE_PATH);
                        break;
                    case "dev":
                        ip = new FileInputStream(AppConstant.CONFIG_DEV_FILE_PATH);
                        break;
                    case "uat":
                        ip = new FileInputStream(AppConstant.CONFIG_UAT_FILE_PATH);
                        break;
                    case "prod":
                        ip = new FileInputStream(AppConstant.CONFIG_FILE_PATH);
                        break;
                    default:
                        System.out.println("Please pass right env name :" + envName);
                        throw new FrameworkException("WRONG ENV PASS");
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            prop.load(ip);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return prop;
    }

    /**
     * take a screenshot
     */
    public static String getScreenshot(String methodName){
        File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir")+"/screenshots"+methodName+"_"+System.currentTimeMillis()+".png";
        File destination = new File(path);
        try {
            FileHandler.copy(srcFile, destination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }

}
