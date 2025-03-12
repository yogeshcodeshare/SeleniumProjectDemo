package com.qa.projectNameLUMA.utils;

import com.qa.projectNameLUMA.exceptions.ElementException;
import com.qa.projectNameLUMA.factory.DriverFactory;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ElementUtil {

    private WebDriver driver;
    private JavaScriptUtil jsUtil;
    /*============== public constructor of the ElementUtil class ==============*/
    /**
     * Constructor to initialize the WebDriver instance.
     * @param driver the WebDriver instance to interact with the browser
     */
    public ElementUtil(WebDriver driver) {
        this.driver = driver;
        jsUtil = new JavaScriptUtil(driver);
    }


    /* ========================  WaitUtil  ============================*/

    /**
     * This method is used to Waits for an element to be present in the DOM, but it doesn't have to be visible.
     *
     * @param locator : the locator to be present in the DOM
     * @param timeOut : Maximum time to wait for the element to be present.
     * @return : WebElement
     */
    public WebElement waitForElementPresence(By locator, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            highlightElement(element);
            return element;

        } catch (Exception e) {
            throw new ElementException("Failed to find element by locator: " + locator, e);
        }
    }

    /**
     * This method is used to Waits for an element to be visible, meaning it is displayed and has non-zero height and width.
     *
     * @param locator : Provide a element locator.
     * @param timeOut : Provide Maximum time to wait for the element to be visible.
     * @return : WebElement
     */
    public WebElement waitForElementVisible(By locator, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            highlightElement(element);
            return element;
        } catch (TimeoutException e) {
            //System.out.println("Element not visible: " + locator);
            return null;
        } catch (Exception e) {
            throw new ElementException("Failed to find visible element by locator: " + locator, e);
        }
    }

    /**
     * This method is used to Waits for an element to be visible, with custom polling intervals.
     *
     * @param locator      : Provide a element locator.
     * @param timeOut      : Provide Maximum time to wait for the element to be visible.
     * @param intervalTime : Provide the Polling interval time.
     * @return : WebElement
     */
    public WebElement waitForElementVisible(By locator, int timeOut, int intervalTime) {
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(timeOut))
                    .pollingEvery(Duration.ofSeconds(intervalTime))
                    .ignoring(NoSuchElementException.class)
                    .withMessage("===element is not found===");
            WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            highlightElement(element);
            return element;
        } catch (Exception e) {
            throw new ElementException("Failed to find visible element by locator with interval: " + locator, e);
        }
    }

    /**
     * This method is used to Waits for at least one element to be present on the page.
     *
     * @param locator : Provide the locator contains List of elements.
     * @param timeOut : Provide Maximum time to wait for the element to be visible.
     * @return : `List<WebElement>` - List of located elements.
     */
    public List<WebElement> waitForPresenceOfElementsLocated(By locator, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch (Exception e) {
            throw new ElementException("Failed to find elements by locator: " + locator, e);
        }
    }

    /**
     * This method is used to Waits for all elements to be visible on the page.
     *
     * @param locator : Provide the locator contains List of elements.
     * @param timeOut : Provide Maximum time to wait for the element to be visible.
     * @return : `List<WebElement>` - List of located elements.
     */
    public List<WebElement> waitForVisibilityOfElementsLocated(By locator, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        } catch (Exception e) {
            throw new ElementException("Failed to find visible elements by locator: " + locator, e);
        }
    }

    public boolean isElementClickable(By locator, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            return element != null && element.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    // Method to wait for an element to be clickable
    public WebElement waitForElementClickable(By locator, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            throw new RuntimeException("Failed to find clickable element by locator: " + locator, e);
        }
    }

    /**
     * This method is used to Waits for an element to be clickable, then clicks it.
     *
     * @param locator : Provide the locator contains List of elements.
     * @param timeOut : Provide Maximum time to wait for the element to be clickable.
     */
    public void clickWhenReady(By locator, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        } catch (Exception e) {
            throw new ElementException("Failed to click element by locator: " + locator, e);
        }
    }

    /**
     * This method Waits for the page title to contain a specific text.
     *
     * @param titleFraction : Provide The partial title text to wait for.
     * @param timeOut       : Provide Maximum time to wait for the title to contain the text.
     * @return : String The current title of the page.
     */
    public String waitForTitleContains(String titleFraction, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
                return driver.getTitle();
            } else {
                throw new ElementException("Title does not contain: " + titleFraction);
            }
        } catch (Exception e) {
            throw new ElementException("Failed to wait for title to contain: " + titleFraction, e);
        }
    }

    /**
     * This Method Waits for the page title to be exactly as specified.
     *
     * @param titleVal : Provide The exact title text to wait for.
     * @param timeOut  : Provide Maximum time to wait for the title to be the specified text.
     * @return : String The current title of the page.
     */
    @Step("Waiting for the page title and capturing it.")
    public String waitForTitleToBe(String titleVal, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            if (wait.until(ExpectedConditions.titleIs(titleVal))) {
                return driver.getTitle();
            } else {
                throw new ElementException("Title is not: " + titleVal);
            }
        } catch (Exception e) {
            throw new ElementException("Failed to wait for title to be: " + titleVal, e);
        }
    }

    /**
     * This method Waits for the URL to contain a specific text.
     *
     * @param urlFraction : Provide The partial URL text to wait for.
     * @param timeOut     : Provide Maximum time to wait for the URL to contain the text.
     * @return : Sting The current URL of the page.
     */
    @Step("")
    public String waitForURLContains(String urlFraction, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
                return driver.getCurrentUrl();
            } else {
                throw new ElementException("URL does not contain: " + urlFraction);
            }
        } catch (Exception e) {
            throw new ElementException("Failed to wait for URL to contain: " + urlFraction, e);
        }
    }

    /**
     * This method Waits for the URL to be exactly as specified.
     *
     * @param urlValue : Provide The exact URL text to wait for.
     * @param timeOut  : Provide Maximum time to wait for the URL to be the specified text.
     * @return : String The current URL of the page.
     */
    public String waitForURLToBe(String urlValue, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            if (wait.until(ExpectedConditions.urlToBe(urlValue))) {
                return driver.getCurrentUrl();
            } else {
                throw new ElementException("URL is not: " + urlValue);
            }
        } catch (Exception e) {
            throw new ElementException("Failed to wait for URL to be: " + urlValue, e);
        }
    }

    /**
     * This method Waits for a JavaScript alert to be present.
     *
     * @param timeOut : provide Maximum time to wait for the alert.
     * @return : Alert The alert present on the page.
     */
    public Alert waitForJSAlert(int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            return wait.until(ExpectedConditions.alertIsPresent());
        } catch (Exception e) {
            throw new ElementException("Failed to wait for JS alert", e);
        }
    }

    /**
     * This method Waits for a JavaScript alert to be present, with custom polling intervals.
     *
     * @param timeOut      : Provide Maximum time to wait for the alert.
     * @param intervalTime : Provide Polling interval time.
     * @return : Alert - The alert present on the page.
     */

    public Alert waitForJSAlert(int timeOut, int intervalTime) {
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(timeOut))
                    .pollingEvery(Duration.ofSeconds(intervalTime))
                    .ignoring(NoAlertPresentException.class)
                    .withMessage("===alert is not found===");
            return wait.until(ExpectedConditions.alertIsPresent());
        } catch (Exception e) {
            throw new ElementException("Failed to wait for JS alert with interval", e);
        }
    }

    /**
     * This Method Retrieves the text of a JavaScript alert and accepts it.
     *
     * @param timeOut : Provide Maximum time to wait for the alert.
     * @return : String - The text of the alert.
     */
    public String getAlertText(int timeOut) {
        try {
            Alert alert = waitForJSAlert(timeOut);
            String text = alert.getText();
            alert.accept();
            return text;
        } catch (Exception e) {
            throw new ElementException("Failed to get alert text", e);
        }
    }

    /**
     * This Method Waits for a JavaScript alert to be present and accepts it.
     *
     * @param timeOut : Provide Maximum time to wait for the alert.
     */

    public void acceptAlert(int timeOut) {
        try {
            waitForJSAlert(timeOut).accept();
        } catch (Exception e) {
            throw new ElementException("Failed to accept alert", e);
        }
    }

    /**
     * This method Waits for a JavaScript alert to be present and dismisses it.
     *
     * @param timeOut : Provide Maximum time to wait for the alert.
     */

    public void dismissAlert(int timeOut) {
        try {
            waitForJSAlert(timeOut).dismiss();
        } catch (Exception e) {
            throw new ElementException("Failed to dismiss alert", e);
        }
    }

    /**
     * This method Sends keys to a JavaScript alert and accepts it.
     *
     * @param timeOut : Provide Maximum time to wait for the alert.
     * @param value   : Provide The text to send to the alert.
     */

    public void alertSendKeys(int timeOut, String value) {
        try {
            Alert alert = waitForJSAlert(timeOut);
            alert.sendKeys(value);
            alert.accept();
        } catch (Exception e) {
            throw new ElementException("Failed to send keys to alert", e);
        }
    }

    /**
     * This Method Waits for a frame to be available to switch to it using a locator.
     *
     * @param frameLocator : Provide Locator strategy to find the frame.
     * @param timeOut      : Provide Maximum time to wait for the frame to be available.
     */
    public void waitForFrameByLocator(By frameLocator, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
        } catch (Exception e) {
            throw new ElementException("Failed to wait for frame by locator: " + frameLocator, e);
        }
    }

    /**
     * This method Waits for a frame to be available to switch to it using a locator, with custom polling intervals.
     *
     * @param frameLocator : Provide Locator strategy to find the frame.
     * @param timeOut      : Provide Maximum time to wait for the frame to be available.
     * @param intervalTime :  Polling interval time.
     */

    public void waitForFrameByLocator(By frameLocator, int timeOut, int intervalTime) {
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(timeOut))
                    .pollingEvery(Duration.ofSeconds(intervalTime))
                    .ignoring(NoSuchFrameException.class)
                    .withMessage("===frame is not found===");
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
        } catch (Exception e) {
            throw new ElementException("Failed to wait for frame by locator with interval: " + frameLocator, e);
        }
    }

    /**
     * This method Waits for a frame to be available to switch to it using an index.
     *
     * @param frameIndex : provide Index of the frame.
     * @param timeOut    : provide Maximum time to wait for the frame to be available.
     */

    public void waitForFrameByIndex(int frameIndex, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
        } catch (Exception e) {
            throw new ElementException("Failed to wait for frame by index: " + frameIndex, e);
        }
    }

    /**
     * This Method Waits for a frame to be available to switch to it using its ID or name.
     *
     * @param frameIDOrName : Provide ID or name of the frame.
     * @param timeOut       : Provide Maximum time to wait for the frame to be available.
     */
    public void waitForFrameByIndex(String frameIDOrName, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIDOrName));
        } catch (Exception e) {
            throw new ElementException("Failed to wait for frame by ID or name: " + frameIDOrName, e);
        }
    }

    /**
     * This Method Waits for a frame to be available to switch to it using a WebElement.
     *
     * @param frameElement : Provide The frame element.
     * @param timeOut      : Provide Maximum time to wait for the frame to be available.
     */
    public void waitForFrameByIndex(WebElement frameElement, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
        } catch (Exception e) {
            throw new ElementException("Failed to wait for frame by WebElement", e);
        }
    }

    /**
     * This Method Waits for the number of browser windows to be a specific number.
     *
     * @param totalWindows : Provide The expected number of windows.
     * @param timeOut      : Provide Maximum time to wait for the number of windows.
     * @return : boolean - true if the number of windows is as expected, false otherwise.
     */
    public boolean waitForWindowsToBe(int totalWindows, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            return wait.until(ExpectedConditions.numberOfWindowsToBe(totalWindows));
        } catch (Exception e) {
            throw new ElementException("Failed to wait for number of windows to be: " + totalWindows, e);
        }
    }

    /**
     * This Method Waits for the page to be completely loaded.
     *
     * @param timeOut : Maximum time to wait for the page to load.
     */

    public void isPageLoaded(int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'")).toString();
            if (Boolean.parseBoolean(flag)) {
                System.out.println("Page is completely loaded");
            } else {
                throw new ElementException("Page is not completely loaded");
            }
        } catch (Exception e) {
            throw new ElementException("Failed to wait for page load", e);
        }
    }

    /* ===================== Generic / Wrapper Methods ============================ */


    /**
     * Highlights the element if highlighting is enabled.
     *
     * @param element The WebElement to highlight.
     */
    private void highlightElement(WebElement element) {
        if(Boolean.parseBoolean(DriverFactory.highlight)) {
            jsUtil.flash(element);
        }
    }

    /**
     * This is Private method to Checks if the provided string value is null and throws an ElementException if it is.
     *
     * @param value : Provide the String value to be checked for null
     * @throws ElementException : if the provided value is null
     */
    private void nullCheck(String value) {
        try {
            if (value == null) {
                throw new ElementException("VALUE IS NULL: " + value);
            }
        } catch (Exception e) {
            throw new ElementException("Error during null check", e);
        }
    }

    /**
     * This method is to find an element on the web page using the given locator.
     * @param locator : The locator used to find the element.
     * @return : WebElement.
     */
    @Step("finding the element using locator : {0}")
    public WebElement getElement(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            highlightElement(element);
            return element;
        } catch (NoSuchElementException e) {
            throw new ElementException("Element is not present on the page: " + locator, e);
        }
    }

    /**
     * This method is to input text into the specified field.
     *
     * @param locator : provide the input field Element locator.
     * @param value   : Provide the input value/text in the Field.
     * @throws ElementException : if the value is null
     */
    @Step("Entering value {1} in text field using locator : {0}")
    public void doSendKeys(By locator, String value) {
        try {
            nullCheck(value);
            WebElement element = getElement(locator);
            element.clear();
            element.sendKeys(value);
        } catch (Exception e) {
            throw new ElementException("Error during sending keys to element: " + locator, e);
        }
    }

    /**
     * Inputs text into the specified field with a timeout for element visibility.
     *
     * @param locator The locator of the input field.
     * @param value   The text to input.
     * @param timeOut The timeout in seconds.
     */
    @Step("Entering value {1} in text field using locator: {0} with timeout: {2} secs")
    public void doSendKeys(By locator, String value, int timeOut) {
        nullCheck(value);
        WebElement element = waitForElementVisible(locator, timeOut);
        element.clear();
        element.sendKeys(value);
    }

    /**
     * This method inputs a sequence of characters into the specified field.
     *
     * @param locator : The locator of the input field element
     * @param value   :  The sequence of characters to be input into the field.
     */
    public void doSendKeys(By locator, CharSequence... value) {
        getElement(locator).sendKeys(value);
    }

    /**
     * This method clicks on the specified element.
     *
     * @param locator : Provide the locator of the element to be clicked.
     */
    @Step("clicking on element using locator: {0}")
    public void doClick(By locator) {
        try {
            getElement(locator).click();
        } catch (Exception e) {
            throw new ElementException("Error during clicking element: " + locator, e);
        }
    }

    /**
     * This method Clicks on the specified element with a timeout for visibility.
     *
     * @param locator : Provide the locator of the element to be clicked.
     * @param timeOut : Provide Timeout duration for waiting for the element to be visible.
     */
    @Step("clicking on element using locator: {0} and waiting for element with : {1} secs")
    public void doClick(By locator, int timeOut) {
        try {
            waitForElementVisible(locator, timeOut).click();
        } catch (Exception e) {
            throw new ElementException("Error during clicking element with timeout: " + locator, e);
        }
    }

    /**
     * This method returns the text of the specified element.
     *
     * @param locator : Provide the locator of the element to get the text.
     * @return : The text of the specified element.
     */
    @Step("finding the element Text using locator : {0} and waiting for element with : {1} secs")
    public String doGetText(By locator, int timeout) {
        try {
            return waitForElementVisible(locator, timeout).getText().replace("\n", " ");
        } catch (Exception e) {
            throw new ElementException("Error during getting text from element: " + locator, e);
        }
    }

    /**
     * This method returns the text of a specific attribute of the element found by the locator.
     *
     * @param locator  : Provide the locator of the element to get the text.
     * @param attrName : Provide the name of attrName.
     * @return : The text of the specified element.
     */
    @Step("finding the element Attribute using locator : {0} and waiting for element with : {1} secs")
    public String doGetAttribute(By locator, String attrName) {
        try {
            return getElement(locator).getAttribute(attrName);
        } catch (Exception e) {
            throw new ElementException("Error during getting attribute from element: " + locator, e);
        }
    }

    /**
     * This method checks if the element found by the locator is displayed on the web page.
     *
     * @param locator : Provide the locator of the element to be displayed.
     * @return : Boolean.
     */
    @Step("checking the state of locator: {0} is displayed or not..")
    public boolean doIsDisplayed(By locator, int timeOut) {
        try {
            WebElement element = getElement(locator);
            if (element != null && waitForElementVisible(locator, timeOut).isDisplayed()) {
                System.out.println("Element is displayed: " + locator);
                return true;
            } else {
                System.out.println("Element with locator : " + locator + " is not displayed");
                return false;
            }
        } catch (Exception e) {
            throw new ElementException("Error during checking if element is displayed: " + locator, e);
        }
    }

    /**
     * This Method check the element is displayed or not.
     *
     * @param locator : Provide the locator of the element to be displayed.
     * @return : Boolean true if the element is displayed
     */
    public boolean isElementDisplayed(By locator) {
        int elementCount = getElements(locator).size();
        if (elementCount == 1) {
            System.out.println("single element is displayed: " + locator);
            return true;
        } else {
            System.out.println("multiple or zero elements are displayed: " + locator);
            return false;
        }
    }

    /**
     * This Method check the number of elements are displayed or not.
     *
     * @param locator : Provide the locator of the elements to be displayed.
     * @return : Boolean true if the elements is displayed
     */
    public boolean isElementsDisplayed(By locator, int expectedElementCount) {
        int elementCount = getElements(locator).size();
        if (elementCount == expectedElementCount) {
            System.out.println("element is displayed: " + locator + " with the occurrence of " + elementCount);
            return true;
        } else {
            System.out.println(
                    "multiple or zero elements are displayed: " + locator + " with the occurrence of " + elementCount);
            return false;
        }
    }


    /**
     * This method Checks if the checkbox, radio button, or dropdown is selected.
     *
     * @param locator :Provide the locator of the element to be selected.
     * @return : Boolean
     */
    public boolean doIsSelected(By locator) {
        try {
            WebElement element = waitForElementVisible(locator,TimeUtil.DEFAULT_TIME);
            boolean flag = element.isSelected();
            System.out.println(flag ? "The element is Selected" : "The element is not Selected");
            return flag;
        } catch (Exception e) {
            throw new ElementException("Error during checking if element is selected: " + locator, e);
        }
    }

    /**
     * WAF: to fetch the text of each link, store it in some list and return.
     * This method Fetches a list of elements found by the locator.
     *
     * @param locator : Provide the locator contains number of elements.
     * @return : List of WebElement
     */
    public List<WebElement> getElements(By locator) {
        return waitForVisibilityOfElementsLocated(locator, 5);
    }

    /**
     * This Method is to find the list of Elements are displayed or Not.
     *
     * @param LocatorList : Provide the locator for the list of elements to be checked.
     * @param timeOut     : Provide Maximum time to wait for the element to be visible.
     */
    public void doIsDisplayedElementsList(By LocatorList, int timeOut) {
        List<WebElement> elementList = getElements(LocatorList);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut)); // Adjust the timeout as per your requirement
        for (WebElement element : elementList) {
            boolean isDisplayed = false;
            try {
                wait.until(ExpectedConditions.visibilityOf(element));
                isDisplayed = element.isDisplayed();
            } catch (NoSuchElementException e) {
                // Element not found, continue to the next one
            }

            if (isDisplayed) {
                System.out.println("Element is Displayed : " + element.getText());
            } else {
                System.out.println("element with locator : " + element + " is not displayed");
            }
            //Thread.sleep(1);
        }
    }

    /**
     * This Method Checks if each element in the list found by the locator is displayed on the web page.
     *
     * @param locator : Provide The locator for the elements to be checked.
     * @return : A list of Boolean values indicating whether each element is displayed [True / False]
     */
    public List<Boolean> areElementsDisplayed(By locator) {
        List<WebElement> elements = getElements(locator);
        List<Boolean> displayedStatus = new ArrayList<>();

        for (WebElement element : elements) {
            try {
                displayedStatus.add(element.isDisplayed());
                System.out.println("element is displayed: " + element);
            } catch (NoSuchElementException e) {
                displayedStatus.add(false);
                System.out.println("element with locator : " + locator + " is not displayed");
            }
        }
        return displayedStatus;
    }

    /**
     * This Method is click on All the Web Elements in the List.
     *
     * @param listLocator : provide the locator representing the list
     * @param timeOut     : Provide Maximum time to wait for the element to be clickable.
     */
    public void doClicks(By listLocator, int timeOut) {
        List<WebElement> eleList = getElements(listLocator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        for (WebElement e : eleList) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(e));
                e.click();
                //Thread.sleep(2);
                // Wait for some condition after click, e.g., visibility of a specific element or state change
                wait.until(ExpectedConditions.stalenessOf(e));
            } catch (Exception ex) {
                throw new ElementException("Failed to click on element: " + e.toString(), ex);
            }
        }
    }

    /**
     * This method is Gets the count of elements found by the locator.
     *
     * @param locator : Provide The locator for the elements to be counted.
     * @return : Int element count.
     */

    public int getElementsCount(By locator) {
        return getElements(locator).size();
    }

    /**
     * This method collect the text in the WebElements and save in the List as String
     *
     * @param ListLocator : Provide the Locator of List.
     * @return : List Of string
     */
    public List<String> getElementsTextList(By ListLocator) {
        List<WebElement> eleList = getElements(ListLocator);
        List<String> eleTextList = new ArrayList<>();

        for (WebElement e : eleList) {
            String text = e.getText();
            if (!text.isEmpty()) {
                eleTextList.add(text);
            }
        }
        return eleTextList;
    }

    /**
     * if Element Texts are in New Lines e.g. "Vistara
     * ₹ 12272"
     * Use this Method to Print the texts without New Line.
     *
     * @param ListLocator : Provide the Locator of List.
     */
    public void printElementsTextWithoutNewLine(By ListLocator) {
        List<String> elementList = getElementsTextList(ListLocator);

        // List to store list without newline characters
        List<String> elementListWithoutNewLine = new ArrayList<>();

        // Remove newline characters from each string in DayWiseFare and store in DayWiseFareWithoutNewLine
        for (String element : elementList) {
            String withoutNewline = element.replace("\n", " ");
            elementListWithoutNewLine.add(withoutNewline);
        }
        System.out.println(elementListWithoutNewLine);
    }

    /**
     * This Method Retrieves the specified attribute value of each element in the list found by the locator.
     *
     * @param locator  : Provide The locator for the elements.
     * @param attrName : Provide The name of the attribute to be retrieved.
     * @return : List Of string
     */
    public List<String> getElementsAttributeList(By locator, String attrName) {
        List<WebElement> imagesList = getElements(locator);
        List<String> attrList = new ArrayList<>();

        for (WebElement e : imagesList) {
            String attrVal = e.getAttribute(attrName);
            if (attrVal != null && !attrVal.isEmpty()) {
                attrList.add(attrVal);
            }
        }
        return attrList;
    }

    /*==================== Select drop down utils =================*/
    // for Checkbox,RadioButton, dropdown

    /**
     * This method Selects an option by index from a dropdown element found by the locator.
     *
     * @param locator : Provide The locator of the dropdown element.
     * @param index   : Provide The index of the option to be selected.
     */
    public void doSelectByIndex(By locator, int index) {
        Select select = new Select(getElement(locator));
        select.selectByIndex(index);
    }

    /**
     * This Method Selects an option by visible text from a dropdown element found by the locator.
     *
     * @param locator     : Provide The locator of the dropdown element.
     * @param visibleText : Provide The visible text of the option to be selected.
     */
    public void doSelectByVisibleText(By locator, String visibleText) {
        Select select = new Select(getElement(locator));
        select.selectByVisibleText(visibleText);
    }

    /**
     * This method Selects an option by value from a dropdown element found by the locator.
     *
     * @param locator : Provide The locator of the dropdown element.
     * @param value   : Provide The value of the option to be selected.
     */
    public void doSelectByValue(By locator, String value) {
        Select select = new Select(getElement(locator));
        select.selectByValue(value);
    }

    /**
     * This method Gets the count of options in a dropdown element found by the locator.
     *
     * @param locator : Provide The locator of the dropdown element.
     * @return : int count of the options in the Dropdown.
     */
    public int getDropDownOptionsCount(By locator) {
        Select select = new Select(driver.findElement(locator));
        return select.getOptions().size();
    }

    /**
     * This method Gets the visible text of each option in a dropdown element found by the locator.
     *
     * @param locator : Provide The locator of the dropdown element.
     * @return : list of the option text values.
     */
    public List<String> getDropDownOptionsTextList(By locator) {
        Select select = new Select(driver.findElement(locator));
        List<WebElement> optionsList = select.getOptions();
        List<String> optionsTextList = new ArrayList<>();

        for (WebElement e : optionsList) {
            String text = e.getText();
            optionsTextList.add(text);
        }
        return optionsTextList;
    }

    /**
     * This method Selects an option by visible text from a dropdown element found by the locator.
     *
     * @param locator    : Provide The locator of the dropdown element.
     * @param optionText : Provide The visible text of the option to be selected.
     */
    public void selectValueFromDropDown(By locator, String optionText) {
        Select select = new Select(getElement(locator));
        List<WebElement> optionsList = select.getOptions();

        for (WebElement e : optionsList) {
            String text = e.getText();
            if (text.equals(optionText.trim())) {
                e.click();
                break;
            }
        }
    }

    /**
     * This method Selects an option by visible text from a dropdown element
     * found by the locator without using the Select class.
     *
     * @param locator    : Provide The locator of the dropdown element.
     * @param optionText :  Provide The visible text of the option to be selected.
     */
    public void selectValueFromDropDownWithoutSelectClass(By locator, String optionText) {
        List<WebElement> optionsList = getElements(locator);
        for (WebElement e : optionsList) {
            String text = e.getText();
            if (text.equals(optionText)) {
                e.click();
                break;
            }
        }
    }

    /**
     * This Method handle multiple windows by iterating through all available windows and
     * switching to each one that is not the original window.
     *
     * @param expectedTitle : Provide the expected title for the window.
     * @return : boolean
     */
    public boolean switchToNewTabAndVerifyTitle(String expectedTitle) {
        String originalWindow = driver.getWindowHandle();
        // 1. fetch the windows ids:
        Set<String> allWindows = driver.getWindowHandles();
        boolean found = false;

        for (String window : allWindows) {
            if (!window.equals(originalWindow)) {
                driver.switchTo().window(window);
                if (driver.getTitle().contains(expectedTitle)) {
                    found = true;
                    // Close the tab if the expected title is found
                    driver.close();
                    break;
                }
                driver.close();
            }
        }
        // Switch back to the original window
        driver.switchTo().window(originalWindow);

        return found;
    }

    public String SwitchToNewWindowAndGetTitle(By ClickLocator, int timeOut){
        //waitForElementVisible(ClickLocator,timeOut).click();// child
        waitForElementVisible(ClickLocator, timeOut);
        clickWhenReady(ClickLocator, timeOut);
        //jsUtil.jsClick(ClickLocator);
        // 1. fetch the windows ids:
        Set<String> handles = driver.getWindowHandles();

        // set-->list
        List<String> handlesList = new ArrayList<String>(handles);
        String parentWindowId = handlesList.get(0);
        //System.out.println(parentWindowId);

        String childWindowId = handlesList.get(1);
        //System.out.println(childWindowId);

        // 2. switching work:
        driver.switchTo().window(childWindowId);
        TimeUtil.sleep(2);
        String NewWindowTitle = driver.getTitle();
        System.out.println("child window Title : " + driver.getTitle());

        driver.close();//close the child window

        // driver is lost
        driver.switchTo().window(parentWindowId);
        return NewWindowTitle;
    }

    /*  =================== Actions utils  ====================*/

    /**
     * Method to move to an element using the Actions class.
     *
     *  @param elementLocator The WebElement to move to.
     *  @param timeOut  provide the timeOut.
     */
    public void moveToElement(By elementLocator, int timeOut) {
        Actions act = new Actions(driver);
        act.moveToElement(waitForElementVisible(elementLocator, timeOut)).perform();
    }

    /**
     * This Method Drags the source element and drops it onto the target element.
     *
     * @param sourceLocator : locator of the source element
     * @param targetLocator : locator of the target element
     */
    public void doDragAndDrop(By sourceLocator, By targetLocator) {
        try {
            Actions act = new Actions(driver);
            act.dragAndDrop(getElement(sourceLocator), getElement(targetLocator)).perform();
        } catch (Exception e) {
            throw new ElementException("Drag and drop action failed: " + e.getMessage());
        }
    }

    /**
     * This method Performs a send keys action on the element.
     *
     * @param locator : locator of the element
     * @param value   : text to be entered
     */
    public void doActionsSendKeys(By locator, String value) {
        try {
            Actions act = new Actions(driver);
            act.sendKeys(getElement(locator), value).perform();
        } catch (Exception e) {
            throw new ElementException("Send keys action failed: " + e.getMessage());
        }
    }

    /**
     * This method Performs a click action on the element.
     *
     * @param locator : locator of the element
     */
    public void doActionsClick(By locator) {
        try {
            Actions act = new Actions(driver);
            act.click(getElement(locator)).perform();
        } catch (Exception e) {
            throw new ElementException("Click action failed: " + e.getMessage());
        }
    }

    /**
     * This method Enters the value in the text field with a pause.
     *
     * @param locator   : locator of the element
     * @param value     : text to be entered
     * @param pauseTime : time to pause after each character
     */
    public void doActionsSendKeysWithPause(By locator, String value, long pauseTime) {
        try {
            Actions act = new Actions(driver);
            char[] ch = value.toCharArray();
            for (char c : ch) {
                act.sendKeys(getElement(locator), String.valueOf(c)).pause(pauseTime).perform();
            }
        } catch (Exception e) {
            throw new ElementException("Send keys with pause action failed: " + e.getMessage());
        }
    }

    /**
     * Enters the value in the text field with a default pause of 500 ms.
     *
     * @param locator : locator of the element
     * @param value   : text to be entered
     */
    public void doActionsSendKeysWithPause(By locator, String value) {
        doActionsSendKeysWithPause(locator, value, 500);
    }





















}

