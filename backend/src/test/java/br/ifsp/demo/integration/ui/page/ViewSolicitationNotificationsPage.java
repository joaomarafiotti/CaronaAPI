package br.ifsp.demo.integration.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.ifsp.demo.integration.ui.BasePageObject;

public class ViewSolicitationNotificationsPage extends BasePageObject {

    private final By title = By.xpath("//*[contains(text(), 'Notificações de Solicitações')]");
    //private final By solicitationText = By.xpath("//*[contains(text(), 'solicitou para se juntar à sua carona')]");
    private final By approveButton = By.xpath("//button[contains(text(), 'Aprovar')]");

    public ViewSolicitationNotificationsPage(WebDriver driver) {
        super(driver);
        waitForVisibility(title); // aguarda título da página
    }

    public boolean isTitleVisible() {
        return isVisible(title);
    }

    public boolean hasSolicitations() {
        return isVisible(approveButton);
    }
}