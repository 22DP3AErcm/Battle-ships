package org.openjfx;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Enemy {
    private static Map<Ships, List<String>> enemyShips = new HashMap<>();
    List<Integer> shipWidths = Arrays.asList(200, 160, 160, 120, 120, 120, 80, 80, 80);
    Random random = new Random();
    boolean shipIsInvalid = false;

    // Generate random coordinates for the enemy ships
    public void generateEnemyShips() {
        for (int i = 0; i < 9; i++) {
            while (shipIsInvalid == false){
                int x = random.nextInt(9);
                int y = random.nextInt(9);
                int orientation = random.nextInt(2);

                for (i = 0; i < shipWidths.size() / 40; i++) {
                    if (orientation == 0) {
                        enemyShips.put();
                    } else {
                        
                    }
                }
            }
        }
    }
}
