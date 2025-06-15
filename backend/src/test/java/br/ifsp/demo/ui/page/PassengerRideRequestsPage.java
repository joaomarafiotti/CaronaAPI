package br.ifsp.demo.ui.page;

import br.ifsp.demo.ui.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PassengerRideRequestsPage extends BasePageObject {

    private static final String PAGE_URL = "http://localhost:5173/dashboard/passenger/ride-requests";

    private final By title = By.xpath("//*[contains(text(), 'Solicitações de Carona')]");
    private final By rideSolicitations = By.cssSelector(".ride-grid");

    public PassengerRideRequestsPage(WebDriver driver) {
        super(driver);
        driver.get(PAGE_URL);
        waitForVisibility(title);
    }

    public boolean isTitleVisible() {
        return driver.findElement(title).isDisplayed();
    }

    public boolean hasSolicitations() {
        return !driver.findElements(rideSolicitations).isEmpty();
    }
}
