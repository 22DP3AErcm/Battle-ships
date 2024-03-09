package org.openjfx;

import java.io.*;


public class CsvManeger {
    
    public void addDataToCSV(String username, String email, String password) {
        try(FileWriter outputfile = new FileWriter("src\\main\\resources\\org\\openjfx\\CSV\\Users.csv", true)) { 
                 
            BufferedWriter writer = new BufferedWriter(outputfile); 
    
            writer.write(username + ", " + email + ", " + password); 
    
            writer.newLine();
            writer.close();
                
        }catch(IOException vException){}
    }

} 