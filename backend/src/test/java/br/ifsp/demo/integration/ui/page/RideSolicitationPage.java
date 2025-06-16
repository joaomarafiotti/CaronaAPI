package br.ifsp.demo.integration.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.ifsp.demo.integration.ui.BasePageObject;

public class RideSolicitationPage extends BasePageObject {

    private static final String RIDE_SOLICITATION_URL = "http://localhost:5173/dashboard/passenger/solicitations";

    private final By driverName = By.xpath("//*[contains(text(), 'Nome do Motorista')]/following-sibling::*");
    private final By cancelButton = By.xpath("//button[contains(text(), 'Cancelar')]");
    private final By spinner = By.cssSelector(".chakra-spinner");

    public RideSolicitationPage(WebDriver driver) {
        super(driver);
        driver.get(RIDE_SOLICITATION_URL);
        waitForVisibility(By.xpath("//*[contains(text(), 'Solicitação')]"));
    }

    public boolean isDriverNameVisible() {
        return !driver.findElements(driverName).isEmpty();
    }

    public boolean isCancelButtonVisible() {
        return !driver.findElements(cancelButton).isEmpty();
    }

    public boolean isSpinnerVisible() {
        return !driver.findElements(spinner).isEmpty();
    }

    public boolean isResponsiveLayoutVisible() {
        return driver.findElement(driverName).isDisplayed();
    }
}
