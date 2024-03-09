package org.openjfx;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RegisterController {

    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField password2;
    @FXML
    private Text DoNotMatch;
    

    @FXML
    private void register() throws IOException {
        String passwordText = password.getText();
        String password2Text = password2.getText();
        

        if (passwordText.equals(password2Text)) {
            DoNotMatch.setVisible(false);
            String usernameText = username.getText();
            String emailText = email.getText();
            
            //App.setRoot("Login");
        } else {
            DoNotMatch.setVisible(true);
            
        }
    }


    @FXML
    private void goToLogin() throws IOException {
        App.setRoot("Login");
    }
}