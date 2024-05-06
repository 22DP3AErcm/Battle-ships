package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */

public class App extends Application {

    private static Scene scene;

    // Creates the scene and sets the stage
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Login"));
        stage.setScene(scene);

        Image icon = new Image("file:src\\main\\resources\\org\\openjfx\\Images\\Icon2done.png");
        stage.getIcons().add(icon);
        stage.setTitle("Battle ships");
        stage.setResizable(false);

        stage.show();
    }

    // Change the scene
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    // Load the FXML file
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    // Start the application
    public static void main(String[] args) {
        launch();
    }
}