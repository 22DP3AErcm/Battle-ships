package org.openjfx;

import java.util.ArrayList;
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
    private Map<Ships, List<String>> shipLocations;

    public Ships(int x, int y, int width, int height, double cellSize, Grid grid, Map<Ships, List<String>> shipLocations) {
        this.cellSize = cellSize;
        this.grid = grid;
        this.rectangle = new Rectangle(width, height, Color.BLUE);
        this.shipLocations = shipLocations;
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
                double rotatedDeltaX = deltaX * Math.cos(angle) - deltaY * Math.sin(angle);
                double rotatedDeltaY = deltaX * Math.sin(angle) + deltaY * Math.cos(angle);
                setTranslateX(oldX + rotatedDeltaX);
                setTranslateY(oldY + rotatedDeltaY);
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

        } else {
            // If the ship is dragged outside the grid, revert to the original position
            double oldGridWidth = gridX + (9 - (int) ((isRotated ? rectangle.getHeight() : rectangle.getWidth()) / cellSize)) * cellSize;
            double oldGridHeight = gridY + (9 - (int) ((isRotated ? rectangle.getWidth() : rectangle.getHeight()) / cellSize)) * cellSize;
        
            if (oldX >= gridX && oldX <= oldGridWidth && oldY >= gridY && oldY <= oldGridHeight && !onShip()) {
                setTranslateX(oldX);
                setTranslateY(oldY);
            } else {
                // If the old position is outside the grid, snap the ship back to the nearest valid grid position
                double snappedX = Math.max(gridX, Math.min(oldX, oldGridWidth));
                double snappedY = Math.max(gridY, Math.min(oldY, oldGridHeight));
                
                setTranslateX(snappedX);
                setTranslateY(snappedY);
            }
        
            if (isRotated) {
                rectangle.getTransforms().clear();
                setIsRotated(false);
                setRotation(0);
            }
        }
        
    }

    public boolean onShip() {
        // Get the current ship's grid coordinates
        List<String> currentShipCoordinates = getGridCoordinates();
    
        // Create a list to store the surrounding cells of the current ship
        List<String> surroundingCells = new ArrayList<>();
    
        // Calculate the surrounding cells
        for (String coordinate : currentShipCoordinates) {
            int x = Character.getNumericValue(coordinate.charAt(0));
            int y = Character.getNumericValue(coordinate.charAt(1));

            // Add the cells that are horizontally and vertically adjacent to the current cell
            if (x - 1 >= 0) surroundingCells.add((x - 1) + "" + y);
            if (x + 1 <= 9) surroundingCells.add((x + 1) + "" + y);
            if (y - 1 >= 0) surroundingCells.add(x + "" + (y - 1));
            if (y + 1 <= 9) surroundingCells.add(x + "" + (y + 1));
        }

        // Iterate over all the other ships
        for (Ships otherShip : shipLocations.keySet()) {
            // Skip the current ship
            if (otherShip == this) {
                continue;
            }

            // Get the other ship's grid coordinates
            List<String> otherShipCoordinates = shipLocations.get(otherShip);

            // Check if any of the current ship's coordinates or surrounding cells overlap with the other ship's coordinates
            for (String coordinate : currentShipCoordinates) {
                if (otherShipCoordinates.contains(coordinate)) {
                    return true;
                }
            }

            for (String cell : surroundingCells) {
                if (otherShipCoordinates.contains(cell)) {
                    return true;
                }
            }
        }

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
                int gridNumber = (int) ('A' + (newY - gridY) / cellSize + (isRotated ? j : 0)) - 'A' + 1;
                char gridLetter = (char) ((newX - gridX) / cellSize + 1 + (isRotated ? 0 : i) + 'A' - 1);
                String coordinates = String.valueOf(gridLetter) + gridNumber;
                gridCoordinates.add(coordinates);
            }
        }
        return gridCoordinates;
    }

    
}