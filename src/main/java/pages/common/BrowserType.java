package pages.common;

public enum BrowserType {
    CHROME("webdriver.chrome.driver", "E:\\WebDriver"),
    FIREFOX("webdriver.gecko.driver", ""),
    EDGE("webdriver.edge.driver", "");

    BrowserType(String property, String executePath){
        System.setProperty(property, executePath);
    }
}
