package org.openjfx;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
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
    private String imageURL;

    // Constructor for the Ships class that creates a ship with the specified parameters
    public Ships(int x, int y, int width, int height, double cellSize, Grid grid, Map<Ships, List<String>> shipLocations, String imageURL) {
        this.cellSize = cellSize;
        this.grid = grid;
        this.rectangle = new Rectangle(width, height, Color.BLUE);
        this.shipLocations = shipLocations;
        this.imageURL = imageURL;
        this.rectangle.setFill(new ImagePattern(new Image(imageURL + ".png")));
        setTranslateX(x);
        setTranslateY(y);

        setOnMousePressed((MouseEvent me) -> {
            mouseX = me.getSceneX();
            mouseY = me.getSceneY();
            oldX = getTranslateX();
            oldY = getTranslateY();
            requestFocus();
        });
        
        // Drag the ship
        rectangle.setOnMouseDragged((MouseEvent me) -> {     
            double offsetX = me.getSceneX() - mouseX;
            double offsetY = me.getSceneY() - mouseY;
            setTranslateX(oldX + offsetX);
            setTranslateY(oldY + offsetY);
        });

        // snap to grid when released
        setOnMouseReleased((MouseEvent me) -> {
            snapToGrid(this);
        });

        // add the ship to the pane
        getChildren().add(rectangle);
    }

    // Update the cell size of the ship
    public void updateCellSize(double newCellSize) {
        this.cellSize = newCellSize;
    }

    // Get the rectangle of the ship
    public Rectangle getRectangle() {
        return rectangle;
    }

    // Set the rotation of the ship
    public void setIsRotated(boolean isRotated) {
        this.isRotated = isRotated;
    }

    // Get the rotation of the ship
    public int getRotation() {
        return rotation;
    }

    // Get the image URL of the ship
    public String getImageURL(){
        return imageURL;
    }

    // Set the rotation of the ship
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
    
    // Get the rotation of the ship
    public boolean isRotated() {
        return isRotated;
    }

    // creates a list of hit coordinates
    private List<String> hitCoordinates = new ArrayList<>();

    // Add a hit coordinate
    public void addHitCoordinate(String coordinate) {
        hitCoordinates.add(coordinate);
    }

    // returns the list of hit coordinates
    public List<String> getHitCoordinates() {
        return hitCoordinates;
    }

    // Snap the ship to the grid
    public void snapToGrid(Ships ship){
        // Get the current grid coordinates
        double gridX = grid.getGridPane().getLayoutX();
        double gridY = grid.getGridPane().getLayoutY();
        
        double newX = Math.round((getTranslateX() - gridX) / cellSize) * cellSize + gridX;
        double newY = Math.round((getTranslateY() - gridY) / cellSize) * cellSize + gridY;
    
        // Get the ship size in terms of cells
        int shipSizex = (int) ((rectangle.getWidth()) / cellSize);
        int shipSizey = (int) ((rectangle.getHeight()) / cellSize);
    
        // Adjust the grid boundaries based on the rotation of the ship
        double gridWidth = gridX + (9 - shipSizex) * cellSize;
        double gridHeight = gridY + (9 - shipSizey) * cellSize;
    
        // Ensure the ship doesn't go outside the grid
        if (newX >= gridX && newX <= gridWidth && newY >= gridY && newY <= gridHeight && !onShip() && !areShipsAdjacent()) {
            setTranslateX(newX);
            setTranslateY(newY);
        } else {
            // If the ship is dragged outside the grid, revert to the original position
            double oldGridWidth = gridX + (9 - (int) ((rectangle.getWidth()) / cellSize)) * cellSize;
            double oldGridHeight = gridY + (9 - (int) ((rectangle.getHeight()) / cellSize)) * cellSize;
            
            // Check if the old position is within the grid
            if (oldX >= gridX && oldX <= oldGridWidth && oldY >= gridY && oldY <= oldGridHeight && !onShip() && !areShipsAdjacent()) {
                setTranslateX(oldX);
                setTranslateY(oldY);
            } else {
                // The new position is invalid, so revert to the old position
                setTranslateX(oldX);
                setTranslateY(oldY);
            }
        }
    }

    // Check if the ship is on another ship
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

    // Check if the ship is adjacent to another ship
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
    
    // Convert the grid coordinates
    public  int[] convertCoordinate(String coordinate) {
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

        int shipSizex = (int) ((rectangle.getWidth()) / cellSize);
        int shipSizey = (int) ((rectangle.getHeight()) / cellSize);

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

    // Rotate the ship
    public void rotateShip(KeyEvent event) {
        if (event.getCode() == KeyCode.R) {
            Rectangle rectangle = this.getRectangle();
            // Save the current rotation and position
            int oldRotation = this.getRotation();
    
            // Save the current width and height
            double oldWidth = rectangle.getWidth();
            double oldHeight = rectangle.getHeight();
    
            // Rotate the ship by swapping its width and height
            rectangle.setWidth(oldHeight);
            rectangle.setHeight(oldWidth);
    
            if (oldRotation == 0) {
                this.setIsRotated(true);
                this.setRotation(1);
                rectangle.setFill(new ImagePattern(new Image(this.getImageURL() + "Rotated.png")));
            } else {
                this.setIsRotated(false);
                this.setRotation(0);
                rectangle.setFill(new ImagePattern(new Image(this.getImageURL() + ".png")));
            }
    
            // Calculate the ship's grid coordinates after rotation
            List<String> newGridCoordinates = this.getGridCoordinates();
    
            /// Check if the new position is valid
            if (!this.onShip() && this.isWithinGrid() && !this.areShipsAdjacent()) {
                // If the new position is valid, snap the ship to the grid and update the ship's grid coordinates
                shipLocations.put(this, newGridCoordinates);
            } else {
                // If the new position is not valid, revert the rotation and position
                rectangle.setWidth(oldWidth);
                rectangle.setHeight(oldHeight);
                
                
                if (oldRotation == 0) {
                    this.setIsRotated(false);
                    this.setRotation(0);
                    rectangle.setFill(new ImagePattern(new Image(this.getImageURL() + ".png")));

                } else {
                    this.setIsRotated(true);
                    this.setRotation(1);
                    rectangle.setFill(new ImagePattern(new Image(this.getImageURL() + "Rotated.png")));
                }   
                         
            }
        }
    }
    
    // Check if the ship is within the grid
    public boolean isWithinGrid() {
        double gridX = grid.getGridPane().getLayoutX();
        double gridY = grid.getGridPane().getLayoutY();
    
        double newX = Math.round((getTranslateX() - gridX) / cellSize) * cellSize + gridX;
        double newY = Math.round((getTranslateY() - gridY) / cellSize) * cellSize + gridY;
    
        // Get the ship size in terms of cells
        int shipSizex = (int) ((rectangle.getWidth()) / cellSize);
        int shipSizey = (int) ((rectangle.getHeight()) / cellSize);
    
        // Adjust the grid boundaries based on the rotation of the ship
        double gridWidth = gridX + (9 - shipSizex) * cellSize;
        double gridHeight = gridY + (9 - shipSizey) * cellSize;
    
        // Ensure the ship doesn't go outside the grid
        if (newX >= gridX && newX <= gridWidth && newY >= gridY && newY <= gridHeight) {
            return true;
        } else {
            return false;
        }
    }
}