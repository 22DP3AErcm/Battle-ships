package org.openjfx;

import java.io.IOException;

public class WonController {

    public void initialize()
    {
        try {
            CsvManeger.addScore(LoginController.account.split(", ")[0], 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToMainMenu() throws Exception {
        App.setRoot("MainMenu");
    }
}
