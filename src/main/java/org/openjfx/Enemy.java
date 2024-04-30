package org.openjfx;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Enemy {
    public static Map<String, List<String>> enemyShips = new HashMap<>();
    List<Integer> shipWidths = Arrays.asList(200, 160, 160, 120, 120, 120, 80, 80, 80);
    Random random = new Random();
    boolean shipIsInvalid = false;
    int atemp = 0;
    
    // Generate random coordinates for the enemy ships
    public void generateEnemyShips() {
        while (!shipIsInvalid == true) {
            enemyShips.clear();
            atemp = 0;

            // Generate random coordinates for each ship
            for (int i = 0; i < 9;) {
                int x = random.nextInt(9) + 1;
                int y = random.nextInt(9) + 1;
                int orientation = random.nextInt(2);
                atemp++;

                if (atemp >= 500){
                    enemyShips.clear();
                    atemp = 0;
                    i = 0; 
                    // prints all ships in map
                    for (Map.Entry<String, List<String>> entry : enemyShips.entrySet()) {
                        String ship = entry.getKey();
                        List<String> coordinates = entry.getValue();
                        System.out.println(ship + " " + coordinates);
                    }
                }
                

                if (orientation == 0) {
                    if (shipWidths.get(i) == 80){
                        String stringCordinates = String.valueOf(x) + String.valueOf(y)+ "," + String.valueOf(x+1) + String.valueOf(y);
                        int[] cordinates = cordinate(stringCordinates.split(","));
                        if (!onShip(cordinates) && validShipPlacement(cordinates) && !areShipsAdjacent(cordinates)){
                            String ship = String.valueOf(random.nextInt(100000)) + "i";
                            enemyShips.put(ship, Arrays.asList(convertCoordinate(cordinates)));
                            i++;
                        }
                    }
                    else if (shipWidths.get(i) == 160){
                        String stringCordinates = String.valueOf(x) + String.valueOf(y)+ "," + String.valueOf(x+1) + String.valueOf(y) + "," + String.valueOf(x+2) + String.valueOf(y) + "," + String.valueOf(x+3) + String.valueOf(y);
                        int[] cordinates = cordinate(stringCordinates.split(","));
                        if (!onShip(cordinates) && validShipPlacement(cordinates) && !areShipsAdjacent(cordinates)){
                            String ship = String.valueOf(random.nextInt(100000)) + "i";
                            enemyShips.put(ship, Arrays.asList(convertCoordinate(cordinates)));
                            i++;
                        }
                    }
                    else if (shipWidths.get(i) == 120){
                        String stringCordinates = String.valueOf(x) + String.valueOf(y)+ "," + String.valueOf(x+1) + String.valueOf(y) + "," + String.valueOf(x+2) + String.valueOf(y);
                        int[] cordinates = cordinate(stringCordinates.split(","));
                        if (!onShip(cordinates) && validShipPlacement(cordinates) && !areShipsAdjacent(cordinates)){
                            String ship = String.valueOf(random.nextInt(100000)) + "i";
                            enemyShips.put(ship, Arrays.asList(convertCoordinate(cordinates)));
                            i++;
                        }
                    }
                    else if(shipWidths.get(i) == 200){
                        String stringCordinates = String.valueOf(x) + String.valueOf(y)+ "," + String.valueOf(x+1) + String.valueOf(y) + "," + String.valueOf(x+2) + String.valueOf(y) + "," + String.valueOf(x+3) + String.valueOf(y) + "," + String.valueOf(x+4) + String.valueOf(y);
                        int[] cordinates = cordinate(stringCordinates.split(","));
                        if (!onShip(cordinates) && validShipPlacement(cordinates) && !areShipsAdjacent(cordinates)){
                            String ship = String.valueOf(random.nextInt(100000)) + "i";
                            enemyShips.put(ship, Arrays.asList(convertCoordinate(cordinates)));
                            i++;
                        }
                    }
                } else {
                    if (shipWidths.get(i) == 80){
                        String stringCordinates = String.valueOf(x) + String.valueOf(y)+ "," + String.valueOf(x) + String.valueOf(y+1);
                        int[] cordinates = cordinate(stringCordinates.split(","));
                        if (!onShip(cordinates) && validShipPlacement(cordinates) && !areShipsAdjacent(cordinates)){
                            String ship = String.valueOf(random.nextInt(100000)) + "i";
                            enemyShips.put(ship, Arrays.asList(convertCoordinate(cordinates)));
                            i++;
                        }
                    }
                    else if (shipWidths.get(i) == 160){
                        String stringCordinates = String.valueOf(x) + String.valueOf(y)+ "," + String.valueOf(x) + String.valueOf(y+1) + "," + String.valueOf(x) + String.valueOf(y+2) + "," + String.valueOf(x) + String.valueOf(y+3);
                        int[] cordinates = cordinate(stringCordinates.split(","));
                        if (!onShip(cordinates) && validShipPlacement(cordinates) && !areShipsAdjacent(cordinates)){
                            String ship = String.valueOf(random.nextInt(100000)) + "i";
                            enemyShips.put(ship, Arrays.asList(convertCoordinate(cordinates)));
                            i++;
                        }
                    }
                    else if (shipWidths.get(i) == 120){
                        String stringCordinates = String.valueOf(x) + String.valueOf(y)+ "," + String.valueOf(x) + String.valueOf(y+1) + "," + String.valueOf(x) + String.valueOf(y+2);
                        int[] cordinates = cordinate(stringCordinates.split(","));
                        if (!onShip(cordinates) && validShipPlacement(cordinates) && !areShipsAdjacent(cordinates)){
                            String ship = String.valueOf(random.nextInt(100000)) + "i";
                            enemyShips.put(ship, Arrays.asList(convertCoordinate(cordinates)));
                            i++;
                        }
                    }
                    else if(shipWidths.get(i) == 200){
                        String stringCordinates = String.valueOf(x) + String.valueOf(y)+ "," + String.valueOf(x) + String.valueOf(y+1) + "," + String.valueOf(x) + String.valueOf(y+2) + "," + String.valueOf(x) + String.valueOf(y+3) + "," + String.valueOf(x) + String.valueOf(y+4);
                        int[] cordinates = cordinate(stringCordinates.split(","));
                        if (!onShip(cordinates) && validShipPlacement(cordinates) && !areShipsAdjacent(cordinates)){
                            String ship = String.valueOf(random.nextInt(100000)) + "i";
                            enemyShips.put(ship, Arrays.asList(convertCoordinate(cordinates)));
                            i++;
                        }
                    }
                }
                if (enemyShips.size() == 9) {
                    shipIsInvalid = true;
                }
            }
        }       
    }

    public boolean validShipPlacement(int[] coordinates) {
        for (int i = 0; i < coordinates.length; i++) {
            int row = coordinates[i] / 10;
            int col = coordinates[i] % 10;
            if (row < 0 || row > 8 || col < 0 || col > 8) {
                return false;
            }
        }
    
        return true;
    }

    public boolean onShip(int[] currentShipCoordinates) {
        String[] coordinateStr = convertCoordinate(currentShipCoordinates);
    
        // Iterate over all the enemy ships
        for (List<String> enemyShipCoordinates : enemyShips.values()) {
            // Check if the given coordinates are on the enemy ship
            for (String enemyCoordinate : enemyShipCoordinates) {
                for (String coordinate : coordinateStr) {
                    if (coordinate.equals(enemyCoordinate)) {
                        return true;
                    }
                }
            }
        }
    
        // The given coordinates are not on any enemy ship
        return false;
    }
    
    public boolean areShipsAdjacent(int[] currentShipCoordinates) {
        String[] coordinateStr = convertCoordinate(currentShipCoordinates);

        // Iterate over all the enemy ships
        for (List<String> enemyShipCoordinates : enemyShips.values()) {
            // Check if the given coordinates are adjacent to the enemy ship
            for (String enemyCoordinate : enemyShipCoordinates) {
                for (String coordinate : coordinateStr) {
                    int[] enemyCordinates = convertCoordinate(enemyCoordinate);
                    int[] cordinates = convertCoordinate(coordinate);
                    if (Math.abs(enemyCordinates[0] - cordinates[0]) <= 1 && Math.abs(enemyCordinates[1] - cordinates[1]) <= 1) {
                        return true;
                    }
                }
            }
        }
        // The given coordinates are not adjacent to any enemy ship
        return false;
    }
    
    public String[] convertCoordinate(int[] coordinates) {
        String[] convertedCoordinates = new String[coordinates.length];
        
        for (int i = 0; i < coordinates.length; i++) {
            int firstDigit = coordinates[i] / 10;
            int secondDigit = coordinates[i] % 10;
            char letter = (char) (firstDigit + 64);
            convertedCoordinates[i] = letter + Integer.toString(secondDigit);
        }
        
        return convertedCoordinates;
    }

    public  int[] convertCoordinate(String coordinate) {
        int x = coordinate.charAt(0) - 'A';
        int y = Integer.parseInt(coordinate.substring(1)) - 1;
        return new int[]{x, y};
    }

    public int[] cordinate(String[] coordinate) {
        int[] intArray = new int[coordinate.length];

        for (int i = 0; i < coordinate.length; i++) {
            intArray[i] = Integer.parseInt(coordinate[i]);
        }
        return intArray;
    }
}
