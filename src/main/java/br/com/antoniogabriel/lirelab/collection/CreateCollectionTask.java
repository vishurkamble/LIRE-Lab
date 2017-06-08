package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.lire.IndexBuilder;
import br.com.antoniogabriel.lirelab.lire.IndexCreatorCallback;
import br.com.antoniogabriel.lirelab.lire.IndexCreator;
import javafx.concurrent.Task;

import java.nio.file.Paths;
import java.util.ArrayList;

public class CreateCollectionTask extends Task<Void> implements IndexCreatorCallback, ThumbnailsCreatorCallback {
    private IndexCreator indexCreator;
    private ThumbnailsCreator thumbnailsCreator;
    private String collectionName;
    private String collectionDirectory;
    private String imagesDir;
    private ArrayList<Feature> features;

    public CreateCollectionTask() {
        this(new IndexCreator(new IndexBuilder()),
                new ThumbnailsCreator(new ThumbnailBuilder()));
    }

    public CreateCollectionTask(IndexCreator indexCreator, ThumbnailsCreator thumbnailsCreator) {
        this.indexCreator = indexCreator;
        this.thumbnailsCreator = thumbnailsCreator;
        this.indexCreator.setCallback(this);
        this.thumbnailsCreator.setCallback(this);
    }

    @Override
    protected Void call() throws Exception {
        indexCreator.create();
        thumbnailsCreator.create();
        return null;
    }

    @Override
    public void beforeAddImageToIndex(int currentImage, int totalImages, String imageFilePath) {
        updateMessage("Indexing " +
                Paths.get(imageFilePath).getFileName().toString());
    }

    @Override
    public void afterAddImageToIndex(int currentImage, int totalImages, String imageFilePath) {
        updateProgress(currentImage, totalImages);
    }

    @Override
    public void afterIndexAllImages(int totalImages) {
        updateMessage("Indexing complete!");
    }

    @Override
    public void beforeCreateThumbnail(int currentImage, int totalImages, String imagePath) {
        updateMessage("Creating thumbnail for  " +
                Paths.get(imagePath).getFileName().toString());
    }

    @Override
    public void afterCreateThumbnail(int currentImage, int totalImages, String imagePath) {
        updateProgress(currentImage, totalImages);
    }

    @Override
    public void afterCreateAllThumbnails(int totalImages) {
        updateMessage("Done!");
    }

    public static CreateCollectionTask aTask() {
        return new CreateCollectionTask();
    }

    public CreateCollectionTask createCollectionNamed(String name) {
        this.collectionName = name;
        return this;
    }

    public CreateCollectionTask inDirectory(String dir) {
        this.collectionDirectory = dir;
        indexCreator.setIndexDir(dir + "/" + collectionName + "/index");
        thumbnailsCreator.setThumbnailsDir(dir + "/" + collectionName + "/thumbnails");
        return this;
    }

    public CreateCollectionTask readImagesFrom(String imagesDir) {
        this.imagesDir = imagesDir;
        indexCreator.setImagesDir(imagesDir);
        thumbnailsCreator.setImagesDir(imagesDir);
        return this;
    }

    public CreateCollectionTask indexedBy(ArrayList<Feature> features) {
        this.features = features;
        indexCreator.setFeatures(features);
        return this;
    }
}
