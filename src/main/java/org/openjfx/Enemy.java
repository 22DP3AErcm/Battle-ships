package org.openjfx;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Enemy {
    private static Map<String, List<String>> enemyShips = new HashMap<>();
    List<Integer> shipWidths = Arrays.asList(200, 160, 160, 120, 120, 120, 80, 80, 80);
    Random random = new Random();
    boolean shipIsInvalid = false;
    int atemp = 0;
    
    // Generate random coordinates for the enemy ships
    public void generateEnemyShips() {
        for (int i = 0; i < 9; i++) {
            while (shipIsInvalid == false){
                int x = random.nextInt(9);
                int y = random.nextInt(9);
                int orientation = random.nextInt(2);
                atemp++;
                System.out.println(atemp);
                for (i = 0; i < shipWidths.size(); i++){
                    x = random.nextInt(9);
                    y = random.nextInt(9);
                    orientation = random.nextInt(2);
                    atemp++;
                    System.out.println(atemp+1);
                    System.out.println("size " + enemyShips.size());
                    if (atemp == 100){
                        enemyShips.clear();
                        System.out.println("Cleared");
                        atemp = 0;
                    }
                    if (orientation == 0) {
                        if (shipWidths.get(i) == 80){
                            String stringCordinates = String.valueOf(x) + String.valueOf(y)+ "," + String.valueOf(x+1) + String.valueOf(y);
                            int[] cordinates = cordinate(stringCordinates.split(","));
                            if (!onShip(enemyShips) && validShipPlacement(cordinates) && !areShipsAdjacent(enemyShips)){
                                String ship = String.valueOf(random.nextInt(100000)) + "i";
                                enemyShips.put(ship, Arrays.asList(convertCoordinate(cordinates)));
                            }
                        }
                        else if (shipWidths.get(i) == 160){
                            String stringCordinates = String.valueOf(x) + String.valueOf(y)+ "," + String.valueOf(x+1) + String.valueOf(y) + "," + String.valueOf(x+2) + String.valueOf(y);
                            int[] cordinates = cordinate(stringCordinates.split(","));
                            if (!onShip(enemyShips) && validShipPlacement(cordinates) && !areShipsAdjacent(enemyShips)){
                                String ship = String.valueOf(random.nextInt(100000)) + "i";
                                enemyShips.put(ship, Arrays.asList(convertCoordinate(cordinates)));
                            }
                        }
                        else if (shipWidths.get(i) == 120){
                            String stringCordinates = String.valueOf(x) + String.valueOf(y)+ "," + String.valueOf(x+1) + String.valueOf(y) + "," + String.valueOf(x+2) + String.valueOf(y);
                            int[] cordinates = cordinate(stringCordinates.split(","));
                            if (!onShip(enemyShips) && validShipPlacement(cordinates) && !areShipsAdjacent(enemyShips)){
                                String ship = String.valueOf(random.nextInt(100000)) + "i";
                                enemyShips.put(ship, Arrays.asList(convertCoordinate(cordinates)));
                            }
                        }
                        else if(shipWidths.get(i) == 200){
                            String stringCordinates = String.valueOf(x) + String.valueOf(y)+ "," + String.valueOf(x+1) + String.valueOf(y) + "," + String.valueOf(x+2) + String.valueOf(y) + "," + String.valueOf(x+3) + String.valueOf(y);
                            int[] cordinates = cordinate(stringCordinates.split(","));
                            if (!onShip(enemyShips) && validShipPlacement(cordinates) && !areShipsAdjacent(enemyShips)){
                                String ship = String.valueOf(random.nextInt(100000)) + "i";
                                enemyShips.put(ship, Arrays.asList(convertCoordinate(cordinates)));
                            }
                        }
                    } else {
                        if (shipWidths.get(i) == 80){
                            String stringCordinates = String.valueOf(x) + String.valueOf(y)+ "," + String.valueOf(x) + String.valueOf(y+1);
                            int[] cordinates = cordinate(stringCordinates.split(","));
                            if (!onShip(enemyShips) && validShipPlacement(cordinates) && !areShipsAdjacent(enemyShips)){
                                String ship = String.valueOf(random.nextInt(100000)) + "i";
                                enemyShips.put(ship, Arrays.asList(convertCoordinate(cordinates)));
                            }
                        }
                        else if (shipWidths.get(i) == 160){
                            String stringCordinates = String.valueOf(x) + String.valueOf(y)+ "," + String.valueOf(x) + String.valueOf(y+1) + "," + String.valueOf(x) + String.valueOf(y+2);
                            int[] cordinates = cordinate(stringCordinates.split(","));
                            if (!onShip(enemyShips) && validShipPlacement(cordinates) && !areShipsAdjacent(enemyShips)){
                                String ship = String.valueOf(random.nextInt(100000)) + "i";
                                enemyShips.put(ship, Arrays.asList(convertCoordinate(cordinates)));
                            }
                        }
                        else if (shipWidths.get(i) == 120){
                            String stringCordinates = String.valueOf(x) + String.valueOf(y)+ "," + String.valueOf(x) + String.valueOf(y+1) + "," + String.valueOf(x) + String.valueOf(y+2);
                            int[] cordinates = cordinate(stringCordinates.split(","));
                            if (!onShip(enemyShips) && validShipPlacement(cordinates) && !areShipsAdjacent(enemyShips)){
                                String ship = String.valueOf(random.nextInt(100000)) + "i";
                                enemyShips.put(ship, Arrays.asList(convertCoordinate(cordinates)));
                            }
                        }
                        else if(shipWidths.get(i) == 200){
                            String stringCordinates = String.valueOf(x) + String.valueOf(y)+ "," + String.valueOf(x) + String.valueOf(y+1) + "," + String.valueOf(x) + String.valueOf(y+2) + "," + String.valueOf(x) + String.valueOf(y+3);
                            int[] cordinates = cordinate(stringCordinates.split(","));
                            if (!onShip(enemyShips) && validShipPlacement(cordinates) && !areShipsAdjacent(enemyShips)){
                                String ship = String.valueOf(random.nextInt(100000)) + "i";
                                enemyShips.put(ship, Arrays.asList(convertCoordinate(cordinates)));
                            }
                        }
                    }
                }
                
                if (enemyShips.size() == 9){
                    shipIsInvalid = true;
                    for (Map.Entry<String, List<String>> entry : enemyShips.entrySet()) {
                        String ship = entry.getKey();
                        List<String> coordinates = entry.getValue();
                        System.out.println(ship + ": " + coordinates);
                    }
                }
            }
        }
    }

    public boolean validShipPlacement(int[] coordinates) {
        for (int i = 0; i < coordinates.length; i++) {
            if (coordinates[i] < 0 || coordinates[i] > 9) {
                return false;
            }
        }

        return true;
    }

    public boolean areShipsAdjacent(Map<String, List<String>> objectLocations) {
        // Check if ships are adjacent using map of ships and their coordinates
        for (Map.Entry<String, List<String>> entry : objectLocations.entrySet()) {
            String ship = entry.getKey();
            List<String> coordinates = entry.getValue();
    
            for (int i = 0; i < coordinates.size(); i++) {
                String[] splitCoordinates = coordinates.get(i).split(",");
                int x = Integer.parseInt(splitCoordinates[0]);
                int y = Integer.parseInt(splitCoordinates[1]);
    
                if (objectLocations.containsValue(x + 1 + "," + y) || objectLocations.containsValue(x - 1 + "," + y) || objectLocations.containsValue(x + "," + (y + 1)) || objectLocations.containsValue(x + "," + (y - 1))) {
                    return true;
                }
            }
        }
    
        return false;
    }

    public boolean onShip(Map<String, List<String>> objectLocations) {
        for (Map.Entry<String, List<String>> entry : objectLocations.entrySet()) {
            String ship = entry.getKey();
            List<String> coordinates = entry.getValue();

            for (int i = 0; i < coordinates.size(); i++) {
                String[] splitCoordinates = coordinates.get(i).split(",");
                int x = Integer.parseInt(splitCoordinates[0]);
                int y = Integer.parseInt(splitCoordinates[1]);

                if (objectLocations.containsValue(x + "," + y)) {
                    return true;
                }
            }
        }

        return false;
    }
    
    public String[] convertCoordinate(int[] coordinates) {
        String[] convertedCoordinates = new String[coordinates.length];
        
        for (int i = 0; i < coordinates.length; i++) {
            char letter = (char) (coordinates[i] + 64);
            convertedCoordinates[i] = letter + Integer.toString(i + 1);
        }
        
        return convertedCoordinates;
    }

    public int[] cordinate(String[] coordinate) {
        int[] intArray = new int[coordinate.length];

        for (int i = 0; i < coordinate.length; i++) {
            intArray[i] = Integer.parseInt(coordinate[i]);
        }
        return intArray;
    }
}
