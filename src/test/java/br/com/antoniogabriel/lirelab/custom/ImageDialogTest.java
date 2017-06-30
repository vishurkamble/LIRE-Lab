package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.app.ImageViewFactory;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.antoniogabriel.lirelab.test.TestUtils.runOnFXThread;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ImageDialogTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    private static final String IMAGE_PATH = "some/image/path";

    @Mock private ImageView imageView;
    @Mock private ImageViewFactory imageViewFactory;
    @Mock private DialogActions dialogActions;

    private ImageDialog dialog;

    @Before
    public void setUp() throws Exception {
        given(imageViewFactory.create(IMAGE_PATH)).willReturn(imageView);
        createDialog();
    }

    @Test
    public void shouldSetImageAsContentWhenInitialize() throws Exception {
        verify(dialogActions).setContent(imageView);
    }

    @Test
    public void shouldAddOkButtonWhenInitialize() throws Exception {
        verify(dialogActions).addButtonType(ButtonType.OK);
    }

    private void createDialog() {
        runOnFXThread(() -> dialog = new ImageDialog(IMAGE_PATH, imageViewFactory, dialogActions));
    }
}