package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.Random;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class CoinToss {
    @FXML 
    private Text coinTossText;
    Random random = new Random();
    GameController gameController = new GameController();

    public void coinToss() {
        gameController.isPlayerTurn = true;
    }
    
}