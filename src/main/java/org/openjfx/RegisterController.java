package org.openjfx;

import java.io.IOException;
import javafx.fxml.FXML;

public class RegisterController {

    @FXML
    private void goToLogin() throws IOException {
        App.setRoot("Login");
    }
}