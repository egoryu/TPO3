import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class OtherProfileTest {
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

    public void checkPom(WebDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//qbsearch-input/div/button")).click();
        driver.findElement(By.xpath("//div[2]/input")).sendKeys("egoryu");
        driver.findElement(By.xpath("//div[2]/input")).sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        driver.findElement(By.xpath("//li[6]/a/div/div/span")).click();
        driver.findElement(By.xpath("//div/a/span/span")).click();
        driver.findElement(By.xpath("//nav/ul/li[2]/a")).click();
        Thread.sleep(1000);

        driver.findElement(By.xpath("//a[contains(text(),'BLPS')]")).click();
        driver.findElement(By.xpath("(//a[contains(text(),'pom.xml')])[2]")).click();
        Thread.sleep(1000);
        Assertions.assertEquals("https://github.com/egoryu/BLPS/blob/main/pom.xml", driver.getCurrentUrl());
    }

    public void follow(WebDriver driver) throws InterruptedException {
        driver.get("https://github.com/egoryu");
        Thread.sleep(1000);

        driver.findElement(By.xpath("//input[@name='commit']")).click();
        Thread.sleep(1000);
        String home = driver.findElement(By.xpath("//form[2]/input[2]")).getAttribute("value");
        Assertions.assertEquals("Unfollow", home);
    }

    public void unFollow(WebDriver driver) throws InterruptedException {
        driver.get("https://github.com/egoryu");
        Thread.sleep(1000);

        driver.findElement(By.xpath("//form[2]/input[2]")).click();
        Thread.sleep(1000);
        String home = driver.findElement(By.xpath("//input[@name='commit']")).getAttribute("value");
        Assertions.assertEquals("Follow", home);
    }

    public void addStar(WebDriver driver) throws InterruptedException {
        driver.get("https://github.com/egoryu");
        Thread.sleep(1000);

        driver.findElement(By.xpath("//nav/ul/li[2]/a")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//a[contains(text(),'BLPS')]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[2]/form/button/span")).click();
        Thread.sleep(1000);
        String home = driver.findElement(By.xpath("//li[3]/div/div/form/button/span")).getText();
        Assertions.assertEquals("Starred", home);
    }

    public void deleteStar(WebDriver driver) throws InterruptedException {
        driver.get("https://github.com/egoryu");
        Thread.sleep(1000);

        driver.findElement(By.xpath("//nav/ul/li[2]/a")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//a[contains(text(),'BLPS')]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//li[3]/div/div/form/button/span")).click();
        Thread.sleep(1000);
        String home = driver.findElement(By.xpath("//div[2]/form/button/span")).getText();
        Assertions.assertEquals("Star", home);
    }
    @Test
    public void checkPomTest() throws InterruptedException {
        checkPom(chromeDriver);
    }

    @Test
    public void followTest() throws InterruptedException {
        AuthTest.logIn(chromeDriver);
        follow(chromeDriver);
    }

    @Test
    public void unFollowTest() throws InterruptedException {
        unFollow(chromeDriver);
    }

    @Test
    public void addStarTest() throws InterruptedException {
        addStar(chromeDriver);
    }

    @Test
    public void qdeleteStarTest() throws InterruptedException {
        deleteStar(chromeDriver);
    }
}
