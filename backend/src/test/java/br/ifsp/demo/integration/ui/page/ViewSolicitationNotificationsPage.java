package br.ifsp.demo.integration.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.ifsp.demo.integration.ui.BasePageObject;

public class ViewSolicitationNotificationsPage extends BasePageObject {

    private static final String PAGE_URL = "http://localhost:5173/dashboard/driver/notifications";

    private final By title = By.xpath("//*[contains(text(), 'Notificações de Solicitações')]");
    private final By solicitationGrid = By.cssSelector(".ride-grid");

    public ViewSolicitationNotificationsPage(WebDriver driver) {
        super(driver);
        driver.get(PAGE_URL);
        waitForVisibility(title);
    }

    public boolean isTitleVisible() {
        return driver.findElement(title).isDisplayed();
    }

    public boolean hasSolicitations() {
        return !driver.findElements(solicitationGrid).isEmpty();
    }
}
