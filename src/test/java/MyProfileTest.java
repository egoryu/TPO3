import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class MyProfileTest {
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

    public void rename(WebDriver driver) throws InterruptedException {
        String name = "Egor" + (int) (Math.random() * 100);
        driver.findElement(By.xpath("//img")).click();
        driver.findElement(By.xpath("//span[contains(.,'Your profile')]")).click();
        driver.findElement(By.xpath("//button[contains(.,'Edit profile')]")).click();
        driver.findElement(By.xpath("//waiting-form/form/div/input")).click();
        driver.findElement(By.xpath("//waiting-form/form/div/input")).clear();
        driver.findElement(By.xpath("//waiting-form/form/div/input")).sendKeys(name);
        driver.findElement(By.xpath("//waiting-form/form/div/input")).sendKeys(Keys.ENTER);

        Thread.sleep(1000);
        String home = driver.findElement(By.xpath("//h1/span")).getText();
        Assertions.assertEquals(name, home);
    }

    public void addRepo(WebDriver driver) throws InterruptedException {
        driver.get("https://github.com/Keker14?tab=repositories");
        Thread.sleep(1000);

        driver.findElement(By.xpath("(//a[contains(@href, '/new')])[6]")).click();
        driver.findElement(By.xpath("//span/input")).click();
        driver.findElement(By.xpath("//span/input")).sendKeys("kektest");
        driver.findElement(By.xpath("//fieldset/div/div[2]/div/input")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[contains(.,'Create repository')]")).click();
        Thread.sleep(1000);

        driver.get("https://github.com/Keker14?tab=repositories");
        Thread.sleep(1000);
        String home = driver.findElement(By.xpath("//div[@id='user-repositories-list']/ul/li/div/div/h3/a")).getText();
        Assertions.assertEquals("kektest", home);
    }

    public void removeRepo(WebDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//input[@id='dashboard-repos-filter-left']")).click();
        driver.findElement(By.xpath("//input[@id='dashboard-repos-filter-left']")).sendKeys("kektest");
        driver.findElement(By.xpath("//input[@id='dashboard-repos-filter-left']")).sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//li/div/div/a")).click();
        driver.findElement(By.xpath("//a[@id='settings-tab']")).click();
        driver.findElement(By.xpath("//form/button/span/span")).click();

        driver.findElement(By.xpath("//li[5]/div/form/dialog-helper/dialog/div[2]/div/button/span/span")).click();
        driver.findElement(By.xpath("//div[2]/div/div/button/span")).click();
        driver.findElement(By.xpath("//primer-text-field/div/input")).click();
        driver.findElement(By.xpath("//primer-text-field/div/input")).sendKeys("Keker14/kektest");
        driver.findElement(By.xpath("//div[2]/div/div/button/span")).click();
        Thread.sleep(1000);

        String home = driver.findElement(By.xpath("//div[@id='user-repositories-list']/div/div/h2")).getText();
        Assertions.assertEquals("Keker14 doesn’t have any public repositories yet.", home);
    }

    @Test
    public void renameTest() throws InterruptedException {
        rename(chromeDriver);
    }

    @Test
    public void removeRepoTest() throws InterruptedException {
        removeRepo(chromeDriver);
    }

    @Test
    public void addRepoTest() throws InterruptedException {
        AuthTest.logIn(chromeDriver);
        addRepo(chromeDriver);
    }
}
