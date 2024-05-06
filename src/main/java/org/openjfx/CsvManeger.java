package org.openjfx;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;


public class CsvManeger {

    public static void addDataToCSV(String username, String email, String password) {
        try(FileWriter outputfile = new FileWriter("src\\main\\resources\\org\\openjfx\\CSV\\Users.csv", true)) { 
                 
            BufferedWriter writer = new BufferedWriter(outputfile); 
    
            writer.write(username + ", " + email + ", " + password); 
    
            writer.newLine();
            writer.close();
                
        }catch(IOException vException){}
    }
    
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
    public static void addScore(String username, int win) throws IOException
    {
        addScore(username, win, 0);
    }
    public static void defaultScore(String username) throws IOException
    {
        FileWriter outputfile = new FileWriter("src\\main\\resources\\org\\openjfx\\CSV\\Leaderboard.csv", true);
                 
        BufferedWriter writer = new BufferedWriter(outputfile); 

        writer.write(username + ", 0, 0, 0"); 

        writer.newLine();
        writer.close();
    }
} 
