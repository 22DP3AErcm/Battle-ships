package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class EnemyController {
    @FXML
    public AnchorPane anchorPane;
    private Grid grid;
    Enemy enemy = new Enemy();
    GameController gameController = new GameController();

    public void initialize() {
        Bullets bullets = new Bullets(anchorPane);
        grid = new Grid(anchorPane);
        grid.addButtonsToGrid();
        bullets.addBullet();
        bullets.EnemyShips();
    }
}