package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.custom.FeatureTable;
import br.com.antoniogabriel.lirelab.custom.ViewableFeature;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.util.FeatureUtils;
import br.com.antoniogabriel.lirelab.util.ProgressDialog;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class CreateCollectionController implements Initializable {

    @FXML private TextField nameField;
    @FXML private TextField imagesDirectoryField;
    @FXML private FeatureTable featuresTable;
    @FXML private Button createButton;

    private DialogProvider dialogProvider;
    private CollectionService service;

    @Inject
    public CreateCollectionController(DialogProvider dialogProvider, CollectionService service) {
        this.dialogProvider = dialogProvider;
        this.service = service;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateTable();
        setupCreateButton();
    }

    @FXML
    void chooseImagesDirectory(ActionEvent event) {
        Window parent = getWindowFrom(event);
        File dir = dialogProvider.chooseImagesDirectory(parent);
        if (dir != null) {
            imagesDirectoryField.setText(dir.getAbsolutePath());
        }
    }

    @FXML
    void close(ActionEvent event) {
        getWindowFrom(event).hide();
    }

    @FXML
    void createCollection(ActionEvent event) {
        CreateCollectionTask task = service.getTaskToCreateCollection(
                collectionName(),
                imagesDirectory(),
                collectionFeatures()
        );

        ProgressDialog dialog = dialogProvider.getProgressDialog(
                                                    task, getWindowFrom(event));
        dialog.showAndStart();
    }

    private void populateTable() {
        featuresTable.setItems(getViewableFeatures());
    }

    private ObservableList<ViewableFeature> getViewableFeatures() {
        return FeatureUtils.toViewableFeatures(Feature.values());
    }

    private void setupCreateButton() {
        createIsDisabledWhen(nameIsEmpty().or(imagesDirectoryIsEmpty().or(noFeatureSelected())));
    }

    private void createIsDisabledWhen(ObservableBooleanValue condition) {
        createButton.disableProperty().bind(condition);
    }

    private BooleanBinding nameIsEmpty() {
        return nameField.textProperty().isEmpty();
    }

    private BooleanBinding imagesDirectoryIsEmpty() {
        return imagesDirectoryField.textProperty().isEmpty();
    }

    private BooleanBinding noFeatureSelected() {
        return featuresTable.noFeatureSelected();
    }

    private Window getWindowFrom(ActionEvent event) {
        return dialogProvider.getWindowFrom(event);
    }

    String imagesDirectory() {
        return imagesDirectoryField.getText();
    }

    List<Feature> collectionFeatures() {
        return Collections.unmodifiableList(featuresTable.getSelectedFeatures());
    }

    String collectionName() {
        return nameField.getText();
    }

    /* SHOULD BE USED ONLY BY TESTS */

    void imagesDirectory(String dir) {
        imagesDirectoryField.setText(dir);
    }

    void collectionFeatures(List<Feature> features) {
        for (ViewableFeature viewableFeature : featuresTable.getItems()) {
            if(features.contains(viewableFeature.getFeature())) {
                viewableFeature.setSelected(true);
            }
        };
    }

    void collectionName(String name) {
        nameField.setText(name);
    }

}
