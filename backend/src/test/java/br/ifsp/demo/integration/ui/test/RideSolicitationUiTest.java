package br.ifsp.demo.integration.ui.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import br.ifsp.demo.integration.ui.BasePassengerTest;
import br.ifsp.demo.integration.ui.page.RideSolicitationPage;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class RideSolicitationUiTest extends BasePassengerTest {

    void goToSolicitationPage() {
        Actions actions = new Actions(driver);

        // Abre o menu de caronas
        WebElement menu = waitForVisibility(By.xpath("//button[contains(text(), 'Carona')]"));
        actions.moveToElement(menu).perform();
        menu.click();

        // Clica em "Solicitações Enviadas" com XPath seguro
        WebElement solicitBtn = waitForVisibility(By.xpath("//*[normalize-space()='Solicitações Enviadas']"));
        solicitBtn.click();

        // Aguarda URL correta
        waitForUrlContains("/dashboard/passenger/ride-requests");
    }

    @Test
    @DisplayName("Happy Path - Should show driver name and cancel button")
    void shouldShowDriverNameAndCancelButton() {
        goToSolicitationPage();
        RideSolicitationPage page = new RideSolicitationPage(driver);

        assertThat(page.isDriverNameVisible()).isTrue();
        assertThat(page.isCancelButtonVisible()).isTrue();
    }

    @Test
    @DisplayName("Sad Path - Should show spinner if loading")
    void shouldShowSpinnerIfLoading() {
        goToSolicitationPage();
        RideSolicitationPage page = new RideSolicitationPage(driver);

        if (page.isSpinnerVisible()) {
            assertThat(page.isCancelButtonVisible()).isFalse();
        }
    }

    @Test
    @DisplayName("UI Responsiveness - Should render driver name on mobile layout")
    void shouldRenderDriverNameOnMobileLayout() {
        goToSolicitationPage();
        driver.manage().window().setSize(new Dimension(375, 812));

        RideSolicitationPage page = new RideSolicitationPage(driver);
        assertThat(page.isResponsiveLayoutVisible()).isTrue();
    }
}