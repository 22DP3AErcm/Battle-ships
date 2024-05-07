package org.openjfx;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class Testgame {
    @Test
    public void Gridtest() {
        Enemy enemy = new Enemy();
        int[] cordinates = {01, 02, 03};
        assertEquals(true, enemy.validShipPlacement(cordinates));
    }
    
    @Test
    public void ShipPlacement() {
        Enemy enemy = new Enemy();
        int[] cordinates = {99, 02, 03};
        assertEquals(false, enemy.validShipPlacement(cordinates));
    }

    @Test
    public void enemyShipGeneration() {
        Enemy enemy = new Enemy();
        enemy.generateEnemyShips();
        assertEquals(9, enemy.enemyShips.size());
    }

    @Test
    public void convertCordinates() {
        Enemy enemy = new Enemy();
        String cordinates = "B3";
        int[] cordinatesArray = enemy.convertCoordinate(cordinates);
        assertEquals(1, cordinatesArray[0]);
        assertEquals(2, cordinatesArray[1]);
    }
    
    @Test
    public void convertCordinates2() {
        Enemy enemy = new Enemy();
        int[] cordinates = {12};
        String[] cordinatesString = enemy.convertCoordinate(cordinates);
        assertEquals("A2", cordinatesString[0]);
    }
} 