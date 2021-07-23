import org.testng.annotations.Test;
import pages.common.Browser;

import static pages.common.BrowserType.CHROME;

public class BrowserTest {
    @Test
    public void testBrowser(){
        Browser browser =  new Browser(CHROME);
        browser.openUrl("http://www.baidu.com");
        System.out.println(browser.getCurrentURL());
        browser.sleepInfo(1000, "Test");
        browser.quit();
    }
}
