package org.openjfx;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SettingsController {
    public static int[] currentResolution = {800, 600};
    @FXML
    private Text resolution;

    @FXML
    private CheckBox fullscreenCheckbox;

    @FXML
    private MenuButton resolutionButton;
    @FXML
    private CheckBox rememberMe;
    private int Fullscreen = 0;
    private Singleton singleton;

    GameController gameController = new GameController();

    @FXML
    private void goToGame() throws IOException {
        if (gameController.cameFrom.equals("MainMenu"))
            App.setRoot("MainMenu");
        else if (gameController.cameFrom.equals("Game")){
            App.setRoot("Game");
        }
    }

    @FXML
    private void changeResulution800x600() throws IOException {
        Stage stage = (Stage) resolution.getScene().getWindow();

        stage.setFullScreen(false);
        Fullscreen = 0;

        fullscreenCheckbox.setSelected(false);
        singleton.setCheckboxState(false);

        stage.setWidth(800);
        stage.setHeight(600);
        resolutionButton.setText("800x600");
        stage.centerOnScreen();


        ResolutionService.resolutionProperty().set(800);
        currentResolution[0] = 800;
        currentResolution[1] = 600;
    }

    @FXML
    private void changeResulution1024x768() throws IOException {
        Stage stage = (Stage) resolution.getScene().getWindow();

        stage.setFullScreen(false);
        Fullscreen = 0;

        fullscreenCheckbox.setSelected(false);
        singleton.setCheckboxState(false);

        stage.setWidth(1024);
        stage.setHeight(768);
        resolutionButton.setText("1024x768");
        stage.centerOnScreen();

        ResolutionService.resolutionProperty().set(1024);
        currentResolution[0] = 1024;
        currentResolution[1] = 768;
    }

    @FXML
    private void changeResulution1280x720() throws IOException {
        Stage stage = (Stage) resolution.getScene().getWindow();

        stage.setFullScreen(false);
        Fullscreen = 0;

        fullscreenCheckbox.setSelected(false);
        singleton.setCheckboxState(false);

        stage.setWidth(1280);
        stage.setHeight(720);
        resolutionButton.setText("1280x720");
        stage.centerOnScreen();

        ResolutionService.resolutionProperty().set(1280);
        currentResolution[0] = 1280;
        currentResolution[1] = 720;
    }

    @FXML
    private void changeResulution1280x800() throws IOException {
        Stage stage = (Stage) resolution.getScene().getWindow();

        stage.setFullScreen(false);
        Fullscreen = 0;

        fullscreenCheckbox.setSelected(false);
        singleton.setCheckboxState(false);

        stage.setWidth(1280);
        stage.setHeight(800);
        resolutionButton.setText("1280x800");
        stage.centerOnScreen();

        ResolutionService.resolutionProperty().set(1280);
        currentResolution[0] = 1280;
        currentResolution[1] = 800;
    }

    @FXML
    private void changeResulution1366x768() throws IOException {
        Stage stage = (Stage) resolution.getScene().getWindow();

        stage.setFullScreen(false);
        Fullscreen = 0;

        fullscreenCheckbox.setSelected(false);
        singleton.setCheckboxState(false);

        stage.setWidth(1366);
        stage.setHeight(768);
        resolutionButton.setText("1366x768");
        stage.centerOnScreen();

        ResolutionService.resolutionProperty().set(1366);
        currentResolution[0] = 1366;
        currentResolution[1] = 768;
    }

    @FXML
    private void changeResulution1440x900() throws IOException {
        Stage stage = (Stage) resolution.getScene().getWindow();

        stage.setFullScreen(false);
        Fullscreen = 0;

        fullscreenCheckbox.setSelected(false);
        singleton.setCheckboxState(false);

        stage.setWidth(1440);
        stage.setHeight(900);
        resolutionButton.setText("1440x900");
        stage.centerOnScreen();

        ResolutionService.resolutionProperty().set(1440);
        currentResolution[0] = 1440;
        currentResolution[1] = 900;
    }

    @FXML
    private void changeResulution1600x900() throws IOException {
        Stage stage = (Stage) resolution.getScene().getWindow();

        stage.setFullScreen(false);
        Fullscreen = 0;

        fullscreenCheckbox.setSelected(false);
        singleton.setCheckboxState(false);

        stage.setWidth(1600);
        stage.setHeight(900);
        resolutionButton.setText("1600x900");
        stage.centerOnScreen();

        ResolutionService.resolutionProperty().set(1600);
        currentResolution[0] = 1600;
        currentResolution[1] = 900;
    }

    @FXML
    private void changeResulution1920x1080() throws IOException {
        Stage stage = (Stage) resolution.getScene().getWindow();

        stage.setFullScreen(false);
        Fullscreen = 0;

        fullscreenCheckbox.setSelected(false);
        singleton.setCheckboxState(false);

        stage.setWidth(1920);
        stage.setHeight(1080);
        resolutionButton.setText("1920x1080");
        stage.centerOnScreen();

        ResolutionService.resolutionProperty().set(1920);
        currentResolution[0] = 1920;
        currentResolution[1] = 1080;
    }

    @FXML
    private void initialize() throws IOException {
        singleton = Singleton.getInstance();
        fullscreenCheckbox.setSelected(singleton.getCheckboxState());
        Fullscreen = Singleton.getInstance().getFullscreenState();
    }

    @FXML
    private void handleCheckboxAction(ActionEvent event) {
        singleton.setCheckboxState(fullscreenCheckbox.isSelected());
    }

    @FXML
    private void changeResulutionFullscreen() throws IOException {
        Stage stage = (Stage) resolution.getScene().getWindow();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        if (Fullscreen == 0) {
            stage.setFullScreen(true);
            Fullscreen = 1;
            resolutionButton.setText((int) bounds.getWidth() + "x" + (int) bounds.getHeight());

            ResolutionService.resolutionProperty().set(bounds.getWidth());

            fullscreenCheckbox.setSelected(true);
            singleton.setCheckboxState(true);
            currentResolution[0] = (int)stage.getWidth() +19;
            currentResolution[1] = (int)stage.getHeight() +30;
        } else {
            stage.setFullScreen(false);
            Fullscreen = 0;
            resolutionButton.setText("800x600");

            stage.setWidth(800);
            stage.setHeight(600);

            ResolutionService.resolutionProperty().set(800);

            fullscreenCheckbox.setSelected(false);
            singleton.setCheckboxState(false);
            currentResolution[0] = 800;
            currentResolution[1] = 600;
        }

        singleton.setFullscreenState(Fullscreen);
    }

    @FXML
    public void RemoveAccount() throws IOException {
        CsvManeger.RemoveAccount(LoginController.account);
        Stage stage = (Stage) resolution.getScene().getWindow();
        App.setRoot("Login");
        stage.setWidth(400);
        stage.setHeight(600);
        stage.centerOnScreen();
    }

    @FXML
    public void Confirmation() throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to delete your account?");

        alert.initOwner((Stage) resolution.getScene().getWindow());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            RemoveAccount();
        }
    }
    
}