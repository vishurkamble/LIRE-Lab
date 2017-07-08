package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.acceptance.CollectionHelper;
import br.com.antoniogabriel.lirelab.util.CollectionUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.test_utilities.TestPaths.TEST_IMAGES;
import static br.com.antoniogabriel.lirelab.test_utilities.TestPaths.TEST_ROOT;
import static br.com.antoniogabriel.lirelab.test_utilities.TestUtils.*;
import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CollectionRepositoryTest {

    private static final Collection COLLECTION1 = collection("Collection1", TEST_IMAGES, CEDD);
    private static final Collection COLLECTION2 = collection("Collection2", TEST_IMAGES, CEDD);

    private static final PathResolver RESOLVER = new PathResolver(TEST_ROOT);
    private static final CollectionHelper COLLECTION_HELPER = new CollectionHelper(RESOLVER);
    private static final CollectionUtils COLLECTION_UTILS = new CollectionUtils(RESOLVER);

    private CollectionRepository repository = new CollectionRepository(RESOLVER, COLLECTION_UTILS);

    @BeforeClass
    public static void createCollections() throws Exception {
        runOnFxThreadAndWait(() -> {
            COLLECTION_HELPER.createRealCollection(COLLECTION1);
            COLLECTION_HELPER.createRealCollection(COLLECTION2);
        });
    }

    @AfterClass
    public static void deleteCollections() throws Exception {
        runOnFxThreadAndWait(() -> {
            COLLECTION_HELPER.deleteCollection(COLLECTION1);
            COLLECTION_HELPER.deleteCollection(COLLECTION2);

            deleteWorkDirectory(RESOLVER);
        });
    }

    @Test
    public void shouldReturnEmptyCollectionListWhenCollectionsDirectoryDonExist() throws Exception {
        deleteWorkDirectory(RESOLVER);

        assertTrue(repository.getCollections().isEmpty());

        createCollections();

        assertFalse(repository.getCollections().isEmpty());
    }

    @Test
    public void shouldReturnCollectionsFromDisk() throws Exception {
        List<Collection> collections = repository.getCollections();

        assertThat(collections.size(), is(2));
        assertTrue(collections.contains(COLLECTION1));
        assertTrue(collections.contains(COLLECTION2));
    }

    @Test
    public void shouldReturnCollectionsWithImagesPaths() throws Exception {
        List<Collection> collections = repository.getCollections();

        Collection collection = collections.get(0);

        String imagesDir = collection.getImagesDirectory();
        List<String> paths = collection.getImagePaths();

        assertThat(paths.size(), is(10));

        assertTrue(paths.contains(imagesDir + "14474347006_99aa0fd981_k.jpg"));
        assertTrue(paths.contains(imagesDir + "16903390174_1d670a5849_h.jpg"));
        assertTrue(paths.contains(imagesDir + "17099294578_0ba4068bad_k.jpg"));
        assertTrue(paths.contains(imagesDir + "17338370170_1e620bfb18_h.jpg"));
        assertTrue(paths.contains(imagesDir + "17525978165_86dc26e8cb_h.jpg"));
        assertTrue(paths.contains(imagesDir + "19774866363_757555901c_k.jpg"));
        assertTrue(paths.contains(imagesDir + "25601366680_b57441bb52_k.jpg"));
        assertTrue(paths.contains(imagesDir + "25601374660_78e6a9bba8_k.jpg"));
        assertTrue(paths.contains(imagesDir + "26487616294_b22b87133e_k.jpg"));
        assertTrue(paths.contains(imagesDir + "26489383923_98d419eb0d_k.jpg"));
    }

    @Test
    public void shouldReturnCollectionsWithThumbnailsPaths() throws Exception {
        List<Collection> collections = repository.getCollections();

        Collection collection = collections.get(0);

        String thumbnailsDir = RESOLVER.getThumbnailsDirectoryPath(collection.getName()) + "/";
        List<String> paths = collection.getThumbnailPaths();

        assertThat(paths.size(), is(10));

        assertTrue(paths.contains(thumbnailsDir + "14474347006_99aa0fd981_k.thumbnail.jpg"));
        assertTrue(paths.contains(thumbnailsDir + "16903390174_1d670a5849_h.thumbnail.jpg"));
        assertTrue(paths.contains(thumbnailsDir + "17099294578_0ba4068bad_k.thumbnail.jpg"));
        assertTrue(paths.contains(thumbnailsDir + "17338370170_1e620bfb18_h.thumbnail.jpg"));
        assertTrue(paths.contains(thumbnailsDir + "17525978165_86dc26e8cb_h.thumbnail.jpg"));
        assertTrue(paths.contains(thumbnailsDir + "19774866363_757555901c_k.thumbnail.jpg"));
        assertTrue(paths.contains(thumbnailsDir + "25601366680_b57441bb52_k.thumbnail.jpg"));
        assertTrue(paths.contains(thumbnailsDir + "25601374660_78e6a9bba8_k.thumbnail.jpg"));
        assertTrue(paths.contains(thumbnailsDir + "26487616294_b22b87133e_k.thumbnail.jpg"));
        assertTrue(paths.contains(thumbnailsDir + "26489383923_98d419eb0d_k.thumbnail.jpg"));
    }

    @Test
    public void shouldReturnCollectionsWithImagesInfo() throws Exception {
        List<Collection> collections = repository.getCollections();

        Collection collection = collections.get(0);

        List<Image> images = collection.getImages();
        String imagesDir = collection.getImagesDirectory();
        String thumbnailsDir = RESOLVER.getThumbnailsDirectoryPath(collection.getName()) + "/";

        assertThat(images.size(), is(10));

        images.contains(new Image(imagesDir + "14474347006_99aa0fd981_k.jpg", thumbnailsDir + "14474347006_99aa0fd981_k.thumbnail.jpg"));
        images.contains(new Image(imagesDir + "16903390174_1d670a5849_h.jpg", thumbnailsDir + "16903390174_1d670a5849_h.thumbnail.jpg"));
        images.contains(new Image(imagesDir + "17099294578_0ba4068bad_k.jpg", thumbnailsDir + "17099294578_0ba4068bad_k.thumbnail.jpg"));
        images.contains(new Image(imagesDir + "17338370170_1e620bfb18_h.jpg", thumbnailsDir + "17338370170_1e620bfb18_h.thumbnail.jpg"));
        images.contains(new Image(imagesDir + "17525978165_86dc26e8cb_h.jpg", thumbnailsDir + "17525978165_86dc26e8cb_h.thumbnail.jpg"));
        images.contains(new Image(imagesDir + "19774866363_757555901c_k.jpg", thumbnailsDir + "19774866363_757555901c_k.thumbnail.jpg"));
        images.contains(new Image(imagesDir + "25601366680_b57441bb52_k.jpg", thumbnailsDir + "25601366680_b57441bb52_k.thumbnail.jpg"));
        images.contains(new Image(imagesDir + "25601374660_78e6a9bba8_k.jpg", thumbnailsDir + "25601374660_78e6a9bba8_k.thumbnail.jpg"));
        images.contains(new Image(imagesDir + "26487616294_b22b87133e_k.jpg", thumbnailsDir + "26487616294_b22b87133e_k.thumbnail.jpg"));
        images.contains(new Image(imagesDir + "26489383923_98d419eb0d_k.jpg", thumbnailsDir + "26489383923_98d419eb0d_k.thumbnail.jpg"));
    }

    @Test
    public void shouldReturnCollectionFromDisk() throws Exception {

        Collection collection = repository.getCollection(COLLECTION1.getName());

        assertThat(collection.getName(), equalTo(COLLECTION1.getName()));
        assertThat(collection.getImages().size(), is(10));
        assertTrue(collection.getFeatures().contains(CEDD));
    }
}
