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

    public void checkIfShipIsShot(Map<Ships, List<String>> objectLocations, String cordinates){
        
        drawCircle(cordinates);
    }

    public void drawCircle(String coordinates) {
        // Convert grid coordinates to pixel positions
        int[] xy = enemy.convertCoordinate(coordinates);
        int x = xy[0] * 40 + (SettingsController.currentResolution[0]-375)/2;
        int y = xy[1] * 40 + (SettingsController.currentResolution[1]-400)/2;
    
        // Create a new circle
        Circle circle = new Circle(x, y, 10, Color.RED);
    
        // Add the circle to the anchor pane
        anchorPane.getChildren().add(circle);
        circle.toFront();
    }
}