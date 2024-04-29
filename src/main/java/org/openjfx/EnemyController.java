package org.openjfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class EnemyController {
    @FXML
    public AnchorPane anchorPane;
    private Grid grid;

    public void initialize() {
        grid = new Grid(anchorPane);
        grid.addButtonsToGrid();
    }
}