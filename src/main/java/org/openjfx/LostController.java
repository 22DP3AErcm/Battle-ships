package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LostController {
    @FXML
    private Button mainMenuButton;

    // Initialize the Lost scene
    public void initialize()
    {   
        // Add the score to the CSV file
        try {
            CsvManeger.addScore(LoginController.account.split(", ")[0],0,  1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method for switching to the MainMenu scene if the button is clicked
    public void mainMenuButtonClicked() throws Exception{
        App.setRoot("MainMenu");
    }

}
