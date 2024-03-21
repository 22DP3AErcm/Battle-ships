package org.openjfx;

import java.io.IOException;


import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SettingsController {
    @FXML
    private Text resolution;

    @FXML
    private MenuButton resolutionButton;
    @FXML
    private CheckBox rememberMe;
    private int Fullscreen = 0;

    @FXML
    private void goToGame() throws IOException
    {
        App.setRoot("Game");
    }

    @FXML
    private void changeResulution800x600() throws IOException
    {
        Stage stage = (Stage) resolution.getScene().getWindow();
        stage.setWidth(800);
        stage.setHeight(600);
        resolutionButton.setText("800x600");
        stage.centerOnScreen();
        
    }

    @FXML
    private void changeResulution1024x768() throws IOException
    {
        Stage stage = (Stage) resolution.getScene().getWindow();
        stage.setWidth(1024);
        stage.setHeight(768);
        resolutionButton.setText("1024x768");
        stage.centerOnScreen();
        
    }

    @FXML
    private void changeResulution1280x720() throws IOException
    {
        Stage stage = (Stage) resolution.getScene().getWindow();
        stage.setWidth(1280);
        stage.setHeight(720);
        resolutionButton.setText("1280x720");
        stage.centerOnScreen();
    }
    @FXML
    private void changeResulution1280x800() throws IOException
    {
        Stage stage = (Stage) resolution.getScene().getWindow();
        stage.setWidth(1280);
        stage.setHeight(800);
        resolutionButton.setText("1280x800");
        stage.centerOnScreen();
    }
    @FXML
    private void changeResulution1366x768() throws IOException
    {
        Stage stage = (Stage) resolution.getScene().getWindow();
        stage.setWidth(1366);
        stage.setHeight(768);
        resolutionButton.setText("1366x768");
        stage.centerOnScreen();
    }
    @FXML
    private void changeResulution1440x900() throws IOException
    {
        Stage stage = (Stage) resolution.getScene().getWindow();
        stage.setWidth(1440);
        stage.setHeight(900);
        resolutionButton.setText("1440x900");
        stage.centerOnScreen();
    }
    @FXML
    private void changeResulution1600x900() throws IOException
    {
        Stage stage = (Stage) resolution.getScene().getWindow();
        stage.setWidth(1600);
        stage.setHeight(900);
        resolutionButton.setText("1600x900");
        stage.centerOnScreen();
    }

    @FXML
    private void changeResulution1920x1080() throws IOException
    {
        Stage stage = (Stage) resolution.getScene().getWindow();
        stage.setWidth(1920);
        stage.setHeight(1080);
        resolutionButton.setText("1920x1080");
        stage.centerOnScreen();
    }

    @FXML
    private void changeResulutionFullscreen() throws IOException
    {
        Stage stage = (Stage) resolution.getScene().getWindow();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        if (Fullscreen == 0) {
            stage.setFullScreen(true);
            Fullscreen = 1;
            resolutionButton.setText((int)bounds.getWidth() + "x" + (int)bounds.getHeight());
        } else {
            stage.setFullScreen(false);
            Fullscreen = 0;
        }
    }
}
