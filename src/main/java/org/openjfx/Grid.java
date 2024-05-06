package org.openjfx;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Grid {
    GameController gameController = new GameController();
    private List<Ships> ships = new ArrayList<>();
    private AnchorPane anchorPane;
    private Pane gridPane;
    Bullets bullets;
    public Grid() {}

    // Add a ship to the grid
    public void addShip(Ships ship) {
        ships.add(ship);
        gridPane.getChildren().add(ship);
    }

    // Get the list of ships
    public List<Ships> getShips() {
        return ships;
    }
    
    // Create a 9x9 grid
    public Grid(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
        this.gridPane = new Pane();
        this.bullets = new Bullets(anchorPane);

        for (int i = 0; i < 350 ; i+= 40){
            for (int j = 0; j < 350; j+= 40){
                Rectangle r = new Rectangle(i, j, 40, 40);
                r.setFill(Color.LIGHTBLUE);
                r.setStroke(Color.BLACK);
                
                gridPane.getChildren().add(r);
            }
        }

        // Set the gridPane's preferred width and height
        gridPane.setPrefWidth(400);
        gridPane.setPrefHeight(400);

        // Add a change listener to the width and height properties of the anchorPane
        anchorPane.widthProperty().addListener((obs, oldVal, newVal) -> centerGridPane());
        anchorPane.heightProperty().addListener((obs, oldVal, newVal) -> centerGridPane());

        anchorPane.getChildren().add(gridPane);
        
        gridPane.toBack();
    }

    // Center the gridPane in the anchorPane
    public void centerGridPane() {
        double paneWidth = anchorPane.getWidth();
        double paneHeight = anchorPane.getHeight();
        double gridPaneWidth = gridPane.getPrefWidth();
        double gridPaneHeight = gridPane.getPrefHeight();

        AnchorPane.setTopAnchor(gridPane, (paneHeight - gridPaneHeight) / 2);
        AnchorPane.setBottomAnchor(gridPane, (paneHeight - gridPaneHeight) / 2);
        AnchorPane.setLeftAnchor(gridPane, (paneWidth - gridPaneWidth) / 2);
        AnchorPane.setRightAnchor(gridPane, (paneWidth - gridPaneWidth) / 2);
    }

    // Get the gridPane
    public Pane getGridPane() {
        return gridPane;
    }
    
    // Add buttons to the grid
    public void addButtonsToGrid() {
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
        int cellSize = 40;
        int gridWidth = cellSize * 9;
        int gridHeight = cellSize * 9;

        // Create a layout pass to ensure that the anchorPane has been laid out before we try to get its width and height
        Platform.runLater(() -> {
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            double decorationWidth = stage.getWidth() - anchorPane.getScene().getWidth();
            double decorationHeight = stage.getHeight() - anchorPane.getScene().getHeight();

            double paneWidth = anchorPane.getWidth() - decorationWidth - 25;
            double paneHeight = anchorPane.getHeight() - decorationHeight;

            double startX = (paneWidth - gridWidth) / 2;
            double startY = (paneHeight - gridHeight) / 2;

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String buttonName = letters[j] + Integer.toString(i + 1);
                    Button button = new Button(buttonName);
                    button.setPrefSize(cellSize, cellSize);
                    button.setLayoutX(startX + j * cellSize);
                    button.setLayoutY(startY + i * cellSize);

                    // If it is the player's turn, add an event handler to the button
                    if (gameController.isPlayerTurn) {
                        // Check if the ship is shot when the button is clicked
                        button.setOnAction(event -> bullets.checkIfShipIsShot(buttonName));
                    }
                    
                    button.setOpacity(0);
                    anchorPane.getChildren().add(button);
                    button.toFront();
                }
            }
        });
    }

}