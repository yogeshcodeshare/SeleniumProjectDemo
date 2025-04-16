package com.qa.projectNameLUMA.factory;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class OptionsManager {

    private final Properties prop;

    public OptionsManager(Properties prop) {
        this.prop = prop;
    }


    public ChromeOptions getChromeOptions() {
        ChromeOptions co = new ChromeOptions();

        if (Boolean.parseBoolean(prop.getProperty("headless"))) {
            System.out.println("=====Running tests in headless======");
            co.addArguments("--headless");  // Recommended for Chrome 109+

        }
        if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
            System.out.println("=====Running tests in incognito======");
            co.addArguments("--incognito");
        }
        if(Boolean.parseBoolean(prop.getProperty("remote"))){
            // Implement the logic to run test cases on the Remote Machine/grid
            co.setCapability("browserName", "chrome");
            co.setBrowserVersion(prop.getProperty("browserVersion").trim());

            Map<String, Object> selenoidOptions = new HashMap<>();
            selenoidOptions.put("screenResolution", "1280x1024x24");
            //This is a specific capability for enabling VNC support on the remote browser session.
            // VNC allows you to see the browser's graphical interface remotely while your tests are running.
            selenoidOptions.put("enableVNC", true);
            selenoidOptions.put("name", prop.getProperty("testName"));
            co.setCapability("selenoid:options", selenoidOptions);
        }

        return co;
    }

    public FirefoxOptions getFirefoxOptions() {
        FirefoxOptions fo = new FirefoxOptions();

        if (Boolean.parseBoolean(prop.getProperty("headless"))) {
            System.out.println("====Running tests in headless======");
            fo.addArguments("--headless");
        }
        if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
            System.out.println("=====Running tests in incognito======");
            fo.addArguments("--incognito");
        }
        if(Boolean.parseBoolean(prop.getProperty("remote"))){
            // Implement the logic to run test cases on the Remote Machine/grid
            fo.setCapability("browserName", "firefox");
            fo.setBrowserVersion(prop.getProperty("browserVersion").trim());

            Map<String, Object> selenoidOptions = new HashMap<>();
            selenoidOptions.put("screenResolution", "1280x1024x24");
            //This is a specific capability for enabling VNC support on the remote browser session.
            // VNC allows you to see the browser's graphical interface remotely while your tests are running.
            selenoidOptions.put("enableVNC", true);
            selenoidOptions.put("name", prop.getProperty("testName"));
            fo.setCapability("selenoid:options", selenoidOptions);
        }

        return fo;
    }

    public EdgeOptions getEdgeOptions() {
        EdgeOptions eo = new EdgeOptions();

        if (Boolean.parseBoolean(prop.getProperty("headless"))) {
            System.out.println("====Running tests in headless======");
            eo.addArguments("--headless");
        }
        if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
            System.out.println("=====Running tests in incognito======");
            eo.addArguments("--inprivate");
        }
        if(Boolean.parseBoolean(prop.getProperty("remote"))){
            eo.setCapability("browserName", "edge");
//        eo.setCapability("enableVNC", true);
        }
        return eo;
    }
}
