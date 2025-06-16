package br.ifsp.demo.integration.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import br.ifsp.demo.integration.ui.BasePageObject;

public class RegisterRidePage extends BasePageObject {

    private static final String REGISTER_RIDE_URL = "http://localhost:5173/dashboard/driver/rides/register";

    private final By startAddressField = By.id("startAddress");
    private final By endAddressField = By.id("endAddress");
    private final By departureTimeField = By.id("departureTime");
    private final By carSelectField = By.id("carSelect");
    private final By submitButton = By.cssSelector("button[type='submit'].auth-button");

    private final By formError = By.cssSelector("div.form-error");
    private final By formSuccess = By.cssSelector("div.form-success");

    public RegisterRidePage(WebDriver driver) {
        super(driver);
        driver.get(REGISTER_RIDE_URL);
        waitForVisibility(startAddressField);
    }

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

    public void submitForm() {
        clickWhenClickable(submitButton);
    }

    public boolean isFormErrorVisible() {
        return !driver.findElements(formError).isEmpty();
    }

    public String getFormErrorText() {
        if (isFormErrorVisible()) {
            return getElementText(formError);
        }
        return "";
    }

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
        return isVisible(startAddressField);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
