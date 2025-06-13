package br.ifsp.demo.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePageObject {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public BasePageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // default wait de 10s
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    // Wait até um elemento estar visível
    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Clicar quando estiver clicável
    protected void clickWhenClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    // Preencher campo (espera visibilidade)
    protected void fillField(By locator, String text) {
        WebElement field = waitForVisibility(locator);
        field.clear();
        field.sendKeys(text);
    }

    // Obter texto de um elemento (espera visibilidade)
    protected String getElementText(By locator) {
        return waitForVisibility(locator).getText();
    }
}
