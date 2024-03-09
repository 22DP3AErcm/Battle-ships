package org.openjfx;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RegisterController {

    @FXML
    private TextField username, password, password2, email;
    @FXML
    private Text DoNotMatch;
    
    CsvManeger CsvManeger = new CsvManeger();
    

    @FXML
    private void register() throws IOException {
        String passwordText = password.getText();
        String password2Text = password2.getText();
        

        if (passwordText.equals(password2Text)) {
            DoNotMatch.setVisible(false);
            String usernameText = username.getText();
            String emailText = email.getText();

            CsvManeger.addDataToCSV(usernameText, emailText, passwordText);

            App.setRoot("Login");
        } else {
            DoNotMatch.setVisible(true);
            
        }
    }


    @FXML
    private void goToLogin() throws IOException {
        App.setRoot("Login");
    }
}