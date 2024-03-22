package org.openjfx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GameController implements Initializable{
    @FXML
    private StackPane pane;

    private int button = 0;

    @FXML
    private Pane draggablePane;

    Draggable draggable = new Draggable();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        draggable.makeDraggable(draggablePane);
    }

    @FXML
    private void panevisable() throws IOException
    {
        if (button == 0) {
            pane.setVisible(true);
            button = 1;
        } else {
            pane.setVisible(false);
            button = 0;
        }
    }

    @FXML
    private void goToSettings() throws IOException
    {
        App.setRoot("Settings");
    }

}
