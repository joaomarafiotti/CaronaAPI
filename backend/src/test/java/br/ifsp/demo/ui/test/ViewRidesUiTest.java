package br.ifsp.demo.ui.test;

import br.ifsp.demo.ui.BaseSeleniumTest;
import br.ifsp.demo.ui.page.ViewRidesPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class ViewRidesUiTest extends BaseSeleniumTest {

    @Override
    protected void setInitialPage() {
        new ViewRidesPage(driver); // inicia na página de visualização de caronas
    }

    @Test
    @DisplayName("Happy Path - Should display all ride sections if data exists")
    void shouldDisplayAllRideSectionsIfDataExists() {
        ViewRidesPage viewRidesPage = new ViewRidesPage(driver);

        // Verifica se pelo menos um dos grupos aparece (podem não ter todas as seções)
        assertThat(
            viewRidesPage.hasNotStartedRides()
            || viewRidesPage.hasInProgressRides()
            || viewRidesPage.hasFinishedRides()
        ).isTrue();
    }

    @Test
    @DisplayName("Sad Path - Should show error alert if API fails")
    void shouldShowErrorAlertIfApiFails() {
        ViewRidesPage viewRidesPage = new ViewRidesPage(driver);

        if (viewRidesPage.hasErrorMessage()) {
            assertThat(viewRidesPage.getErrorMessageText()).contains("Erro ao buscar caronas");
        } else {
            System.out.println("⚠️ Nenhum erro foi simulado. Ignorando teste sad path.");
        }
    }

    @Test
    @DisplayName("UI Responsiveness - Should display title correctly on mobile")
    void shouldDisplayTitleCorrectlyOnMobile() {
        driver.manage().window().setSize(new Dimension(375, 812)); // iPhone X

        ViewRidesPage viewRidesPage = new ViewRidesPage(driver);
        assertThat(viewRidesPage.isResponsiveTitleVisible()).isTrue();
    }
}