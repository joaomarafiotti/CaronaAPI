package br.ifsp.demo.integration.ui.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import br.ifsp.demo.integration.ui.BasePassengerTest;
import br.ifsp.demo.integration.ui.page.PassengerRideRequestsPage;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class PassengerRideRequestsUiTest extends BasePassengerTest {

    void goToRideRequestsPage() {
        Actions actions = new Actions(driver);

        // Hover ou clique no botão "Carona"
        WebElement caronaMenu = waitForVisibility(By.xpath("//button[contains(text(), 'Carona')]"));
        actions.moveToElement(caronaMenu).perform();
        caronaMenu.click();

        // Clica em "Solicitações Enviadas"
        WebElement solicitacoesBtn = waitForVisibility(By.xpath("//*[contains(text(), 'Solicitações Enviadas')]"));
        solicitacoesBtn.click();

        waitForUrlContains("/dashboard/passenger/ride-requests");
    }

    @Test
    @DisplayName("Happy Path - Should display ride requests and title")
    void shouldDisplayRideRequestsAndTitle() {
        goToRideRequestsPage();

        PassengerRideRequestsPage page = new PassengerRideRequestsPage(driver);
        assertThat(page.isTitleVisible()).isTrue();
        assertThat(page.hasSolicitations()).isTrue();
    }

    @Test
    @DisplayName("UI Responsiveness - Should show title on mobile layout")
    void shouldRenderOnMobile() {
        goToRideRequestsPage();

        driver.manage().window().setSize(new Dimension(375, 812));

        PassengerRideRequestsPage page = new PassengerRideRequestsPage(driver);
        assertThat(page.isTitleVisible()).isTrue();
    }
}