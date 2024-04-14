package org.openjfx;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class GameController implements Initializable {
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

    // Create a map to store each ship and its grid coordinates
    Map<Ships, List<String>> shipLocations = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        grid = new Grid(anchorPane);
        grid.centerGridPane();

        for (Integer width : shipWidths) {
            Ships ship = new Ships(0, 0, width, 40, 40, grid, shipLocations);
            ships.add(ship);
            grid.addShip(ship);
            gridPane.getChildren().add(ship);

            ship.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.R) {
                    Rectangle rectangle = ship.getRectangle();
                    // Clear the previous transformations
                    rectangle.getTransforms().clear();
                    if (ship.getRotation() == 0) {
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
                    // Check if the new position is valid
                    if (ship.onShip()) {
                        // If the new position is not valid, revert the rotation
                        rectangle.getTransforms().clear();
                        if (ship.getRotation() == 0){
                            rectangle.getTransforms().add(new Rotate(90, 0, 0));
                            ship.setIsRotated(true);
                            ship.setRotation(1);
                        } else {
                            ship.setIsRotated(false);
                            ship.setRotation(0);
                        }

                    } else {
                        // If the new position is valid, update the ship's grid coordinates
                        List<String> gridCoordinates = ship.getGridCoordinates();
                        shipLocations.put(ship, gridCoordinates);
                        PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(0.5));
                        System.out.println("NEW");
                        for (Ships s : shipLocations.keySet()) {
                            System.out.println(s + " " + shipLocations.get(s));
                        }
                    }
                }
            });


            ship.setOnMouseReleased(event -> {
                ship.snapToGrid(ship);
                PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(0.5));
                pause.setOnFinished(e -> {
                    System.out.println("NEW");
                    // Call getGridCoordinates() and store the returned list in shipLocations
                    List<String> gridCoordinates = ship.getGridCoordinates();
                    shipLocations.put(ship, gridCoordinates);
                    
                    for (Ships s : shipLocations.keySet()) {
                        System.out.println(s + " " + shipLocations.get(s));
                    }
                });
                pause.play();
            });
            

            ship.setOnMouseClicked(event -> {
                ship.requestFocus();
            });

            ship.setFocusTraversable(true);
        }

        gridPane.toFront();
    }

    @FXML
    private void panevisable() throws IOException {
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
    private void goToSettings() throws IOException {
        App.setRoot("Settings");
    }

    @FXML
    public void goToAccountEdit() throws IOException {
        App.setRoot("AccountEdit");
    }
}