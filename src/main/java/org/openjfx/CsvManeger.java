package org.openjfx;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;


public class CsvManeger {
    // Method for adding new account to the CSV file
    public static void addDataToCSV(String username, String email, String password) {
        try(FileWriter outputfile = new FileWriter("src\\main\\resources\\org\\openjfx\\CSV\\Users.csv", true)) { 
                 
            BufferedWriter writer = new BufferedWriter(outputfile); 
    
            writer.write(username + ", " + email + ", " + password); 
    
            writer.newLine();
            writer.close();
                
        }catch(IOException vException){}
    }
    
    // Method for removing an account from the CSV file
    @FXML
    public static void RemoveAccount(String account) throws IOException
    {
        FileReader reader = new FileReader("src\\main\\resources\\org\\openjfx\\CSV\\Users.csv");
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<String> Data = new ArrayList<String>();
        String line;
        
        while ((line = bufferedReader.readLine()) != null) {
            Data.add(line);
        }
        
        Data.remove(account);
        
        FileWriter fileWriter = new FileWriter("src\\main\\resources\\org\\openjfx\\CSV\\Users.csv");
        BufferedWriter writer = new BufferedWriter(fileWriter);
        for (String lines : Data)
        {
            writer.write(lines);
            writer.newLine();
        }
        writer.close();
        bufferedReader.close();
    }

    // Method for getting all accounts from the CSV file
    public static List<String> getAllAccountList() throws IOException
    {
        FileReader reader = new FileReader("src\\main\\resources\\org\\openjfx\\CSV\\Users.csv");
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<String> Data = new ArrayList<String>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            Data.add(line);
        }
        bufferedReader.close();
        return Data;
    }

    // Method for getting all accounts from the CSV file for the leaderboard
    public static List<Object> getLeaderboardList() throws IOException
    {
        FileReader reader = new FileReader("src\\main\\resources\\org\\openjfx\\CSV\\Leaderboard.csv");
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<Object> Data = new ArrayList<Object>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            Data.add(line);
        }
        bufferedReader.close();
        return Data;
    }

    // Method for adding a score to the leaderboard
    public static void addScore(String username, int win, int loss) throws IOException
    {
        FileReader reader = new FileReader("src\\main\\resources\\org\\openjfx\\CSV\\Leaderboard.csv");
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<String[]> Data = new ArrayList<String[]>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] lineValues = line.split(", ");
            if (lineValues[0].equals(username))
            {
                lineValues[1] = String.valueOf(Integer.parseInt(lineValues[1]) + win);
                lineValues[2] = String.valueOf(Integer.parseInt(lineValues[2]) + loss);
                lineValues[3] = String.valueOf(Integer.parseInt(lineValues[1])*100/(Integer.parseInt(lineValues[1])+Integer.parseInt(lineValues[2])));
            }
            Data.add(lineValues);
        }

        FileWriter fileWriter = new FileWriter("src\\main\\resources\\org\\openjfx\\CSV\\Leaderboard.csv");
        BufferedWriter writer = new BufferedWriter(fileWriter);
        for (String[] lines : Data)
        {
            writer.write(lines[0] + ", " + lines[1] + ", " + lines[2] + ", " + lines[3]);
            writer.newLine();
        }
        writer.close();

        bufferedReader.close();
    }

    // Method for adding a score to the leaderboard
    public static void addScore(String username, int win) throws IOException
    {
        addScore(username, win, 0);
    }

    // Adds a default score to the leaderboard for a new account
    public static void defaultScore(String username) throws IOException
    {
        FileWriter outputfile = new FileWriter("src\\main\\resources\\org\\openjfx\\CSV\\Leaderboard.csv", true);
                 
        BufferedWriter writer = new BufferedWriter(outputfile); 

        writer.write(username + ", 0, 0, 0"); 

        writer.newLine();
        writer.close();
    }
} 
