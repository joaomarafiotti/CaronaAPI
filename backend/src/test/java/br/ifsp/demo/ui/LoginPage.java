package br.ifsp.demo.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePageObject {

    private static final String LOGIN_URL = "http://localhost:5173/login";

    // Locators
    private final By emailField = By.name("username");
    private final By passwordField = By.name("password");
    private final By submitButton = By.cssSelector("button[type='submit']");

    public LoginPage(WebDriver driver) {
        super(driver);
        driver.get(LOGIN_URL);
        // Espera ficar visivel
        waitForVisibility(emailField);
    }

    public void fillEmail(String email) {
        fillField(emailField, email);
    }

    public void fillPassword(String password) {
        fillField(passwordField, password);
    }

    public void submitLogin() {
        clickWhenClickable(submitButton);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return getPageTitle(); // Usa helper da BasePageObject
    }

    public boolean isEmailFieldVisible() {
        return driver.findElement(emailField).isDisplayed();
    }
}