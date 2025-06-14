package br.ifsp.demo.ui.page;

import br.ifsp.demo.ui.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterRidePage extends BasePageObject {

    private static final String REGISTER_RIDE_URL = "http://localhost:5173/dashboard/driver/rides/register";

    // Locators
    private final By startAddressField = By.id("startAddress");
    private final By endAddressField = By.id("endAddress");
    private final By departureTimeField = By.id("departureTime");
    private final By carSelectField = By.id("carSelect");
    private final By submitButton = By.cssSelector("button[type='submit'].auth-button");

    // Locators para as mensagens de erro e sucesso do formulário
    private final By formError = By.cssSelector("div.form-error");
    private final By formSuccess = By.cssSelector("div.form-success");

    public RegisterRidePage(WebDriver driver) {
        super(driver);
        driver.get(REGISTER_RIDE_URL);
        // espera que o campo de endereço de partida esteja visível
        waitForVisibility(startAddressField);
    }

    // Fill methods
    public void fillStartAddress(String startAddress) {
        fillField(startAddressField, startAddress);
    }

    public void fillEndAddress(String endAddress) {
        fillField(endAddressField, endAddress);
    }

    public void fillDepartureTime(String departureTime) {
        fillField(departureTimeField, departureTime);
    }

    public void selectCar(String carId) {
        driver.findElement(carSelectField).sendKeys(carId);
    }

    // Submit form
    public void submitForm() {
        clickWhenClickable(submitButton);
    }

    // Error message
    public boolean isFormErrorVisible() {
        return !driver.findElements(formError).isEmpty();
    }

    public String getFormErrorText() {
        if (isFormErrorVisible()) {
            return getElementText(formError);
        }
        return "";
    }

    // Success message
    public boolean isFormSuccessVisible() {
        return !driver.findElements(formSuccess).isEmpty();
    }

    public String getFormSuccessText() {
        if (isFormSuccessVisible()) {
            return getElementText(formSuccess);
        }
        return "";
    }

    public boolean isStartAddressFieldVisible() {
        return driver.findElement(startAddressField).isDisplayed();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
