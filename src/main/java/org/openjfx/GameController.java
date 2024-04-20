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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GameController implements Initializable {
    @FXML
    private StackPane pane;
    @FXML
    private Button settingButton;
    private int button = 0;
    @FXML
    private VBox mainVBox;
    @FXML
    private Button startGame;
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
    private static Map<Ships, List<String>> shipLocations = new HashMap<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainVBox.prefWidthProperty().bind(anchorPane.widthProperty());
        startGame.setDisable(true);
        grid = new Grid(anchorPane);
        grid.centerGridPane();

        for (Integer width : shipWidths) {
            Ships ship = new Ships(0, 0, width, 40, 40, grid, shipLocations);
            ships.add(ship);
            grid.addShip(ship);
            anchorPane.getChildren().add(ship);
            
            ship.setOnKeyPressed(event -> {
                ship.rotateShip(event);
                
                // Update the shipLocations map
                List<String> gridCoordinates = ship.getGridCoordinates();
                shipLocations.put(ship, gridCoordinates);

                System.out.println("New");
                //pritnts all ships in shipLocations
                for (Map.Entry<Ships, List<String>> entry : shipLocations.entrySet()) {
                    Ships key = entry.getKey();
                    List<String> value = entry.getValue();
                    System.out.println(key + " " + value);
                }
            });
            


            ship.setOnMouseReleased(event -> {
                ship.snapToGrid(ship);

                if (ship.isWithinGrid()) {
                    List<String> gridCoordinates = ship.getGridCoordinates();
                    shipLocations.put(ship, gridCoordinates);

                    //pritnts all ships in shipLocations
                    System.out.println("New");
                    for (Map.Entry<Ships, List<String>> entry : shipLocations.entrySet()) {
                        Ships key = entry.getKey();
                        List<String> value = entry.getValue();
                        System.out.println(key + " " + value);
                    }
                }

                if (shipLocations.size() == 9) {
                    startGame.setDisable(false);
                    startGame.getStyleClass().add("RegisterButton");
                }
            });
        }

        startGame.setOnAction(event ->{
            System.out.println("Game Started");
        });
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
        if (LoginController.account.equals("a, a, a")) {
            App.setRoot("AccountEditAdmin");
        }
        else
        {
            System.out.println(LoginController.account);
            App.setRoot("AccountEdit");
        }
    }
}