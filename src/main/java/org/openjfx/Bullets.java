package org.openjfx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Bullets {
    // Store the coordinates of the shots fired by the player and the enemy
    public static Map<String, Boolean> bulletsShotPlayer= new HashMap<>();
    public static Map<String, Boolean> bulletsShotEnemy= new HashMap<>();
    // Create instances of the Enemy, GameController and Bullets classes
    Enemy enemy = new Enemy();
    GameController gameController = new GameController();

    private AnchorPane anchorPane;
    private Rectangle rectangle;
    String imageURL;

    // Create a boolean variable to store the game state
    public static boolean gameOver = false;

    // For enemy algorithm
    public static boolean isHit = false;

    public static boolean isPauseInProgress = false;

    // Create a constructor for the Bullets class
    public Bullets(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }
    
    // Create a method to check if a ship is shot
    public void checkIfShipIsShot(String coordinates){
        // If a pause is in progress, do nothing
        if (isPauseInProgress) {
            return;
        }
        // If the game is over, do nothing
        if (gameOver) {
            return;
        }
        Map<String, Boolean> bulletsShot = gameController.isPlayerTurn ? bulletsShotPlayer : bulletsShotEnemy;
    
        // If a shot has already been fired at these coordinates, do nothing
        if (bulletsShot.containsKey(coordinates)) {
            return;
        }
        
        // Check if the shot hit a ship
        Map<?, List<String>> ships = gameController.isPlayerTurn ? Enemy.enemyShips : GameController.shipLocations;
        for (Map.Entry<?, List<String>> entry : ships.entrySet()) {
            List<String> shipCoordinates = new ArrayList<>(entry.getValue());
            if (shipCoordinates.contains(coordinates)) {
                // If the shot hit a ship, draw a red circle
                drawCircle(coordinates, Color.RED);
                // Store the shot coordinates
                bulletsShot.put(coordinates, true);

                if (gameController.isPlayerTurn) {
                    // Check if the ship is destroyed for players turn
                    EnemyShips();
                }else{
                    isHit = true;
                }

                // Check if the game is over
                gameOver();
                return;
            }
        }

        // If the shot missed the ship, draw a blue circle
        drawCircle(coordinates, Color.BLUE);
        // Store the shot coordinates
        bulletsShot.put(coordinates, false);
        
        isPauseInProgress = true;

        if (!gameController.isPlayerTurn) {
            isHit = false;
        }

        // Check if the game is over
        gameOver();

        // Create a pause transition for changing the scene
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            isPauseInProgress = false;

            if (gameController.isPlayerTurn && !gameOver) {
                // If it is the player's turn and the game is not over, change the scene to the enemy's turn
                gameController.isPlayerTurn = false;
                try {
                    App.setRoot("Game");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (!gameController.isPlayerTurn && !gameOver){
                // If it is the enemy's turn and the game is not over, change the scene to the player's turn
                gameController.isPlayerTurn = true;
                try {
                    App.setRoot("Enemy");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        pause.play();
    }
    
    // Create a method to draw a circle on the anchor pane
    public void drawCircle(String coordinates, Color color) {
        // Convert grid coordinates to pixel positions
        int[] xy = enemy.convertCoordinate(coordinates);
        int x = xy[0] * 40 + (SettingsController.currentResolution[0]-375)/2;
        int y = xy[1] * 40 + (SettingsController.currentResolution[1]-400)/2;
    
        // Create a new circle
        Circle circle = new Circle(x, y, 10, color);
    
        // Add the circle to the anchor pane
        anchorPane.getChildren().add(circle);
        circle.toFront();
    }

    // Method to add a bullet to the anchor pane when changing the scene
    public void addBullet() {
        // If it is the player's turn, add the bullets shot by the player to the anchor pane
        if (!gameController.isPlayerTurn) {
            for (Map.Entry<String, Boolean> entry : bulletsShotEnemy.entrySet()) {
                String coordinates = entry.getKey();
                Boolean isHit = entry.getValue();
    
                if (isHit) {
                    drawCircle(coordinates, Color.RED);
                } else {
                    drawCircle(coordinates, Color.BLUE);
                }
            }
        } 
        // If it is the enemy's turn, add the bullets shot by the enemy to the anchor pane
        else {
            for (Map.Entry<String, Boolean> entry : bulletsShotPlayer.entrySet()) {
                String coordinates = entry.getKey();
                Boolean isHit = entry.getValue();
                if (isHit) {
                    drawCircle(coordinates, Color.RED);
                } else {
                    drawCircle(coordinates, Color.BLUE);
                }
            }
        }
    }

    // Stores the coordinates of the destroyed ships
    public Set<List<String>> destroyedShips = new HashSet<>();
    // Stores all shots of the enemy
    public static List<String> AllShots = new ArrayList<>();
    public static boolean shipDestroyed = false;

    // Method to check if the enemy ships are destroyed
    public void EnemyShips() {
        // Check if the enemy ships are destroyed
        for (Map.Entry<String, List<String>> entry : Enemy.enemyShips.entrySet()) {
            List<String> shipCoordinates = entry.getValue();
            List<String> hitCoordinates = new ArrayList<>();
    
            for (String coordinate : shipCoordinates) {
                if (bulletsShotPlayer.containsKey(coordinate)) {
                    hitCoordinates.add(coordinate);
                }
            }
    
            if (shipCoordinates.size() == hitCoordinates.size() && !destroyedShips.contains(shipCoordinates)) {
                // If the enemy ships are destroyed, draw the enemy ships and add the coordinates around the destroyed ship to AllShots
                drawEnemyShip(shipCoordinates);
                destroyedShips.add(shipCoordinates);
                shipDestroyed = true;

                // Add all the coordinates around the destroyed ship to AllShots
                addSurroundingCoordinatesToAllShots(shipCoordinates);
            }
        }
    }

    // Method to add the coordinates around the destroyed ship to AllShots
    public void addSurroundingCoordinatesToAllShots(List<String> coordinate) {
        for (String c : coordinate) {
            int[] xy = enemy.convertCoordinate(c);
            int x = xy[0];
            int y = xy[1];

            // Add cordinates around the given cordinates to AllShots
            AllShots.add(String.valueOf((char) (x + 64) + (y + 1)));
            AllShots.add(String.valueOf((char) (x + 64) + (y - 1)));
            AllShots.add(String.valueOf((char) (x + 65) + y));
            AllShots.add(String.valueOf((char) (x + 63) + y));
            AllShots.add(String.valueOf((char) (x + 65) + (y + 1)));
            AllShots.add(String.valueOf((char) (x + 65) + (y - 1)));
            AllShots.add(String.valueOf((char) (x + 63) + (y + 1)));
            AllShots.add(String.valueOf((char) (x + 63) + (y - 1)));
        }
    }

    // Method to draw the enemy ships
    public void drawEnemyShip(List<String> shipCoordinates) {
        rectangle = new Rectangle();
        boolean isVertical = shipCoordinates.get(0).charAt(0) == shipCoordinates.get(1).charAt(0);
        int shipLength = shipCoordinates.size();

        // Convert grid coordinates to pixel positions
        int[] xy = enemy.convertCoordinate(shipCoordinates.get(0));
        int x = xy[0] * 40 + (SettingsController.currentResolution[0]-375)/2 - 20;
        int y = xy[1] * 40 + (SettingsController.currentResolution[1]-400)/2 - 20;

        // Check if the ship is vertical or horizontal
        if (isVertical) {
            rectangle.setWidth(40);
            rectangle.setHeight(40 * shipLength);
        } else {
            rectangle.setWidth(40 * shipLength);
            rectangle.setHeight(40);
        }
        
        // Set the image of the ship
        if (shipLength == 2) {imageURL = "file:src\\main\\resources\\org\\openjfx\\Images\\ship2long";}
        else if (shipLength == 3) {imageURL = "file:src\\main\\resources\\org\\openjfx\\Images\\ship3long";}
        else if (shipLength == 4) {imageURL = "file:src\\main\\resources\\org\\openjfx\\Images\\ship4long";}
        else if (shipLength == 5) {imageURL = "file:src\\main\\resources\\org\\openjfx\\Images\\ship5long";}

        if (!isVertical) {
            rectangle.setFill(new ImagePattern(new Image(imageURL + ".png")));
        }else {
            rectangle.setFill(new ImagePattern(new Image(imageURL + "Rotated.png")));
        }

        // Set the position of the ship
        rectangle.setLayoutX(x);
        rectangle.setLayoutY(y);
        anchorPane.getChildren().add(rectangle);
    }

    // Stores the coordinates of the destroyed ships for the player and the enemy
    public static Set<List<String>> destroyedShipsPlayer = new HashSet<>();
    public static Set<List<String>> destroyedShipsEnemy = new HashSet<>();

    // Method to check if the game is over
    public void gameOver() {
        // Adds the coordinates of the destroyed ships to the destroyedShipsPlayer and destroyedShipsEnemy maps
        if (gameController.isPlayerTurn){
            for (Map.Entry<String, List<String>> entry : Enemy.enemyShips.entrySet()) {
                List<String> shipCoordinates = entry.getValue();
                List<String> hitCoordinates = new ArrayList<>();
        
                for (String coordinate : shipCoordinates) {
                    if (bulletsShotPlayer.containsKey(coordinate)) {
                        hitCoordinates.add(coordinate);
                    }
                }
                
                // If the ship is destroyed, add the coordinates to destroyedShipsPlayer
                if (shipCoordinates.size() == hitCoordinates.size() && !destroyedShipsPlayer.contains(shipCoordinates)) {
                    destroyedShipsEnemy.add(shipCoordinates);
                }
            }
        }else{
            for (Map.Entry<Ships, List<String>> entry : GameController.shipLocations.entrySet()) {
                List<String> shipCoordinates = entry.getValue();
                List<String> hitCoordinates = new ArrayList<>();
        
                for (String coordinate : shipCoordinates) {
                    if (bulletsShotEnemy.containsKey(coordinate)) {
                        hitCoordinates.add(coordinate);
                    }
                }
                
                // If the ship is destroyed, add the coordinates to destroyedShipsEnemy
                if (shipCoordinates.size() == hitCoordinates.size() && !destroyedShipsEnemy.contains(shipCoordinates)) {
                    destroyedShipsPlayer.add(shipCoordinates);
                }
            }
        }

        // If all ships are destroyed, the game is over
        if (destroyedShipsPlayer.size() == 9) {
            gameOver = true;
            PauseTransition pause1 = new PauseTransition(Duration.seconds(2));
            pause1.setOnFinished(event -> {
                try {
                    App.setRoot("Lost");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            pause1.play();
        } else if (destroyedShipsEnemy.size() == 9) {
            gameOver = true;
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> {
                try {
                    App.setRoot("Won");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            pause.play();
        }
    }
}