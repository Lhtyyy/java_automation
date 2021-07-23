import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HelloSelenium {
    public static void main(String[] args) {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://www.bilibili.com/");
        System.out.println("Test");
    }
}
