package br.ifsp.demo.integration.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@Tag("UiTest")
public class BaseSeleniumTest {

    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Setup do driver usando WebDriverManager
        WebDriverManager.chromedriver().setup();

        // Opções do Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // Começar maximizado

        // Criação do driver
        driver = new ChromeDriver(options);

        // Ao inves de usar implicitWait vou sempre usar explicitWait no PageObject
        // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); (removido)

        // Navega para a página inicial, se necessário
        setInitialPage();
    }

    // Hook para ser sobrescrito nos testes
    protected void setInitialPage() {}

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
