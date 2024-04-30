package org.openjfx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullets {
    public static Map<String, Circle> bulletsShotPlayer= new HashMap<>();
    public static Map<String, Circle> bulletsShotEnemy= new HashMap<>();
    Enemy enemy = new Enemy();
    GameController gameController = new GameController();
    private AnchorPane anchorPane;

    public Bullets(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public void checkIfShipIsShot(String coordinates){
        Map<String, Circle> bulletsShot = gameController.isPlayerTurn ? bulletsShotPlayer : bulletsShotEnemy;
    
        // If a shot has already been fired at these coordinates, do nothing
        if (bulletsShot.containsKey(coordinates)) {
            return;
        }
    
        Map<?, List<String>> ships = gameController.isPlayerTurn ? Enemy.enemyShips : GameController.shipLocations;
        List<Object> keysToRemove = new ArrayList<>();
        for (Map.Entry<?, List<String>> entry : ships.entrySet()) {
            List<String> shipCoordinates = new ArrayList<>(entry.getValue()); // Ensure the list is mutable
            if (shipCoordinates.contains(coordinates)) {
                shipCoordinates.remove(coordinates);
                if (shipCoordinates.isEmpty()) {
                    keysToRemove.add(entry.getKey());
                }
                drawCircle(coordinates, Color.RED);
                // Store the shot coordinates
                bulletsShot.put(coordinates, new Circle());
                return;
            }
        }
        for (Object key : keysToRemove) {
            ships.remove(key);
        }
        drawCircle(coordinates, Color.BLUE);
        // Store the shot coordinates
        bulletsShot.put(coordinates, new Circle());
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
}