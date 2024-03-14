package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class GameController {
    @FXML
    private Pane pane;

    private int button = 0;


    @FXML
    private void panevisable() throws IOException
    {
        if (button == 0) {
            pane.setVisible(false);
            button = 1;
        } else {
            pane.setVisible(true);
            button = 0;
        }
    }

    @FXML
    private void goToSettings() throws IOException
    {
        App.setRoot("Settings");
    }
}
