package org.openjfx;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameController implements Initializable{
    @FXML
    private StackPane pane;
    @FXML
    private Button settingButton;
    private int button = 0;
    
    @FXML
    private AnchorPane anchorPane;

    private Grid grid;

    @FXML
    private Pane gridPane;

    @FXML
    private Pane draggablePane;

    private ArrayList<Ships> ships;

    Draggable draggable = new Draggable();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        grid = new Grid(anchorPane);
        grid.centerGridPane();

        Ships ship = new Ships(1, 0, 0, 30, 70, anchorPane);
        ship.setColor(Color.GREEN);
        ship.setStroke(Color.BLACK);
        ships = new ArrayList<>();
        ships.add(ship);
        ship.bringToFront();

        anchorPane.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.R && ship.isBeingDragged()) {
                ship.rotate();
            }
        });
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
