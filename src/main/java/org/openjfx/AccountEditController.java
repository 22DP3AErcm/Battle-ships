package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AccountEditController {
    
    @FXML
    private TextField username, email, password;

    private void setText()
    {
        username.setText("Hello");
        email.setText("No");
        password.setText("Yes");
    }
}
