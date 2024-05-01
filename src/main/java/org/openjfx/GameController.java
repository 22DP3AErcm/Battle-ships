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
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GameController implements Initializable {
    String imageURL;
    
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
    public AnchorPane anchorPane;

    private Grid grid;

    @FXML
    private Pane gridPane;

    @FXML
    private Pane draggablePane;

    public static int gameStarted = 0;

    Draggable draggable;

    public static boolean isPlayerTurn;

    List<Integer> shipWidths = Arrays.asList(200, 160, 160, 120, 120, 120, 80, 80, 80);
    static List<Ships> ships = new ArrayList<>();

    // Create a map to store each ship and its grid coordinates
    public static Map<Ships, List<String>> shipLocations = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainVBox.prefWidthProperty().bind(anchorPane.widthProperty());
        startGame.setDisable(true);
        grid = new Grid(anchorPane);
        grid.centerGridPane();

        if (shipLocations.size() == 9)
        {
            drawShipsFromMap(shipLocations);
            startGame.setDisable(false);
            startGame.getStyleClass().add("RegisterButton");
            
            if (gameStarted == 0)
            {
                startGame.setOnAction(event ->{
                    System.out.println("Game Started");
                    startGame.setDisable(true);
                    startGame.setOpacity(0);
                    gameStarted = 1;
                    try {
                        App.setRoot("Enemy");
                        // Platform.runLater(() -> {
                        //     CoinToss coinToss = new CoinToss();
                        //     coinToss.coinToss();
                        // });
                    } catch (IOException e) {
                        System.out.println("Error in GameController.java");
                    }
                    
                });
            }else{
                grid.addButtonsToGrid();
                startGame.setDisable(true);
                startGame.setOpacity(0);
            }
        }
        else
        {
            shipLocations.clear();
            for (Integer width : shipWidths) {
                if (width / 40 == 2) {imageURL = "file:src\\main\\resources\\org\\openjfx\\Images\\ship2long";}
                else if (width / 40 == 3) {imageURL = "file:src\\main\\resources\\org\\openjfx\\Images\\ship3long";}
                else if (width / 40 == 4) {imageURL = "file:src\\main\\resources\\org\\openjfx\\Images\\ship4long";}
                else if (width / 40 == 5) {imageURL = "file:src\\main\\resources\\org\\openjfx\\Images\\ship5long";}
                Ships ship = new Ships(0, 0, width, 40, 40, grid, shipLocations, imageURL);
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
                }
                );
                


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
            Enemy enemy = new Enemy();
            enemy.generateEnemyShips();
            
            if (gameStarted == 0)
            {
                startGame.setOnAction(event ->{
                    System.out.println("Game Started");
                    startGame.setDisable(true);
                    startGame.setOpacity(0);
                    //grid.addButtonsToGrid();
                    isPlayerTurn = true;
                    gameStarted = 1;
                    try {
                        App.setRoot("CoinToss");
                    } catch (IOException e) {
                        System.out.println("Error in GameController.java");
                    }
                });
            }
        }
    }
    public void drawShipsFromMap(Map<Ships, List<String>> objectLocations) {
        // Iterate over the shipLocations map entries
        for (Map.Entry<Ships, List<String>> entry : objectLocations.entrySet()) {
            Ships ship = entry.getKey();
            List<String> gridCoordinates = entry.getValue();

            // Iterate over the grid coordinates of the ship
            for (String coordinate : gridCoordinates) {
                // Convert grid coordinate to pixel position
                int[] xy = ship.convertCoordinate(coordinate);
                int x = Math.round(xy[0] / 40) * 40 + (SettingsController.currentResolution[0]-360)/2 - 220;
                int y = Math.round(xy[1] / 40) * 40 + (SettingsController.currentResolution[1]-360)/2 - 120;

                // Set ship's position
                ship.setLayoutX(x);
                ship.setLayoutY(y);

                // Add the ship to the anchor pane if it's not already a child
                if (!anchorPane.getChildren().contains(ship)) {
                    anchorPane.getChildren().add(ship);
                }
            }
        }
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