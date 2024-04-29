package org.openjfx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullets {
    public static Map<String, Circle> bulletsShotPlayer = new HashMap<>();
    public static Map<String, Circle> bulletsShotEnemy= new HashMap<>();
    Enemy enemy = new Enemy();
    GameController gameController = new GameController();
    private AnchorPane anchorPane;
    
    public void checkIfShipIsShot(Map<Ships, List<String>> objectLocations, String cordinates){
        for (Map.Entry<Ships, List<String>> entry : objectLocations.entrySet()) {
            Ships ship = entry.getKey();
            List<String> coordinates = entry.getValue();
            if (coordinates.contains(cordinates)){
                drawBullet(cordinates, Color.RED);
    
                // Add the hit coordinate to the ship's hit coordinates
                ship.addHitCoordinate(cordinates);
    
                // Check if all the ship's coordinates have been shot
                if (ship.getHitCoordinates().size() == coordinates.size()) {
                    System.out.println("All the coordinates of the ship have been shot");
                }
            }else{
                drawBullet(cordinates, Color.BLUE);
            }
        }
    }
    

    public void drawBullet(String coordinates, Color color){
        // Convert grid coordinate to pixel position
        int[] xy = enemy.convertCoordinate(coordinates);
        int x = Math.round(xy[0] / 40) * 40 + (SettingsController.currentResolution[0]-360)/2 - 220;
        int y = Math.round(xy[1] / 40) * 40 + (SettingsController.currentResolution[1]-360)/2 - 120;
    
        // Create a new circle
        Circle bullet = new Circle(x, y, 10, color); // 10 is the radius of the circle
    
        // Set bullet's position
        bullet.setLayoutX(x);
        bullet.setLayoutY(y);
    
        // Add the bullet to the anchor pane if it's not already a child
        if (!anchorPane.getChildren().contains(bullet)) {
            anchorPane.getChildren().add(bullet);
        }
    }
}
