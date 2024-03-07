package org.openjfx;

import java.io.IOException;
import javafx.fxml.FXML;

public class Register {

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("Login");
    }
}