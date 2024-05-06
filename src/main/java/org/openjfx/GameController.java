package org.openjfx;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GameController implements Initializable {
    String imageURL;
    
    public static String cameFrom;

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

    // List of ship widths
    List<Integer> shipWidths = Arrays.asList(200, 160, 160, 120, 120, 120, 80, 80, 80);
    static List<Ships> ships = new ArrayList<>();

    // Create a map to store each ship and its grid coordinates
    public static Map<Ships, List<String>> shipLocations = new HashMap<>();

    // Method for initializing the game scene with the grid and player ships
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainVBox.prefWidthProperty().bind(anchorPane.widthProperty());
        startGame.setDisable(true);
        grid = new Grid(anchorPane);
        grid.centerGridPane();

        // if shipLocations is 9, draw the ships from the map
        if (shipLocations.size() == 9)
        {
            drawShipsFromMap(shipLocations);
            startGame.setDisable(false);
            startGame.getStyleClass().add("RegisterButton");
            
            // Checks if the game has started
            if (gameStarted == 0)
            {   
                // If the startGame button is clicked, starts the game
                startGame.setOnAction(event ->{
                    startGame.setDisable(true);
                    startGame.setOpacity(0);
                    gameStarted = 1;
                    // Goes to the CoinToss scene
                    try {
                        App.setRoot("CoinToss");
                    } catch (IOException e) {
                        System.out.println("Error in GameController.java");
                    }
                    
                });
            }else{
                Bullets bullets = new Bullets(anchorPane);
                // Adds all shots to the scene
                bullets.addBullet();
                startGame.setDisable(true);
                startGame.setOpacity(0);
                // Adds buttons to the grid
                grid.addButtonsToGrid();
                enemyTurn();
                
            }
        }
        // If shipLocations is not 9, draw the ships from the list of ship widths
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
                    
                    if (ship.isWithinGrid()) {
                        List<String> gridCoordinates = ship.getGridCoordinates();
                        shipLocations.put(ship, gridCoordinates);
                    }
                }
                );
                
                // When the ship is released, snap it to the grid
                ship.setOnMouseReleased(event -> {
                    ship.snapToGrid(ship);

                    // If the ship is within the grid, add it to the shipLocations map
                    if (ship.isWithinGrid()) {
                        List<String> gridCoordinates = ship.getGridCoordinates();
                        shipLocations.put(ship, gridCoordinates);
                    }

                    // If the shipLocations map has 9 ships, enable the startGame button
                    if (shipLocations.size() == 9) {
                        startGame.setDisable(false);
                        startGame.getStyleClass().add("RegisterButton");
                    }
                });
            }
            // Generates enemy ships 
            Enemy enemy = new Enemy();
            enemy.generateEnemyShips();
            
            // Checks if the game has started
            if (gameStarted == 0)
            {
                // If the startGame button is clicked, starts the game
                startGame.setOnAction(event ->{
                    startGame.setDisable(true);
                    startGame.setOpacity(0);
                    //grid.addButtonsToGrid();
                    isPlayerTurn = true;
                    gameStarted = 1;
                    // Goes to the CoinToss scene
                    try {
                        App.setRoot("CoinToss");
                    } catch (IOException e) {
                        System.out.println("Error in GameController.java");
                    }
                });
            }
        }
    }

    // Method for drawing ships from the map
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
    
    
    // Method for making the settings pane visible
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

    // Method for going to the settings scene
    @FXML
    private void goToSettings() throws IOException {
        App.setRoot("Settings");
        cameFrom = "Game";
    }

    // Method for going to the account edit scene
    @FXML
    public void goToAccountEdit() throws IOException {
        if (LoginController.account.equals("a, a, a")) {
            App.setRoot("AccountEditAdmin");
            cameFrom = "Game";
        }
        else
        {
            App.setRoot("AccountEdit");
        }
    }

    Random random = new Random();
    public static List<String> hitCordinates = new ArrayList<>();
    String coordinates = "";
    int x;
    int y;
    // Method for the enemy's turn
    public void enemyTurn() {
        // Create a new instance of the Bullets class
        Bullets bullets = new Bullets(anchorPane);
        // If the game is over, return
        if (bullets.gameOver){
            return;
        }
        
        // If the enemy has not hit a ship, shoot randomly
        if (!bullets.isHit){
            x = random.nextInt(9) + 1;
            y = random.nextInt(9) + 1;
            coordinates = String.valueOf((char) (x + 64)) + y;

            // If the coordinates have already been shot, generate new coordinates
            while (bullets.AllShots.contains(coordinates) && bullets.bulletsShotEnemy.containsKey(coordinates) && x < 10 && x > 0 && y < 10 && y > 0){
                x = random.nextInt(9) + 1;
                y = random.nextInt(9) + 1;
                coordinates = String.valueOf((char) (x + 64)) + y;
            }

            // Add the coordinates to the list of all shots
            bullets.AllShots.add(coordinates);
            // Check if the ship is shot
            bullets.checkIfShipIsShot(coordinates);

        }
        // If the enemy has hit a ship, shoot around the hit coordinates
        else{
            String[] hit = hitCordinates.get(0).split("");
            x = (int) hit[0].charAt(0) - 64;
            y = Integer.parseInt(hit[1]);

            // Shoot around the hit coordinates
            if (x + 1 < 10 && x + 1 > 0 && !bullets.AllShots.contains(String.valueOf((char) (x + 1 + 64)) + y) && x + 1 > 0 && x + 1 < 10){
                coordinates = String.valueOf((char) (x + 1 + 64)) + String.valueOf(y);
            }else if (x - 1 > 0 && x - 1 < 10 && !bullets.AllShots.contains(String.valueOf((char) (x - 1 + 64)) + y) && x - 1 > 0 && x - 1 < 10){
                coordinates = String.valueOf((char) (x - 1 + 64)) + String.valueOf(y);
            }else if (y + 1 < 10 && y + 1 > 0 && !bullets.AllShots.contains(String.valueOf((char) (x + 64)) + (y + 1)) && y + 1 > 0 && y + 1 < 10){
                coordinates = String.valueOf((char) (x + 64)) + String.valueOf(y + 1);
            }else if (y - 1 > 0 && y - 1 < 10 && !bullets.AllShots.contains(String.valueOf((char) (x + 64)) + (y - 1)) && y - 1 > 0 && y - 1 < 10){
                coordinates = String.valueOf((char) (x + 64)) + String.valueOf(y - 1);
            }
            // If all the coordinates around the hit coordinates have been shot, shoot randomly
            else{
                x = random.nextInt(9) + 1;
                y = random.nextInt(9) + 1;
                coordinates = String.valueOf((char) (x + 64)) + String.valueOf(y);
                
                // If the coordinates have already been shot, generate new coordinates
                while (bullets.AllShots.contains(coordinates) && bullets.bulletsShotEnemy.containsKey(coordinates) && x < 10 && x > 0 && y < 10 && y > 0){
                    x = random.nextInt(9) + 1;
                    y = random.nextInt(9) + 1;
                    coordinates = String.valueOf((char) (x + 64)) + String.valueOf(y);
                }
            }
            // Add the coordinates to the list of all shots
            bullets.AllShots.add(coordinates);
            // Check if the ship is shot
            bullets.checkIfShipIsShot(coordinates);
        }

        // If hit is true enemy shoots again
        PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(0.6));
        pause.setOnFinished(event -> {
            if (bullets.isHit && !bullets.gameOver){
                hitCordinates.clear();
                hitCordinates.add(coordinates);
                enemyTurn();
            }
        });
        pause.play();
    }
}