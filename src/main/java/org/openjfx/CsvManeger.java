package org.openjfx;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.stage.Stage;


public class CsvManeger {
    
    public void addDataToCSV(String username, String email, String password) {
        try(FileWriter outputfile = new FileWriter("src\\main\\resources\\org\\openjfx\\CSV\\Users.csv", true)) { 
                 
            BufferedWriter writer = new BufferedWriter(outputfile); 
    
            writer.write(username + ", " + email + ", " + password); 
    
            writer.newLine();
            writer.close();
                
        }catch(IOException vException){}
    }
    
    @FXML
    public static void RemoveAccount(Stage stage) throws IOException
    {
        FileReader reader = new FileReader("src\\main\\resources\\org\\openjfx\\CSV\\Users.csv");
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<String> Data = new ArrayList<String>();
        String line;
        
        while ((line = bufferedReader.readLine()) != null) {
            Data.add(line);
        }
        
        Data.remove(LoginController.account);
        
        FileWriter fileWriter = new FileWriter("src\\main\\resources\\org\\openjfx\\CSV\\Users.csv");
        BufferedWriter writer = new BufferedWriter(fileWriter);
        for (String lines : Data)
        {
            writer.write(lines);
            writer.newLine();
        }
        writer.close();
        App.setRoot("Login");
        stage.setWidth(400);
        stage.setHeight(600);
        stage.centerOnScreen();
    }

} 
