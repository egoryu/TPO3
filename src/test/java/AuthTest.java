import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

@Slf4j
public class AuthTest {
    private static WebDriver chromeDriver;

    @BeforeAll
    static void setup() {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        chromeDriver = new ChromeDriver();
        chromeDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @AfterAll
    static void clean() {
        chromeDriver.quit();
    }

    @BeforeEach
    public void setUrl() {
        chromeDriver.get("https://github.com/");
        chromeDriver.manage().window().setSize(new Dimension(974, 815));
    }

    public static void logIn(WebDriver driver) {
        driver.findElement(By.xpath("//a[contains(text(),'Sign in')]")).click();
        driver.findElement(By.xpath("//input[@id='login_field']")).click();
        driver.findElement(By.xpath("//input[2]")).sendKeys("Keker14");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("testkek@1");
        driver.findElement(By.xpath("//input[13]")).click();
        String home = driver.findElement(By.xpath("//feed-container/div/h2")).getText();
        Assertions.assertEquals("Home", home);
    }

    private void logOut(WebDriver driver) {
        driver.findElement(By.xpath("//img")).click();
        driver.findElement(By.xpath("//span[contains(.,'Sign out')]")).click();
        driver.findElement(By.xpath("//input[@name='commit']")).click();
        String home = driver.findElement(By.xpath("//h1/span")).getText();
        Assertions.assertEquals("Let’s build from here", home);
    }

    @Test
    public void logInTest() {
        logIn(chromeDriver);
    }

    @Test
    public void logOutTest() {
        logIn(chromeDriver);
        logOut(chromeDriver);
    }
}
