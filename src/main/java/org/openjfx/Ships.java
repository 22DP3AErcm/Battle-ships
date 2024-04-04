package org.openjfx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Ships extends Pane {
    private double mouseX, mouseY;
    private double oldX, oldY;
    private final Rectangle rectangle;
    private double cellSize;
    private Grid grid;
    private boolean isRotated = false;
    private int rotation = 0;

    public Ships(int x, int y, int width, int height, double cellSize, Grid grid) {
        this.cellSize = cellSize;
        this.grid = grid;
        this.rectangle = new Rectangle(width, height, Color.BLUE);
        setTranslateX(x);
        setTranslateY(y);

        setOnMousePressed((MouseEvent me) -> {
            mouseX = me.getSceneX();
            mouseY = me.getSceneY();
            oldX = getTranslateX();
            oldY = getTranslateY();
        });

        rectangle.setOnMousePressed((MouseEvent me) -> {
            mouseX = me.getSceneX();
            mouseY = me.getSceneY();
            oldX = getTranslateX();
            oldY = getTranslateY();
        });
        
        rectangle.setOnMouseDragged((MouseEvent me) -> {
            double deltaX = me.getSceneX() - mouseX;
            double deltaY = me.getSceneY() - mouseY;
            if (isRotated) {
                // Adjust the position of the ship based on its rotation
                double angle = Math.toRadians(rotation);
                double localX = deltaX * Math.cos(angle) - deltaY * Math.sin(angle);
                double localY = deltaX * Math.sin(angle) + deltaY * Math.cos(angle);
                setTranslateX(oldX + localX);
                setTranslateY(oldY + localY);
            } else {
                setTranslateX(oldX + deltaX);
                setTranslateY(oldY + deltaY);
            }
        });

        
        setOnMouseReleased((MouseEvent me) -> {
            snapToGrid(this);
        });

        

        getChildren().add(rectangle);
    }

    public void updateCellSize(double newCellSize) {
        this.cellSize = newCellSize;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
    public void setIsRotated(boolean isRotated) {
        this.isRotated = isRotated;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
    
    public boolean isRotated() {
        return isRotated;
    }

    // Create a map to store each ship and its grid coordinates
    Map<Ships, List<String>> shipLocations = new HashMap<>();
    
    public void snapToGrid(Ships ship){
        double gridX = isRotated ? grid.getGridPane().getLayoutX() + cellSize : grid.getGridPane().getLayoutX();
        double gridY = grid.getGridPane().getLayoutY();
    
        double newX = Math.round((getTranslateX() - gridX) / cellSize) * cellSize + gridX;
        double newY = Math.round((getTranslateY() - gridY) / cellSize) * cellSize + gridY;
        
        // Get the ship size in terms of cells
        int shipSizex = (int) ((isRotated ? rectangle.getHeight() : rectangle.getWidth()) / cellSize);
        int shipSizey = (int) ((isRotated ? rectangle.getWidth() : rectangle.getHeight()) / cellSize);
    
        // Adjust the grid boundaries based on the rotation of the ship
        double gridWidth = gridX + (9 - shipSizex) * cellSize;
        double gridHeight = gridY + (9 - shipSizey) * cellSize;
    
        // Ensure the ship doesn't go outside the grid
        if (newX >= gridX && newX <= gridWidth && newY >= gridY && newY <= gridHeight && !onShip()) {
            setTranslateX(newX);
            setTranslateY(newY);

            // After the ship's position is set, get its grid coordinates
            List<String> gridCoordinates = ship.getGridCoordinates();

            // Save the grid coordinates in the shipLocations map
            shipLocations.put(ship, gridCoordinates);

            // Print the ship and its grid coordinates to the console
            System.out.println("Ship: " + ship + ", Grid Coordinates: " + gridCoordinates);

        } else {
            // If the ship is dragged outside the grid, revert to the original position
            double oldGridWidth = gridX + (9 - (int) ((isRotated ? rectangle.getHeight() : rectangle.getWidth()) / cellSize)) * cellSize;
            double oldGridHeight = gridY + (9 - (int) ((isRotated ? rectangle.getWidth() : rectangle.getHeight()) / cellSize)) * cellSize;
        
            if (oldX >= gridX && oldX <= oldGridWidth && oldY >= gridY && oldY <= oldGridHeight) {
                setTranslateX(oldX);
                setTranslateY(oldY);
            } else {
                // If the old position is outside the grid, snap the ship back to the nearest valid grid position
                double snappedX = Math.max(gridX, Math.min(oldX, oldGridWidth));
                double snappedY = Math.max(gridY, Math.min(oldY, oldGridHeight));
                setTranslateX(snappedX);
                setTranslateY(snappedY);
            }
        
            // After the ship's position is set, get its grid coordinates
            List<String> gridCoordinates = ship.getGridCoordinates();
        
            // Save the grid coordinates in the shipLocations map
            shipLocations.put(ship, gridCoordinates);
        
            // Print the ship and its grid coordinates to the console
            System.out.println("Ship: " + ship + ", Grid Coordinates: " + gridCoordinates);
        
            if (isRotated) {
                rectangle.getTransforms().clear();
                setIsRotated(false);
                setRotation(0);
            }
        }
        
    }

    public boolean onShip() {

        return false;
    }
    
    // Get the grid coordinates of the ship
    public List<String> getGridCoordinates() {
        double gridX = isRotated ? grid.getGridPane().getLayoutX() + cellSize : grid.getGridPane().getLayoutX();
        double gridY = grid.getGridPane().getLayoutY();
    
        double newX = Math.round((getTranslateX() - gridX) / cellSize) * cellSize + gridX;
        double newY = Math.round((getTranslateY() - gridY) / cellSize) * cellSize + gridY;
    
        int shipSizex = (int) ((isRotated ? rectangle.getHeight() : rectangle.getWidth()) / cellSize);
        int shipSizey = (int) ((isRotated ? rectangle.getWidth() : rectangle.getHeight()) / cellSize);
    
        List<String> gridCoordinates = new ArrayList<>();
        for (int i = 0; i < shipSizex; i++) {
            for (int j = 0; j < shipSizey; j++) {
                char gridLetter = (char) ('A' + (newY - gridY) / cellSize + (isRotated ? j : 0));
                int gridNumber = (int) ((newX - gridX) / cellSize + 1 + (isRotated ? 0 : i));
                String coordinates = gridLetter + Integer.toString(gridNumber);
                gridCoordinates.add(coordinates);
            }
        }
        return gridCoordinates;
    }

    
}