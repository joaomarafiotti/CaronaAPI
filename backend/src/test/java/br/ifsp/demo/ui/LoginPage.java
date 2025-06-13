package br.ifsp.demo.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePageObject {

    private static final String LOGIN_URL = "http://localhost:5173/login";

    public LoginPage(WebDriver driver) {
        super(driver);
        driver.get(LOGIN_URL);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
    }

    public void fillEmail(String email) {
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void fillPassword(String password) {
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    public String getErrorMessage() {
        return driver.findElement(By.cssSelector(".error-message")).getText();
    }
}