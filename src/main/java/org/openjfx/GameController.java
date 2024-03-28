package org.openjfx;

import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class GameController implements Initializable{
    @FXML
    private StackPane pane;
    @FXML
    private Button settingButton;
    private int button = 0;
    
    private Ships ship;

    @FXML
    private AnchorPane anchorPane;

    private Grid grid;

    @FXML
    private Pane gridPane;

    @FXML
    private Pane draggablePane;

    
    Draggable draggable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        grid = new Grid(anchorPane);
        grid.centerGridPane();

        ship = new Ships(0, 0, 80, 40, 40, grid);

        gridPane.getChildren().add(ship);
        gridPane.toFront();

    }

    @FXML
    private void panevisable() throws IOException
    {
        if (button == 0) {
            pane.setVisible(true);
            pane.toFront();
            settingButton.toFront();
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
