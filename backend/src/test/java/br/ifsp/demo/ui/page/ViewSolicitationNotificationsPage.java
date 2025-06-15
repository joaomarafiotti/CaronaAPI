package br.ifsp.demo.ui.page;

import br.ifsp.demo.ui.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
