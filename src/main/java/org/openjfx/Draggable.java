package org.openjfx;

public class Draggable {
    private double xOffset;
    private double yOffset;
    private double cellSize;

    public Draggable(double cellSize) {
        this.cellSize = cellSize;
    }

    public void makeDraggable(javafx.scene.Node node) {
        node.setOnMousePressed((event) -> {
            xOffset = event.getSceneX() - node.getTranslateX();
            yOffset = event.getSceneY() - node.getTranslateY();
        });

        node.setOnMouseDragged((event) -> {
            node.setTranslateX(event.getSceneX() - xOffset);
            node.setTranslateY(event.getSceneY() - yOffset);
        });

        node.setOnMouseReleased((event) -> {
            double newX = Math.round(node.getTranslateX() / cellSize) * cellSize;
            double newY = Math.round(node.getTranslateY() / cellSize) * cellSize;
            node.setTranslateX(newX);
            node.setTranslateY(newY);
        });
    }
}