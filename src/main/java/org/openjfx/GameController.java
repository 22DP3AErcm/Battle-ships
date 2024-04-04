package org.openjfx;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

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

    
    Draggable draggable;

    List<Integer> shipWidths = Arrays.asList(200, 160, 160, 120, 120, 120, 80, 80, 80);
    List<Ships> ships = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        grid = new Grid(anchorPane);
        grid.centerGridPane();

        for (Integer width : shipWidths) {
            Ships ship = new Ships(0, 0, width, 40, 40, grid);
            ships.add(ship);
            grid.addShip(ship);
            gridPane.getChildren().add(ship);
            
            
            ship.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.R) {
                    Rectangle rectangle = ship.getRectangle();
                    // Clear the previous transformations
                    rectangle.getTransforms().clear();
                    if (ship.getRotation() == 0){
                        // Adjust the pivot point for the rotation to be the top-left corner of the ship
                        rectangle.getTransforms().add(new Rotate(90, 0, 0));
                        // Set isRotated to true
                        ship.setIsRotated(true);
                        ship.setRotation(1);
                    } else {
                        // Set isRotated to false
                        ship.setIsRotated(false);
                        ship.setRotation(0);
                    }
                    ship.snapToGrid(ship);
                }
            });
        
            ship.setOnMouseClicked(event -> {
                ship.requestFocus();
            });
        
            ship.setFocusTraversable(true);
        }
        
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