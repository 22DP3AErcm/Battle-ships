package org.openjfx;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.event.ActionEvent;

public class Login {

    @FXML
    private Hyperlink myHyperlink;

    @FXML
    void handleHyperlinkAction(ActionEvent event) {
        try {
            switchToRegister();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToRegister() throws IOException {
        App.setRoot("Register");
    }
}
