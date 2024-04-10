package org.openjfx;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AccountEditController {

    @FXML
    private static TextField username;
    private static TextField email;
    private static TextField password;

    @FXML
    public void goToGame() throws IOException {
        App.setRoot("Game");
    }

    @FXML
    public static void setText() {
        username.setText("Hello");
        email.setText("No");
        password.setText("Yes");
    }

}
