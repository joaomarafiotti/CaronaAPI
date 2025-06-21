package br.ifsp.demo.integration.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.ifsp.demo.integration.ui.BasePageObject;

public class PassengerRideRequestsPage extends BasePageObject {

    private final By title = By.xpath("//*[contains(text(), 'Solicitações de Carona')]");
    private final By rideSolicitations = By.cssSelector(".ride-grid");

    public PassengerRideRequestsPage(WebDriver driver) {
        super(driver);
        waitForVisibility(title);
    }

    public boolean isTitleVisible() {
        return isVisible(title);
    }

    public boolean hasSolicitations() {
        return !driver.findElements(rideSolicitations).isEmpty();
    }
}