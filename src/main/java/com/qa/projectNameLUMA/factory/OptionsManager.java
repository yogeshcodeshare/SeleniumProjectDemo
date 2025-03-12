package com.qa.projectNameLUMA.factory;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

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
        return eo;
    }
}
