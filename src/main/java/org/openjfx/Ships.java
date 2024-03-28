package org.openjfx;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.input.KeyEvent;

public class Ships extends Pane {
    private double mouseX, mouseY;
    private double oldX, oldY;
    private final Rectangle rectangle;
    private double cellSize;
    private Grid grid;


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
            double gridX = grid.getGridPane().getLayoutX();
            double gridY = grid.getGridPane().getLayoutY();
        
            double newX = Math.round((getTranslateX() - gridX) / cellSize) * cellSize + gridX;
            double newY = Math.round((getTranslateY() - gridY) / cellSize) * cellSize + gridY;
            
            double gridWidth = gridX + 10 * cellSize;
            double gridHeight = gridY + 9 * cellSize;
        
            // Get the ship size
            double shipSize = rectangle.getWidth();
        
            // Ensure the ship doesn't go outside the grid
            if (newX >= gridX && newX + shipSize <= gridWidth && newY >= gridY && newY <= gridHeight) {
                setTranslateX(newX);
                setTranslateY(newY);
            } else {
                // If the ship is dragged outside the grid, revert to the original position
                setTranslateX(oldX);
                setTranslateY(oldY);
            }
        });

        

        getChildren().add(rectangle);
    }

    public void updateCellSize(double newCellSize) {
        this.cellSize = newCellSize;
    }
}