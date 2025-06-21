package br.ifsp.demo.integration.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.ifsp.demo.integration.ui.BasePageObject;

public class RideSolicitationPage extends BasePageObject {

    private final By driverName = By.xpath("//*[contains(text(), 'Nome do Motorista')]/following-sibling::*");
    private final By cancelButton = By.xpath("//button[contains(text(), 'Cancelar')]");
    private final By spinner = By.cssSelector(".chakra-spinner");
    private final By pageIndicator = By.xpath("//*[contains(text(), 'Solicitações de Carona')]"); // <- corrigido

    public RideSolicitationPage(WebDriver driver) {
        super(driver);
        waitForVisibility(pageIndicator); // espera o título correto
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
        return isVisible(driverName);
    }
}