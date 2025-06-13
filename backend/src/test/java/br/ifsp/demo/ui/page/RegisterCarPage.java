package br.ifsp.demo.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.ifsp.demo.ui.BasePageObject;

public class RegisterCarPage extends BasePageObject {

    private static final String REGISTER_CAR_URL = "http://localhost:5173/dashboard/driver/cars/register";

    // Locators
    private final By brandField = By.id("brand");
    private final By modelField = By.id("model");
    private final By colorField = By.id("color");
    private final By seatsField = By.id("seats");
    private final By licensePlateField = By.id("licensePlate");
    private final By submitButton = By.cssSelector("button.auth-button");

    // Locators para mensagens de erro
    private final By formError = By.cssSelector("div.form-error");

    public RegisterCarPage(WebDriver driver) {
        super(driver);
        driver.get(REGISTER_CAR_URL);
        // Espera que o campo de marca esteja visível
        waitForVisibility(brandField);
    }

    // Preencher campos do formulário
    public void fillBrand(String brand) {
        fillField(brandField, brand);
    }

    public void fillModel(String model) {
        fillField(modelField, model);
    }

    public void fillColor(String color) {
        fillField(colorField, color);
    }

    public void fillSeats(String seats) {
        fillField(seatsField, seats);
    }

    public void fillLicensePlate(String licensePlate) {
        fillField(licensePlateField, licensePlate);
    }

    // Submit form
    public void submitForm() {
        clickWhenClickable(submitButton);
    }

    // Checar se o formulário foi enviado com sucesso
    public boolean isGeneralFormErrorVisible() {
        return !driver.findElements(formError).isEmpty();
    }

    public String getGeneralFormErrorText() {
        if (isGeneralFormErrorVisible()) {
            return getElementText(formError);
        }
        return "";
    }

    // Utilitários para verificar visibilidade dos campos
    public boolean isBrandFieldVisible() {
        return driver.findElement(brandField).isDisplayed();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
