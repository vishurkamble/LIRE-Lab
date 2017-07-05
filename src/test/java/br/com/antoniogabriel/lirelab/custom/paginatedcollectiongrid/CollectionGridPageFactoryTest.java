package br.com.antoniogabriel.lirelab.custom.paginatedcollectiongrid;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.collectiongrid.CollectionGrid;
import br.com.antoniogabriel.lirelab.custom.collectiongrid.ImageClickHandler;
import javafx.embed.swing.JFXPanel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CollectionGridPageFactoryTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    @Mock private CollectionGrid page;
    @Mock private ImageClickHandler handler;
    @Mock private Collection collection;
    @Mock private List<Image> imagesSubSet;

    @Test
    public void shouldReturnCollectionGridWithImagesSubsetForInformedPage() throws Exception {
        TestableCollectionGridPageFactory factory;
        int pageSize = 10;
        int totalImages = 95;

        factory = getFactory(pageSize, totalImages);

        factory.call(0);
        verify(collection).getImagesInRange(0, 10);
        verify(page).setImages(imagesSubSet, handler);

        factory = getFactory(pageSize, totalImages);

        factory.call(1);
        verify(collection).getImagesInRange(10, 20);
        verify(page).setImages(imagesSubSet, handler);

        factory = getFactory(pageSize, totalImages);

        factory.call(6);
        verify(collection).getImagesInRange(60, 70);
        verify(page).setImages(imagesSubSet, handler);

        factory = getFactory(pageSize, totalImages);

        factory.call(9);
        verify(collection).getImagesInRange(90, 95);
        verify(page).setImages(imagesSubSet, handler);
    }

    private TestableCollectionGridPageFactory getFactory(int pageSize, int totalImages) {
        reset(page);

        given(collection.getImagesInRange(anyInt(), anyInt())).willReturn(imagesSubSet);
        given(collection.totalImages()).willReturn(totalImages);

        return new TestableCollectionGridPageFactory(collection, pageSize, handler);
    }

    private class TestableCollectionGridPageFactory extends CollectionGridPageFactory {

        public TestableCollectionGridPageFactory(Collection collection, int pageSize, ImageClickHandler handler) {
            super(collection, pageSize, handler);
        }

        @Override
        protected CollectionGrid createCollectionGrid() {
            return page;
        }
    }
}