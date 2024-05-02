package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LostController {
    @FXML
    private Button mainMenuButton;

    public void mainMenuButtonClicked() throws Exception{
        App.setRoot("MainMenu");
    }

}
