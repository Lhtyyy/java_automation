package pages.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Browser {
    private Logger logger = LogManager.getLogger(Browser.class);
    private WebDriver driver;
    private int implicitlyWaitInterval = 20;
    private JavascriptExecutor jsExecutor;
    private Actions actions;

    public Browser(BrowserType browserType) {
        driver = browserInit(browserType);
    }


    public WebDriver browserInit(BrowserType browserType) {
        WebDriver driver = null;
        switch (browserType) {
            case FIREFOX:
                driver = null;
                break;

            case EDGE:
                driver = null;
                break;

            case CHROME:
            default:
                driver = chromeInit();
        }
        if (driver == null) {
            throw new RuntimeException("Fail to init browser");
        }
        actions = new Actions(driver);
        jsExecutor = (JavascriptExecutor) driver;

        return driver;
    }

    public static RemoteWebDriver chromeInit() {
        RemoteWebDriver driver;
        File chromeDriverFile = new File("src\\main\\resources\\drivers\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", chromeDriverFile.toString());
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }

    public void openUrl(String url) {
        driver.get(url);
    }

    public void quit() {
        driver.quit();
    }

    public void sleepInfo(long millis, String info) {
        logger.info("Sleep "+ millis + " millis for " + info);
        try {
            Thread.sleep(millis);

        } catch (InterruptedException e) {

        }
    }

    public String getCurrentURL() {
        String url = driver.getCurrentUrl();
        logger.info(String.format("The current URL is %s", url));
        return url;
    }

    public void refresh() {
        driver.navigate().refresh();
    }

    public By generateBy(String type, String value) {
        Method generator = null;
        By by = null;
        try {
            generator = By.class.getMethod(type, String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            assert generator != null;
            by = (By) generator.invoke(By.class, value);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return by;
    }

    public WebElement findElement(String selector, String value) {
        return driver.findElement(generateBy(selector, value));
    }

    public WebElement findElement(String selector, String value, long waitTime) {
        return new WebDriverWait(driver, waitTime).until(
                ExpectedConditions.presenceOfElementLocated(generateBy(selector, value))
        );
    }

    public WebElement findElement(WebElement parentElement, String selector, String value) {
        return parentElement.findElement(generateBy(selector, value));
    }

    public List<WebElement> findElements(String selector, String value) {
        return driver.findElements(generateBy(selector, value));
    }

    public List<WebElement> findElements(WebElement parentElement, String selector, String value) {
        return parentElement.findElements(generateBy(selector, value));
    }


    public WebElement findElementById(String value) {
        return findElement("id", value);
    }

    public boolean isDisplayed(WebElement webElement) {
        return webElement.isDisplayed();
    }

    public String getAttribute(WebElement webElement, String name) {
        return webElement.getAttribute(name);
    }

    public String getTagName(WebElement webElement) {
        return webElement.getTagName();
    }

    public String getCssValue(WebElement webElement, String name) {
        return webElement.getCssValue(name);
    }

    public String getText(WebElement webElement) {
        return webElement.getText();
    }

    public void clear(WebElement webElement) {
        webElement.clear();
    }

    public void sendKeys(WebElement webElement, String keys, boolean isClearFirst) {
        if (isClearFirst) {
            clear(webElement);
        }

        webElement.sendKeys(keys);
    }

    public void sendKeys(WebElement webElement, String keys) {
        sendKeys(webElement, keys, true);
    }

    public void mouseToElement(WebElement webElement) {
        this.actions.moveToElement(webElement).perform();
    }

    public boolean isEnable(WebElement webElement) {
        return webElement.isEnabled();
    }

    public void click(WebElement webElement) {
        webElement.click();
    }

    public void doubleClick(WebElement webElement) {
        logger.info("double click on element");
        this.actions.doubleClick(webElement).build().perform();
    }

    public boolean isSelected(WebElement webElement) {
        return webElement.isSelected();
    }

    public boolean isMultipleSelect(WebElement webElement) {
        boolean isMultiple = new Select(webElement).isMultiple();
        return isMultiple;
    }

    public ArrayList<String> getSelectOptions(WebElement webElement) {
        Select select = new Select(webElement);
        List<WebElement> elements = select.getOptions();
        ArrayList<String> options = new ArrayList<>();
        for (WebElement element : elements) {
            options.add(element.getText());
        }
        logger.info(String.format("The options are: %s", String.join(", ", options)));
        return options;
    }

    public String getCurrentSelectedOption(WebElement webElement) {
        Select selectElement = new Select(webElement);
        String CurrentSelectedOption = selectElement.getFirstSelectedOption().getText();
        logger.info("The current selected option is " + CurrentSelectedOption);
        return CurrentSelectedOption;
    }

    public void selectByIndex(WebElement webElement, int index) {
        Select select = new Select(webElement);
        logger.info("To select option via index " + index);
        select.selectByIndex(index);
    }

    public void selectByVisibleText(WebElement webElement, String text) {
        Select select = new Select(webElement);
        select.selectByVisibleText(text);
    }

    public void multipleSelect(WebElement webElement, String... options) {
        logger.info("To multiple select below options: " + String.join(", ", options));
        if (!isMultipleSelect(webElement)) {
            logger.warn(String.format("The select element is NOT support multiple selected," +
                    " to select first target option %s only", options[0]));
            selectByVisibleText(webElement, options[0]);
        }
        for (String option : options) {
            selectByVisibleText(webElement, option);
        }
    }

    public void clickRadio(WebElement webElement) {
        boolean isSelected = isSelected(webElement);
        if (isSelected) {
            return;
        }
        click(webElement);
    }

    public Set<Cookie> getCookies() {
        return driver.manage().getCookies();
    }

    public Cookie getCookie(String name) {
        return driver.manage().getCookieNamed(name);
    }

    public void selectByValue(WebElement webElement, String value) {
        Select select = new Select(webElement);
        logger.info("To select option via value " + value);
        select.selectByValue(value);
    }

    public Object executeJs(String script, Object... args) {
        Object toReturn = null;
        try {
            toReturn = jsExecutor.executeScript(script, args);
        } catch (JavascriptException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    public boolean removeAttribute(WebElement webElement, String name) {
        boolean isSuccess = false;
        String js = "arguments[0].removeAttribute(arguments[1])";
        Object executeResult = executeJs(js, webElement, name);
        // add judgement to result
        return false;
    }

    public boolean removeAttribute(String css, String name) {
        boolean isSuccess = false;
        String js = String.format("document.querySelector(%s).removeAttribute(%s);", css, name);
        Object executeResult = executeJs(js);
        return false;
    }

    public void setAttribute(WebElement webElement, String name, String value) {
        boolean isSuccess = false;
        String js = "arguments[0].setAttribute(arguments[1], arguments[2])";
        Object executeResult = executeJs(js, webElement, name, value);
    }

    public String getInnerText(WebElement webElement) {
        String js = "arguments[0].innerText";  // getAttribute???
        return (String) executeJs(js);
    }

    public void scrollToBottom() {
        executeJs("window.scrollTo(0,document.body.scrollHeight);");
    }

    public void scrollToTop() {
        executeJs("window.scrollTo(document.body.scrollHeight,0);");
    }

}
