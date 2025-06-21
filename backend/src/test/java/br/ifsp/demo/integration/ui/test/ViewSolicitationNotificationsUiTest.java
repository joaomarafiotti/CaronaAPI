package br.ifsp.demo.integration.ui.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import br.ifsp.demo.integration.ui.BaseDriverTest;
import br.ifsp.demo.integration.ui.page.ViewSolicitationNotificationsPage;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class ViewSolicitationNotificationsUiTest extends BaseDriverTest {

    void goToNotificationsPage() {
        Actions actions = new Actions(driver);

        // Clica no botão "Solicitações"
        WebElement menu = waitForVisibility(By.xpath("//button[contains(text(), 'Solicitações')]"));
        actions.moveToElement(menu).perform();
        menu.click();

        // Clica no item de submenu "Solicitações Recebidas"
        WebElement received = waitForVisibility(By.xpath("//*[contains(text(), 'Solicitações Recebidas')]"));
        received.click();

        // Espera a URL correta
        waitForUrlContains("/dashboard/driver/solicitations/pending");
    }

    @Test
    @DisplayName("Happy Path - Should display solicitation notifications and title")
    void shouldDisplaySolicitationNotificationsAndTitle() {
        goToNotificationsPage();
        ViewSolicitationNotificationsPage page = new ViewSolicitationNotificationsPage(driver);

        assertThat(page.isTitleVisible()).isTrue();
        assertThat(page.hasSolicitations()).isTrue();
    }

    @Test
    @DisplayName("UI Responsiveness - Should show title on mobile layout")
    void shouldRenderOnMobile() {
        goToNotificationsPage();
        driver.manage().window().setSize(new Dimension(375, 812));

        ViewSolicitationNotificationsPage page = new ViewSolicitationNotificationsPage(driver);
        assertThat(page.isTitleVisible()).isTrue();
    }
}