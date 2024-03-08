package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;

public class LoginController {
    
    @FXML
    private void goToRegister() throws IOException
    {
        App.setRoot("Register");
    }

}
