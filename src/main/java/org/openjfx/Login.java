package org.openjfx;

import java.io.IOException;
import javafx.fxml.FXML;

public class Login {

    @FXML
    private void switchToRegister() throws IOException {
        App.setRoot("Register");
    }
}
