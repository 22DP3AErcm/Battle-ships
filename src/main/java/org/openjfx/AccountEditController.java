package org.openjfx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AccountEditController {

    String[] account = LoginController.account.split(", ");

    @FXML
    private Text DoNotMatch;

    @FXML
    private TextField username, email, password;

    GameController gameController = new GameController();

    @FXML
    public void goToGame() throws IOException {
        if (gameController.cameFrom.equals("MainMenu"))
            App.setRoot("MainMenu");
        else if (gameController.cameFrom.equals("Game")){
            App.setRoot("Game");
        }
    }

    @FXML
    public void initialize() throws IOException {
        username.setText(account[0]);
        email.setText(account[1]);
        password.setText(account[2]);
    }

    @FXML
    private void rewriteAccount() throws IOException {
        String passwordText = password.getText();

        // Visiem logiem jābūt aizpildītiem
        if (username.getText().isEmpty() || email.getText().isEmpty() || password.getText().isEmpty()) {
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
            DoNotMatch.setText("Username can only contain letters and numbers and can only 15 characters long!");
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

        FileReader reader = new FileReader("src\\main\\resources\\org\\openjfx\\CSV\\Users.csv");
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<String> Data = new ArrayList<String>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            Data.add(line);
        }
        if (!Data.contains(username.getText() + ", " + email.getText() + ", " + password.getText())) {
            CsvManeger.RemoveAccount(LoginController.account);
            CsvManeger.addDataToCSV(username.getText(), email.getText(), password.getText());
            LoginController.account = username.getText() + ", " + email.getText() + ", " + password.getText();
        }
    }
}
