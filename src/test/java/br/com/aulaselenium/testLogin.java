package br.com.aulaselenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class testLogin {
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

    @ParameterizedTest(name = "Login rejeita credenciais incorretas: {0}")
    @MethodSource("invalidCredentials")
    void loginWithIncorrectEmailAndPasswordShowsError(String caseName, String email, String password) {
        driver.get(BASE_URL);
        assertHomePageIsVisible();

        driver.findElement(By.cssSelector("a[href='/login']")).click();
        waitForVisible(By.xpath("//h2[contains(., 'Login to your account')]"));

        driver.findElement(By.cssSelector("input[data-qa='login-email']")).sendKeys(email);
        driver.findElement(By.cssSelector("input[data-qa='login-password']")).sendKeys(password);
        driver.findElement(By.cssSelector("button[data-qa='login-button']")).click();

        waitForVisible(By.xpath("//*[contains(., 'Your email or password is incorrect!')]"));
    }

    private void assertHomePageIsVisible() {
        waitForVisible(By.xpath("//*[contains(., 'Full-Fledged practice website for Automation Engineers')]"));
    }

    private void waitForVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private static Stream<Arguments> invalidCredentials() {
        String maxLocalPart = repeat('a', 64);
        
        return Stream.of(
                Arguments.of("E-mail padrão não cadastrado", "gabrielletest@gmail.com", "senhaerrada123"),
                Arguments.of("E-mail com números e pontos na estrutura", "joao.silva.123@outlook.com", "senhaerrada123"),
                Arguments.of("Limite inferior de tamanho (curto)", "a@b.co", "x"),
                Arguments.of("Limite superior da parte local (64 chars)", maxLocalPart + "@example.test", "senhaerrada123"),
                Arguments.of("Variação com letras maiúsculas", "GABRIELLETEST@GMAIL.COM", "senhaerrada123"));
    }

    private static String repeat(char value, int count) {
        StringBuilder result = new StringBuilder(count);
        for (int index = 0; index < count; index++) {
            result.append(value);
        }
        return result.toString();
    }
}