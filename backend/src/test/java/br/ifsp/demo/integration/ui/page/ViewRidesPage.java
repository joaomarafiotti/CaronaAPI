package br.ifsp.demo.integration.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.ifsp.demo.integration.ui.BasePageObject;

public class ViewRidesPage extends BasePageObject {

    private static final String VIEW_RIDES_URL = "http://localhost:5173/dashboard/driver/rides/view";

    private final By notStartedTitle = By.xpath("//*[contains(text(),'Caronas não iniciadas')]");
    private final By inProgressTitle = By.xpath("//*[contains(text(),'Em andamento')]");
    private final By finishedTitle = By.xpath("//*[contains(text(),'Caronas encerradas')]");

    private final By errorAlert = By.cssSelector(".chakra-alert");

    public ViewRidesPage(WebDriver driver) {
        super(driver);
        driver.get(VIEW_RIDES_URL);
    }

    public boolean hasNotStartedRides() {
        return !driver.findElements(notStartedTitle).isEmpty();
    }

    public boolean hasInProgressRides() {
        return !driver.findElements(inProgressTitle).isEmpty();
    }

    public boolean hasFinishedRides() {
        return !driver.findElements(finishedTitle).isEmpty();
    }

    public boolean hasErrorMessage() {
        return !driver.findElements(errorAlert).isEmpty();
    }

    public String getErrorMessageText() {
        return getElementText(errorAlert);
    }

    public boolean isResponsiveTitleVisible() {
        return driver.findElement(notStartedTitle).isDisplayed();
    }
}
