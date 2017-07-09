package br.com.antoniogabriel.lirelab.search;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.paginated_collection_grid.PaginatedCollectionGrid;
import br.com.antoniogabriel.lirelab.custom.single_image_grid.SingleImageGrid;
import br.com.antoniogabriel.lirelab.custom.statusbar.StatusBar;
import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.embed.swing.JFXPanel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class SearchViewControllerTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    @Mock private PaginatedCollectionGrid collectionGrid;
    @Mock private SingleImageGrid queryGrid;
    @Mock private CollectionService service;
    @Mock private StatusBar statusBar;
    @Mock private SearchViewController.RunQueryTask queryTask;

    @InjectMocks private SearchViewController controller = new TestableSearchViewController();

    @Test
    public void shouldShowCollectionAndAddImageToQueryPaneWhenClicked() throws Exception {
        Collection collection = new Collection();

        controller.startSearchSession(collection, CEDD);

        verify(collectionGrid).setCollection(eq(collection),
                any(SearchViewController.SetImageToGridClickHandler.class));
    }

    @Test
    public void shouldSetStatusMessageWhenStartSearchSession() throws Exception {
        Collection collection = new Collection();
        Feature feature = CEDD;

        controller.startSearchSession(collection, feature);

        verify(statusBar).setSearchStatusInfo(collection, feature);
    }

    @Test
    public void shouldBindStatusBarProgressToQueryTaskWhenRunQuery() throws Exception {
        controller.runQuery(new Collection(), CEDD, new Image("", ""));

        verify(statusBar).bindProgressTo(queryTask);
    }

    @Test
    public void shouldSetQueryChangeListenerToQueryGrid() throws Exception {
        Collection collection = new Collection();

        controller.startSearchSession(collection, CEDD);

        verify(queryGrid).setOnChange(any(SearchViewController.ImageChangeListenerImpl.class));
    }

    private class TestableSearchViewController extends SearchViewController {

        public TestableSearchViewController() {
            super(service);
        }

        @Override
        protected SearchViewController.RunQueryTask createQueryTask(Collection collection, Feature feature, Image queryImage) {
            return queryTask;
        }
    }
}