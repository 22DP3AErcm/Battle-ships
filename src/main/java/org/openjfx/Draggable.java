package org.openjfx;

public class Draggable {
    private double xOffset;
    private double yOffset;
    
    public void makeDraggable(javafx.scene.Node node) {
        node.setOnMousePressed((event) -> {
            xOffset = event.getSceneX() - node.getLayoutX();
            yOffset = event.getSceneY() - node.getLayoutY();
        });     
        node.setOnMouseDragged((event) -> {
            node.setLayoutX(event.getSceneX() - xOffset);
            node.setLayoutY(event.getSceneY() - yOffset);
        });
    }
}
