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
}