package br.ifsp.demo.integration.ui.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import br.ifsp.demo.integration.ui.BasePassengerTest;
import br.ifsp.demo.integration.ui.page.PassengerRidesPage;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class PassengerRidesUiTest extends BasePassengerTest {

    void goToPassengerRidesPage() {
        // Hover e clique no menu "Carona"
        Actions actions = new Actions(driver);
        WebElement caronaMenu = waitForVisibility(By.xpath("//button[contains(text(), 'Carona')]"));
        actions.moveToElement(caronaMenu).perform();
        caronaMenu.click();

        // Clicar em "Minhas Caronas"
        WebElement minhasCaronasBtn = waitForVisibility(By.xpath("//*[contains(text(), 'Minhas Caronas')]"));
        minhasCaronasBtn.click();

        // Espera URL correta
        waitForUrlContains("/dashboard/passenger/rides");
    }

    @Test
    @DisplayName("Happy Path - Should display passenger rides and title")
    void shouldDisplayPassengerRidesAndTitle() {
        goToPassengerRidesPage();
        PassengerRidesPage page = new PassengerRidesPage(driver);

        assertThat(page.isTitleVisible()).isTrue();
        assertThat(page.hasRides()).isTrue();
    }

    @Test
    @DisplayName("UI Responsiveness - Should show title on mobile layout")
    void shouldRenderOnMobile() {
        goToPassengerRidesPage();
        driver.manage().window().setSize(new Dimension(375, 812));
        PassengerRidesPage page = new PassengerRidesPage(driver);

        assertThat(page.isTitleVisible()).isTrue();
    }
}
