package br.ifsp.demo.integration.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

@Tag("UiTest")
public class BaseSeleniumTest {

    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        // Define diretório de perfil do Chrome para persistir localStorage/token
        String userDataDir = System.getProperty("java.io.tmpdir") + File.separator + "selenium-profile";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("user-data-dir=" + userDataDir);

        driver = new ChromeDriver(options);

        // Importante: não limpar cookies, para manter o localStorage/token entre páginas
        // driver.manage().deleteAllCookies();

        setInitialPage(); // Hook para subclasses definirem a página inicial
    }

    protected void setInitialPage() {}

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void waitForUrlContains(String partialUrl) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.urlContains(partialUrl));
    }

    protected WebElement waitForVisibility(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
