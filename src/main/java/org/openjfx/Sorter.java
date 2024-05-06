package org.openjfx;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.io.IOException;

public class Sorter {
    // Method for sorting the CSV file
    public void sort(String SortType) {
        try (FileReader reader = new FileReader("src\\main\\resources\\org\\openjfx\\CSV\\Users.csv")){
            BufferedReader bufferedReader = new BufferedReader(reader);

            ArrayList<String[]> Data = new ArrayList<String[]>();
            String line;
            
            while ((line = bufferedReader.readLine()) != null) {
                Data.add(line.split(", "));
            
            }

            switch (SortType) {
                case "vards":
                    SortColum(Data, 0);
                    writer(Data);
                    break;
                case "epasts":
                    SortColum(Data, 1);
                    writer(Data);
                    break;
                default:
                    break;
            }
        }catch(IOException vException){}
        
    }

    // Method for sorting the column
    private void SortColum(ArrayList<String[]> Data, Integer index) {
        Collections.sort(Data, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                if (o1.length > index && o2.length > index) {
                    
                    if (o1[index] == null) {
                        return 1;
                    } else if (o2[index] == null) {
                        return -1;
                    }
                    
                    return o1[index].compareTo(o2[index]);
                } else {
                    
                    return 0;
                }
            }
            
        });
    }

    // Method for writing the sorted data to the CSV file
    private void writer(ArrayList<String[]> Data){
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src\\main\\resources\\org\\openjfx\\CSV\\Users.csv"))){

                for (String[] i : Data) {
                    String NewString = i[0] + ", " + i[1] + ", " + i[2];
                    
                    
                    bufferedWriter.write(NewString);  
                    bufferedWriter.newLine();
                }
            
                bufferedWriter.close();
               
            }
            catch(IOException vException){

            }
    }

}