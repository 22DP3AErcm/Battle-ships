package org.openjfx;

import java.io.IOException;

public class WonController {
    // Method for initializing the controller
    public void initialize()
    {   
        // Add score to the user
        try {
            CsvManeger.addScore(LoginController.account.split(", ")[0], 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method for going to the main menu if the user clicks the button
    public void goToMainMenu() throws Exception {
        App.setRoot("MainMenu");
    }
}
