package org.openjfx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Grid {
    
    private AnchorPane anchorPane;
    private Pane gridPane;

    
    public Grid(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
        this.gridPane = new Pane();

        for (int i = 0; i < 400 ; i+= 40){
            for (int j = 0; j < 400; j+= 40){
                Rectangle r = new Rectangle(i, j, 40, 40);
                r.setFill(Color.WHITE);
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
    }

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

}