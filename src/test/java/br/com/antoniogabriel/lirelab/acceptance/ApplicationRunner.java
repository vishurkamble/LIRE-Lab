package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.acceptance.custom.ChooseFeatureDialogViewObject;
import br.com.antoniogabriel.lirelab.lire.Feature;

import java.util.concurrent.TimeoutException;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;


public class ApplicationRunner {

    private CollectionHelper collectionHelper;

    public ApplicationRunner(CollectionHelper collectionHelper) {
        this.collectionHelper = collectionHelper;
    }

    public void createCollection(String collectionName,
                                 String imagesDirectory,
                                 Feature... features) throws TimeoutException {

        AppViewObject appView = new AppViewObject();
        CreateCollectionViewObject createView = appView.createCollection();

        createView
                .writeName(collectionName)
                .writeImagesDirectory(imagesDirectory)
                .selectFeatures(features);

        ProgressDialogViewObject progressView = createView.create();

        progressView
                //indexing images
                .checkProgressMark(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
                //creating thumbnails
                .checkProgressMark(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
                .checkOkIsEnabledWhenFinish()
                .ok();

        collectionHelper.checkCollectionExists(collectionName);

        appView.checkCollectionIsListed(collectionName);
    }

    public void viewCollection(String collection) throws TimeoutException, InterruptedException {
        CollectionTreeViewObject listView = new CollectionTreeViewObject();
        CollectionGridViewObject gridView = new CollectionGridViewObject();

        listView.selectCollection(collection);

        gridView.checkImagesAreVisible( "14474347006_99aa0fd981_k",
                                        "16903390174_1d670a5849_h",
                                        "17099294578_0ba4068bad_k",
                                        "17338370170_1e620bfb18_h",
                                        "17525978165_86dc26e8cb_h",
                                        "19774866363_757555901c_k",
                                        "25601366680_b57441bb52_k",
                                        "25601374660_78e6a9bba8_k",
                                        "26487616294_b22b87133e_k",
                                        "26489383923_98d419eb0d_k");


        ImageDialogViewObject imageView = gridView.selectImage("14474347006_99aa0fd981_k");

        imageView.checkImageIsDisplayed("14474347006_99aa0fd981_k");

        imageView.close();
    }

    public void searchCollection(String collection) {
        AppViewObject appView = new AppViewObject();
        CollectionTreeViewObject listView = new CollectionTreeViewObject();
        ChooseFeatureDialogViewObject chooseView = new ChooseFeatureDialogViewObject();

        listView.selectCollection(collection);

        SearchViewObject searchView = appView.search();

        chooseView.selectFeature(CEDD);
        chooseView.ok();

//        searchView.checkImagesAreVisible( "14474347006_99aa0fd981_k",
//                                            "16903390174_1d670a5849_h",
//                                            "17099294578_0ba4068bad_k",
//                                            "17338370170_1e620bfb18_h",
//                                            "17525978165_86dc26e8cb_h",
//                                            "19774866363_757555901c_k",
//                                            "25601366680_b57441bb52_k",
//                                            "25601374660_78e6a9bba8_k",
//                                            "26487616294_b22b87133e_k",
//                                            "26489383923_98d419eb0d_k");
//
//        searchView.selectImage("14474347006_99aa0fd981_k");
//
//        searchView.checkQueryIs("14474347006_99aa0fd981_k");
//
//        searchView.checkImagesAreSortedFor("14474347006_99aa0fd981_k");
    }
}
