package org.openjfx;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class CoinToss {    
    int userchoice;
    
    
    @FXML 
    private Text coinTossText;
    static Image headsImage = new Image("file:src\\main\\resources\\org\\openjfx\\Images\\heads_coin.png");
    static Image tailsImage = new Image("file:src\\main\\resources\\org\\openjfx\\Images\\tails_coin.png");
    @FXML
    static ImageView coinImageView;
    @FXML
    StackPane root;
    @FXML
    Button tailsButton, headsButton;
    
    Random random = new Random();

    int rotationTimes = 100;
    int outcome = random.nextInt(2);
    
    

    GameController gameController = new GameController();

    public void coinToss() throws IOException {

        coinImageView = new ImageView(headsImage);
        root.getChildren().add(coinImageView);

        // Create a RotateTransition for flipping the coin
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), coinImageView);
        rotateTransition.setAxis(Rotate.Y_AXIS);
        rotateTransition.setByAngle(90); // Rotate 180 degrees (half rotation)
        rotateTransition.setCycleCount(1); // Rotate back and forth once
        rotateTransition.setInterpolator(Interpolator.EASE_BOTH);

        rotateTransition.play();
        
        

        // Change the image at the middle of the animation to simulate the flip
        rotateTransition.setOnFinished(event -> {
            if (rotationTimes >= 0)
            {
                if (coinImageView.getImage() == headsImage) {
                    coinImageView.setImage(tailsImage);
                } else {
                    coinImageView.setImage(headsImage);
                }
            }
            if (rotationTimes > 91)
            {
                rotateTransition.setByAngle(180);
                rotateTransition.setDuration(Duration.seconds(rotateTransition.getDuration().toSeconds() - 0.1));
                rotateTransition.play();
            }
            else if (rotationTimes <= 91 && rotationTimes > 15 && rotationTimes  != 30)
            {
                rotateTransition.play();
            }
            else if (rotationTimes == 30)
            {
                rotationTimes = rotationTimes - outcome;
                rotateTransition.play();
            }
            else if (rotationTimes > 1 && rotationTimes <= 15)
            {
                rotateTransition.setByAngle(180);
                rotateTransition.setDuration(Duration.seconds(rotateTransition.getDuration().toSeconds() + 0.1));
                rotateTransition.play();
            }
            else if (rotationTimes == 1)
            {
                rotateTransition.setByAngle(180);
                rotateTransition.play();
            }
            else if (rotationTimes == 0)
            {
                rotateTransition.setByAngle(90);
                rotateTransition.play();
            }
            else
            {
                if (userchoice == outcome)
                {
                    try
                    {   
                        gameController.isPlayerTurn = true;
                        App.setRoot("Enemy");
                    }
                    catch(IOException e){}
                }
                else
                {
                    try
                    {
                        gameController.isPlayerTurn = false;
                        App.setRoot("Game");
                    }
                    catch(IOException e){}
                }
            }
            
            rotationTimes--;
        });
    }
    @FXML
    private void TailsToss() throws IOException
    {
        userchoice = 0;
        coinToss();
        tailsButton.setDisable(true);
        headsButton.setDisable(true);
    }
    @FXML
    private void HeadsToss() throws IOException
    {
        userchoice = 1;
        coinToss();
        tailsButton.setDisable(true);
        headsButton.setDisable(true);
    }
}