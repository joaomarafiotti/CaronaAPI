package br.ifsp.demo.integration.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.ifsp.demo.integration.ui.BasePageObject;

public class PassengerRidesPage extends BasePageObject {

    private static final String PAGE_URL = "http://localhost:5173/dashboard/passenger/rides";

    private final By title = By.xpath("//*[contains(text(), 'Minhas Caronas')]");
    private final By ridesGrid = By.cssSelector(".ride-grid");

    public PassengerRidesPage(WebDriver driver) {
        super(driver);
        driver.get(PAGE_URL);
        waitForVisibility(title);
    }

    public boolean isTitleVisible() {
        return isVisible(title);
    }

    public boolean hasRides() {
        return !driver.findElements(ridesGrid).isEmpty();
    }
}
