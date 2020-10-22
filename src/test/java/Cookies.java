import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.GregorianCalendar;
import java.util.Set;

import static java.lang.Thread.*;


public class Cookies {
    WebDriver driver;

    @BeforeEach
    public void driverSetup() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1290, 730));
        driver.manage().window().setPosition(new Point(8, 31));
        driver.navigate().to("https://www.amazon.com");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(8, driver.manage().getCookies().size(), "Number of cokkies is not what expected");
    }

    @AfterEach
    public void closeAndQuit() {
        driver.close();
        driver.quit();
    }

    @Test
    public void gettingAndDeletingCookies(){
        Cookie cookieSessionId = driver.manage().getCookieNamed("session-id");
        driver.manage().deleteCookieNamed("session-id");
        Assertions.assertEquals(7, driver.manage().getCookies().size(),"Number of cookies is not what expected");

        driver.manage().deleteAllCookies();
        Assertions.assertEquals(0, driver.manage().getCookies().size(),"Number of cookies is not what expected");
    }

    @Test
    public void addingCookies(){
        Cookie newCookie = new Cookie("test_cookie", "test_value", "amazon.com", "/",
                new GregorianCalendar(2021, 10, 31).getTime(), true, true);
        driver.manage().addCookie(newCookie);
        Assertions.assertEquals(9, driver.manage().getCookies().size(), "Number of cookies is not what expected");
        Cookie secondCookie = new Cookie("test_cookie2", "test_value2");
        driver.manage().addCookie(secondCookie);

        Assertions.assertEquals(10, driver.manage().getCookies().size(), "Number of cookies is not what expected");
        driver.manage().deleteCookie(newCookie);
        Assertions.assertEquals(9, driver.manage().getCookies().size(), "Number of cookies is not what expected");
    }

}