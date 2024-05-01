package org.openjfx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Bullets {
    public static Map<String, Boolean> bulletsShotPlayer= new HashMap<>();
    public static Map<String, Boolean> bulletsShotEnemy= new HashMap<>();
    Enemy enemy = new Enemy();
    GameController gameController = new GameController();
    private AnchorPane anchorPane;
    private Rectangle rectangle;
    String imageURL;

    public Bullets(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }
    
    public void checkIfShipIsShot(String coordinates){
        Map<String, Boolean> bulletsShot = gameController.isPlayerTurn ? bulletsShotPlayer : bulletsShotEnemy;
    
        // If a shot has already been fired at these coordinates, do nothing
        if (bulletsShot.containsKey(coordinates)) {
            return;
        }
    
        Map<?, List<String>> ships = gameController.isPlayerTurn ? Enemy.enemyShips : GameController.shipLocations;
        for (Map.Entry<?, List<String>> entry : ships.entrySet()) {
            List<String> shipCoordinates = new ArrayList<>(entry.getValue());
            if (shipCoordinates.contains(coordinates)) {
                drawCircle(coordinates, Color.RED);
                // Store the shot coordinates
                bulletsShot.put(coordinates, true);
                if (gameController.isPlayerTurn) {
                    EnemyShips();
                } 
                return;
            }
        }
        drawCircle(coordinates, Color.BLUE);
        // Store the shot coordinates
        bulletsShot.put(coordinates, false);
    }
    
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

    public void addBullet() {
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
        } else {
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

    public static Set<List<String>> destroyedShips = new HashSet<>();

    public void EnemyShips() {
        for (Map.Entry<String, List<String>> entry : Enemy.enemyShips.entrySet()) {
            List<String> shipCoordinates = entry.getValue();
            List<String> hitCoordinates = new ArrayList<>();
    
            for (String coordinate : shipCoordinates) {
                if (bulletsShotPlayer.containsKey(coordinate)) {
                    hitCoordinates.add(coordinate);
                }
            }
    
            if (shipCoordinates.size() == hitCoordinates.size() && !destroyedShips.contains(shipCoordinates)) {
                drawEnemyShip(shipCoordinates);
                destroyedShips.add(shipCoordinates);
            }
        }
    }


    public void drawEnemyShip(List<String> shipCoordinates) {
        rectangle = new Rectangle();
        boolean isVertical = shipCoordinates.get(0).charAt(0) == shipCoordinates.get(1).charAt(0);
        int shipLength = shipCoordinates.size();

        int[] xy = enemy.convertCoordinate(shipCoordinates.get(0));
        int x = xy[0] * 40 + (SettingsController.currentResolution[0]-375)/2 - 20;
        int y = xy[1] * 40 + (SettingsController.currentResolution[1]-400)/2 - 20;

       if (isVertical) {
            rectangle.setWidth(40);
            rectangle.setHeight(40 * shipLength);
        } else {
            rectangle.setWidth(40 * shipLength);
            rectangle.setHeight(40);
        }
        

        if (shipLength == 2) {imageURL = "file:src\\main\\resources\\org\\openjfx\\Images\\ship2long";}
        else if (shipLength == 3) {imageURL = "file:src\\main\\resources\\org\\openjfx\\Images\\ship3long";}
        else if (shipLength == 4) {imageURL = "file:src\\main\\resources\\org\\openjfx\\Images\\ship4long";}
        else if (shipLength == 5) {imageURL = "file:src\\main\\resources\\org\\openjfx\\Images\\ship5long";}

        if (!isVertical) {
            rectangle.setFill(new ImagePattern(new Image(imageURL + ".png")));
        }else {
            rectangle.setFill(new ImagePattern(new Image(imageURL + "Rotated.png")));
        }

        rectangle.setLayoutX(x);
        rectangle.setLayoutY(y);
        anchorPane.getChildren().add(rectangle);
    }
}