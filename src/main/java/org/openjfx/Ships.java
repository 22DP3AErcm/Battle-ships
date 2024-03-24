package org.openjfx;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Ships {
    private double x;
    private double y;
    private double width;
    private double height;
    private int id;
    private boolean isPlaced;
    private boolean isHorizontal;
    private Rectangle ship;
    private Pane parentPane;
    private Draggable draggable;

    public Ships(int id, double x, double y, double width, double height, Pane parentPane) {
        this.id = id;
        this.isPlaced = false;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.parentPane = parentPane;
        draggable = new Draggable();
        drawship();
    }

    public void setX(double x) {
        this.x = x;
        drawship();
    }

    public void setY(double y) {
        this.y = y;
        drawship();
    }

    private double getX() {
        return x;
    }

    private double getY() {
        return y;
    }

    public void setColor(Color color) {
        ship.setFill(color);
    }

    public void setStroke(Color color) {
        ship.setStroke(color);
    }

    private void setHorizontal(boolean horizontal) {
        this.isHorizontal = horizontal;
    }

    private void drawship() {
        if (ship != null) {
            
            ((Pane) ship.getParent()).getChildren().remove(ship);
        }
        ship = new Rectangle(x, y, width, height);
        ship.setFill(Color.WHITE);
        ship.setStroke(Color.BLACK);
        
        parentPane.getChildren().add(ship);
        draggable.makeDraggable(ship);
        
    }

    public void bringToFront() {
        ship.toFront();
    }

    public boolean isBeingDragged() {
        return ship.isPressed();
    }
    
    public void rotate() {
        ship.setRotate(ship.getRotate() + 90);
    }
}