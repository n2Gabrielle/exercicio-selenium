package br.com.aulaselenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

class testRegister {
    private static final String BASE_URL = "https://automationexercise.com";
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage", "--window-size=1440,1200");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void registerUserAndDeleteAccount() {
        String name = "Teste Selenium UFF";
        String email = "teste." + UUID.randomUUID().toString().replace("-", "") + "@example.test";

        driver.get(BASE_URL);
        assertHomePageIsVisible();

        driver.findElement(By.cssSelector("a[href='/login']")).click();

        waitForVisible(By.xpath("//h2[contains(., 'New User Signup!')]"));

        driver.findElement(By.cssSelector("input[data-qa='signup-name']")).sendKeys(name);
        driver.findElement(By.cssSelector("input[data-qa='signup-email']")).sendKeys(email);
        driver.findElement(By.cssSelector("button[data-qa='signup-button']")).click();

        
        waitForVisible(By.xpath("//*[contains(translate(., 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'ENTER ACCOUNT INFORMATION')]"));


        driver.findElement(By.id("id_gender1")).click(); 
        driver.findElement(By.cssSelector("input[data-qa='password']")).sendKeys("SenhaForte@123");
        new Select(driver.findElement(By.cssSelector("select[data-qa='days']"))).selectByValue("10");
        new Select(driver.findElement(By.cssSelector("select[data-qa='months']"))).selectByValue("5");
        new Select(driver.findElement(By.cssSelector("select[data-qa='years']"))).selectByValue("1995");

   
        driver.findElement(By.id("newsletter")).click();
        driver.findElement(By.id("optin")).click();


        driver.findElement(By.cssSelector("input[data-qa='first_name']")).sendKeys("Teste");
        driver.findElement(By.cssSelector("input[data-qa='last_name']")).sendKeys("UFF");
        driver.findElement(By.cssSelector("input[data-qa='company']")).sendKeys("Computação UFF");
        driver.findElement(By.cssSelector("input[data-qa='address']")).sendKeys("Rua Passo da Pátria, 156");
        new Select(driver.findElement(By.cssSelector("select[data-qa='country']"))).selectByVisibleText("Canada");
        driver.findElement(By.cssSelector("input[data-qa='state']")).sendKeys("Rio de Janeiro");
        driver.findElement(By.cssSelector("input[data-qa='city']")).sendKeys("Niteroi");
        driver.findElement(By.cssSelector("input[data-qa='zipcode']")).sendKeys("24210240");
        driver.findElement(By.cssSelector("input[data-qa='mobile_number']")).sendKeys("21999999999");


        driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();

       
        waitForVisible(By.xpath("//*[contains(translate(., 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'ACCOUNT CREATED!')]"));
        driver.findElement(By.cssSelector("a[data-qa='continue-button']")).click();

  
        waitForVisible(By.xpath("//a[contains(., 'Logged in as')]"));

    
        driver.findElement(By.cssSelector("a[href='/delete_account']")).click();

     
        waitForVisible(By.xpath("//*[contains(translate(., 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'ACCOUNT DELETED!')]"));
    }

    private void assertHomePageIsVisible() {
        waitForVisible(By.xpath("//*[contains(., 'Full-Fledged practice website for Automation Engineers')]"));
    }

    private void waitForVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}