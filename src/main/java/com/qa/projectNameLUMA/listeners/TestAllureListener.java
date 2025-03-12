package com.qa.projectNameLUMA.listeners;

import com.qa.projectNameLUMA.factory.DriverFactory;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.File;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestAllureListener implements ITestListener {


    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }


    // Text attachments for Allure
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotPNG(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    // Text attachments for Allure
    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    // HTML attachments for Allure
    @Attachment(value = "{0}", type = "text/html")
    public static String attachHtml(String html) {
        return html;
    }

    @Override
    public void onStart(ITestContext iTestContext) {
//        System.out.println("I am in on Start method " + iTestContext.getName());
        System.out.println("************ Test Suite execution started: " + iTestContext.getName() +" ************");
        //iTestContext.setAttribute("WebDriver", BasePage.getDriver());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
//        System.out.println("I am in on Finish method " + iTestContext.getName());
        System.out.println("************ Test Suite execution finished: " + iTestContext.getName()+" ************");
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
//        System.out.println("I am in onTestStart method " + getTestMethodName(iTestResult) + " start");
        System.out.println("!!!!!!!!!!!! Test started: " + getTestMethodName(iTestResult) + " start  !!!!!!!!!!!!");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
//        System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
        System.out.println("!!!!!!!!!!!! Test passed successfully: " + getTestMethodName(iTestResult) + " succeed !!!!!!!!!!!!");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
//        System.out.println("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");
        System.out.println("############ Test failed: " + getTestMethodName(iTestResult) + " failed ############");
        Object testClass = iTestResult.getInstance();
        //WebDriver driver = BasePage.getDriver();
        // Allure ScreenShotRobot and SaveTestLog
        if (DriverFactory.getDriver() instanceof WebDriver) {
            System.out.println("Screenshot captured for test case:" + getTestMethodName(iTestResult));
            saveScreenshotPNG(DriverFactory.getDriver());
        }
        // Save a log on allure.
        saveTextLog(getTestMethodName(iTestResult) + " failed and screenshot taken!");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
//        System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
        System.out.println("@@@@@@@@@@@@ Test was skipped: " + getTestMethodName(iTestResult) + " skipped @@@@@@@@@@@@");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }

}

