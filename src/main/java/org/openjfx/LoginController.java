package org.openjfx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class LoginController {
    @FXML
    private TextField email, password;    
    
    @FXML
    private Text WrongLogin;


    @FXML
    private void goToRegister() throws IOException
    {
        App.setRoot("Register");
    }

    @FXML
    private void login() throws IOException
    {
        String emailText = email.getText();
        String passwordText = password.getText();
        
        Boolean login = false;
        

        try (FileReader reader = new FileReader("src\\main\\resources\\org\\openjfx\\CSV\\Users.csv")){
            BufferedReader bufferedReader = new BufferedReader(reader);

            ArrayList<String[]> Data = new ArrayList<String[]>();
            String line;
            
            while ((line = bufferedReader.readLine()) != null) {
                Data.add(line.split(", "));
            }
            for (String[] strings : Data) {
                if (strings[1].equals(emailText) && strings[2].equals(passwordText)) {
                    System.out.println("Login successful");
                    
                    login = true;
                    WrongLogin.setVisible(false);
                    break;
                }
            }
            if (login) {
                Stage stage = (Stage) email.getScene().getWindow();
                
                stage.setResizable(true);

                stage.setWidth(800);
                stage.setHeight(600);
                App.setRoot("Game");
                
            } else {
                WrongLogin.setVisible(true);
            }

        }catch(IOException vException){

        }
        
        
        
    }




}
