package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LostController {
    @FXML
    private Button mainMenuButton;

    public void initialize()
    {
        try {
            CsvManeger.addScore(LoginController.account.split(", ")[0],0,  1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mainMenuButtonClicked() throws Exception{
        App.setRoot("MainMenu");
    }

}
