package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class EnemyController {
    @FXML
    public AnchorPane anchorPane;
    private Grid grid;

    public void initialize() {
        Bullets bullets = new Bullets(anchorPane);
        grid = new Grid(anchorPane);
        grid.addButtonsToGrid();
        bullets.addBullet();
    }
}