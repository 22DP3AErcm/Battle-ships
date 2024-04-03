package org.openjfx;

import javafx.geometry.Rectangle2D;
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

        setOnMouseDragged((MouseEvent me) -> {
            double deltaX = me.getSceneX() - mouseX;
            double deltaY = me.getSceneY() - mouseY;
            setTranslateX(oldX + deltaX);
            setTranslateY(oldY + deltaY);
        });

        
        setOnMouseReleased((MouseEvent me) -> {
            snapToGrid();
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

    public void snapToGrid(){
        double gridX = isRotated ? grid.getGridPane().getLayoutX() + 40: grid.getGridPane().getLayoutX();
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

            if (oldX >= gridX && oldX <= oldGridWidth && oldY >= gridY && oldY <= oldGridHeight) {
                setTranslateX(oldX);
                setTranslateY(oldY);
            } else {
                // If the old position is outside the grid, snap the ship back to the nearest valid grid position
                double snappedX = Math.max(gridX, Math.min(oldX, oldGridWidth));
                double snappedY = Math.max(gridY, Math.min(oldY, oldGridHeight));

                setTranslateX(snappedX);
                setTranslateY(snappedY);

                if (isRotated) {
                    rectangle.getTransforms().clear();
                    setIsRotated(false);
                    setRotation(0);
                }
            }
        }
    }

    public boolean onShip() {
        
        return false;
    }
        
}