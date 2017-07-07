package br.com.antoniogabriel.lirelab.acceptance.custom;

import br.com.antoniogabriel.lirelab.lire.Feature;
import org.testfx.api.FxRobot;

import java.util.concurrent.TimeoutException;

import static br.com.antoniogabriel.lirelab.test_utilities.AsyncUtils.waitUntilIsVisible;

public class ChooseFeatureDialogViewObject extends FxRobot {

    public void selectFeature(Feature feature) {
        openPopup();
        clickOn(feature.name()).interrupt();
    }

    private void openPopup() {
        clickOn(".list-cell").interrupt();
    }

    public void ok() {
        clickOn("OK");
    }

    public void cancel() {
        clickOn("Cancel");
    }

    public void checkOptionsAreAvailable(Feature... features) throws TimeoutException {
        openPopup();
        for (Feature feature : features) {
            waitUntilIsVisible(feature.name());
        }
    }

    public void waitUntilViewIsVisible() throws TimeoutException {
        waitUntilIsVisible(".list-cell", "#choose-feature-dialog");
    }
}
