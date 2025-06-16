package br.ifsp.demo.integration.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.ifsp.demo.integration.ui.BasePageObject;

public class AvailableRidesPage extends BasePageObject {

    private static final String AVAILABLE_RIDES_URL = "http://localhost:5173/dashboard/passenger/rides/available";

    private final By pageTitle = By.xpath("//*[contains(text(), 'Caronas Disponíveis')]");
    private final By rideCards = By.cssSelector("div[class*='ride']"); // depende da estrutura do componente Ride

    public AvailableRidesPage(WebDriver driver) {
        super(driver);
        driver.get(AVAILABLE_RIDES_URL);
        waitForVisibility(pageTitle);
    }

    public boolean isTitleVisible() {
        return driver.findElement(pageTitle).isDisplayed();
    }

    public boolean hasRideCards() {
        return !driver.findElements(rideCards).isEmpty();
    }

    public int countRidesDisplayed() {
        return driver.findElements(rideCards).size();
    }
}
