package org.openjfx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RegisterController {

    @FXML
    private TextField username, password, password2, email;
    @FXML
    private Text DoNotMatch;
    
    CsvManeger CsvManeger = new CsvManeger();
    Sorter Sorter = new Sorter();

    @FXML
    private void register() throws IOException {
        String passwordText = password.getText();
        String password2Text = password2.getText();
        

        // Visiem lokiem jābūt aizpildītiem
        if (username.getText().isEmpty() || email.getText().isEmpty() || password.getText().isEmpty() || password2.getText().isEmpty()) {
            DoNotMatch.setText("All fields must be filled!");
            DoNotMatch.setVisible(true);
            return;
        }

        // Lietotājvārdam jābūt vismaz 3 simbolus garai
        if (username.getText().length() < 4) {
            DoNotMatch.setText("Username must be at least 4 characters long!");
            DoNotMatch.setVisible(true);
            return;
        }

        // Lietotājvārdam jābūt vismaz 3 simbolus garai
        if (!Validator.isValidString(username.getText())) {
            DoNotMatch.setText("Username can only contain letters!");
            DoNotMatch.setVisible(true);
            return;
        }

        // E-pastam jābūt derīgam
        if (!Validator.isValidEmail(email.getText())) {
            DoNotMatch.setText("Invalid email!");
            DoNotMatch.setVisible(true);
            return;
        }

        // Paroles jābūt vismaz 8 simbolus garai
        if (passwordText.length() < 8) {
            DoNotMatch.setText("Password must be at least 8 characters long!");
            DoNotMatch.setVisible(true);
            return;
        }

        // Pārbauda vai lietotājvārds jau nav aizņemts un ēpasts
        try (FileReader reader = new FileReader("src\\main\\resources\\org\\openjfx\\CSV\\Users.csv")){
            BufferedReader bufferedReader = new BufferedReader(reader);

            ArrayList<String[]> Data = new ArrayList<String[]>();
            String line;
            
            while ((line = bufferedReader.readLine()) != null) {
                Data.add(line.split(", "));
            }
            
            for (String[] strings : Data) {
                if (strings[0].equals(username.getText())) {
                    DoNotMatch.setText("Username already taken!");
                    DoNotMatch.setVisible(true);
                    return;
                }
                if (strings[1].equals(email.getText())) {
                    DoNotMatch.setText("Email already taken!");
                    DoNotMatch.setVisible(true);
                    return;
                }
            }

        } catch (IOException vException) {}

        // Parolēm jāsakrīt
        if (passwordText.equals(password2Text)) {
            DoNotMatch.setVisible(false);
            String usernameText = username.getText();
            String emailText = email.getText();

            CsvManeger.addDataToCSV(usernameText, emailText, passwordText);
            Sorter.sort("epasts");
            App.setRoot("Login");
        } else {
            DoNotMatch.setText("Passwords do not match!");
            DoNotMatch.setVisible(true);
            
        }
    }


    @FXML
    private void goToLogin() throws IOException
    {
        App.setRoot("Login");
    }

    
}