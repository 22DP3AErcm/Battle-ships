package org.openjfx;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;


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
            this.requestFocus();
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
            this.getParent().requestFocus();
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
        double gridX = grid.getGridPane().getLayoutX();
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
        if (newX >= gridX && newX <= gridWidth && newY >= gridY && newY <= gridHeight && !onShip() && !areShipsAdjacent()) {
            setTranslateX(newX);
            setTranslateY(newY);
        } else {
            // If the ship is dragged outside the grid, revert to the original position
            double oldGridWidth = gridX + (9 - (int) ((isRotated ? rectangle.getHeight() : rectangle.getWidth()) / cellSize)) * cellSize;
            double oldGridHeight = gridY + (9 - (int) ((isRotated ? rectangle.getWidth() : rectangle.getHeight()) / cellSize)) * cellSize;
    
            if (oldX >= gridX && oldX <= oldGridWidth && oldY >= gridY && oldY <= oldGridHeight && !onShip() && !areShipsAdjacent()) {
                setTranslateX(oldX);
                setTranslateY(oldY);
            } else {
                // If the old position is outside the grid, snap the ship back to the nearest valid grid position
                double snappedX = Math.max(gridX, Math.min(oldX, oldGridWidth));
                double snappedY = Math.max(gridY, Math.min(oldY, oldGridHeight));
    
                // Temporarily update the ship's position
                setTranslateX(snappedX);
                setTranslateY(snappedY);
    
                // Check if the new position is on another ship or adjacent to another ship
                if (!onShip() && !areShipsAdjacent()) {
                    // The new position is valid, so do nothing
                } else {
                    // The new position is invalid, so revert to the old position
                    setTranslateX(oldX);
                    setTranslateY(oldY);
                }
            }
        }
    }

    public boolean onShip() {
        // Get the current ship's grid coordinates
        List<String> currentShipCoordinates = getGridCoordinates();
    
        // Iterate over all the other ships
        for (Ships otherShip : shipLocations.keySet()) {
            // Skip the current ship
            if (otherShip == this) {
                continue;
            }
    
            // Get the other ship's grid coordinates
            List<String> otherShipCoordinates = shipLocations.get(otherShip);
            
            // Check if any of the current ship's coordinates overlap with the other ship's coordinates
            for (String coordinate : currentShipCoordinates) {
                // Check for overlap
                if (otherShipCoordinates.contains(coordinate)) {
                    return true;
                }
            }
        }
    
        // No overlap with any other ship
        return false;
    }

    public boolean areShipsAdjacent() {
        // Get the current ship's grid coordinates
        List<String> currentShipCoordinates = getGridCoordinates();
    
        // Iterate over all the other ships
        for (Ships otherShip : shipLocations.keySet()) {
            // Skip the current ship
            if (otherShip == this) {
                continue;
            }
    
            // Get the other ship's grid coordinates
            List<String> otherShipCoordinates = shipLocations.get(otherShip);
    
            // Check if any of the current ship's coordinates are adjacent to the other ship's coordinates
            for (String coordinate : currentShipCoordinates) {
                int[] xy = convertCoordinate(coordinate);
                int x = xy[0];
                int y = xy[1];
    
                // Check for adjacency
                for (int[] d : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}}) {
                    int newX = x + d[0];
                    int newY = y + d[1];
    
                    int gridWidth = 9;
                    int gridHeight = 9;
    
                    // Check if the new coordinates are within the grid
                    if (newX >= 0 && newX <= gridWidth && newY >= 0 && newY <= gridHeight) {
                        String adjacentCoordinate = (char)('A' + newX) + Integer.toString(newY + 1);
                        if (otherShipCoordinates.contains(adjacentCoordinate)) {
                            return true;
                        }
                    }
                }
            }
        }
    
        // No adjacency with any other ship
        return false;
    }
    
    
    public int[] convertCoordinate(String coordinate) {
        int x = coordinate.charAt(0) - 'A';
        int y = Integer.parseInt(coordinate.substring(1)) - 1;
        return new int[]{x, y};
    }
    
    // Get the grid coordinates of the ship
    public List<String> getGridCoordinates() {
        double gridX = grid.getGridPane().getLayoutX();
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

    public void rotateShip(KeyEvent event) {
        if (event.getCode() == KeyCode.R) {
            Rectangle rectangle = this.getRectangle();
            // Save the current rotation and position
            int oldRotation = this.getRotation();
    
            // Rotate the ship
            rectangle.getTransforms().clear();
            if (oldRotation == 0) {
                rectangle.getTransforms().add(new Rotate(90, 20, 20));
                this.setIsRotated(true);
                this.setRotation(1);
                System.out.println("Pagriez " + this.isWithinGrid());
            } else {
                this.setIsRotated(false);
                this.setRotation(0);
            }
    
            // Calculate the ship's grid coordinates after rotation
            List<String> newGridCoordinates = this.getGridCoordinates();
    
            /// Check if the new position is valid
            if (!this.onShip() && this.isWithinGrid() && !this.areShipsAdjacent()) {
                // If the new position is valid, snap the ship to the grid and update the ship's grid coordinates
                this.snapToGrid(this);
                shipLocations.put(this, newGridCoordinates);
            } else {
                // If the new position is not valid, revert the rotation and position
                System.out.println("Invalid rotation");
                if (oldRotation == 0) {
                    rectangle.getTransforms().clear();
                    rectangle.getTransforms().add(new Rotate(0, 20, 20));
                    this.setIsRotated(false);
                    this.setRotation(0);
                } else {
                    rectangle.getTransforms().clear();
                    rectangle.getTransforms().add(new Rotate(90, 20, 20));
                    this.setIsRotated(true);
                    this.setRotation(1);
                }
            }
            System.out.println(this.isWithinGrid());
            System.out.println(this.onShip());
            System.out.println(this.areShipsAdjacent());
        }
    }
    

    public boolean isWithinGrid() {
        double gridX = grid.getGridPane().getLayoutX();
        double gridY = grid.getGridPane().getLayoutY();
    
        double newX = Math.round((getTranslateX() - gridX) / cellSize) * cellSize + gridX;
        double newY = Math.round((getTranslateY() - gridY) / cellSize) * cellSize + gridY;
    
        double gridWidth = gridX + (9) * cellSize;
        double gridHeight = gridY + (9) * cellSize;
    
        // Calculate the ship's width and height
        double shipWidth = isRotated ? this.getHeight() : this.getWidth();
        double shipHeight = isRotated ? this.getWidth() : this.getHeight();
    
        // Use newX, newY, shipWidth and shipHeight in the return statement
        return newX >= gridX && newX + shipWidth <= gridWidth && newY >= gridY && newY + shipHeight <= gridHeight;
    }
}