package org.openjfx;

import java.io.IOException;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainMenuController {
    @FXML
    VBox vBox;
    @FXML
    HBox hBox;
    @FXML
    TableView<LeaderboardData> tableView;
    @FXML
    TableColumn<LeaderboardData, String> username;
    @FXML
    TableColumn<LeaderboardData, Integer> wins, losses;
    @FXML
    TableColumn<LeaderboardData, Double> winLoss;
    @FXML
    AnchorPane anchorPane;
    GameController gameController = new GameController();
    Enemy enemy = new Enemy();
    Bullets bullets = new Bullets(anchorPane);

    // Initialize the MainMenu scene
    public void initialize()
    {
        Platform.runLater(() -> {
            hBox.setPrefHeight(SettingsController.currentResolution[0]-62);
            vBox.setPrefWidth((SettingsController.currentResolution[0]-40)/2);
            vBox.setPrefHeight(SettingsController.currentResolution[0]-62);
            try {
                addDataToTable();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    // Method for switching to the Game scene if the button is clicked
    public void startGame() throws Exception {
        gameController.shipLocations.clear();
        enemy.enemyShips.clear();
        bullets.destroyedShips.clear();
        bullets.bulletsShotEnemy.clear();
        bullets.bulletsShotPlayer.clear();
        bullets.AllShots.clear();
        bullets.gameOver = false;
        bullets.shipDestroyed = false;
        gameController.gameStarted = 0;
        gameController.ships.clear();
        bullets.isHit = false;
        bullets.destroyedShipsEnemy.clear();
        bullets.destroyedShipsPlayer.clear();
        App.setRoot("Game");
    }

    // Switch to the Settings scene
    public void setting() throws Exception {
        App.setRoot("Settings");
        gameController.cameFrom = "MainMenu";
    }

    // Switch to the AccountEdit scene
    public void AccountEdit() throws Exception {
        if (LoginController.account.equals("a, a, a")) {
            App.setRoot("AccountEditAdmin");
        }
        else
        {
            System.out.println(LoginController.account);
            App.setRoot("AccountEdit");
        }
        gameController.cameFrom = "MainMenu";
    }

    // Exit the game
    public void exitGame() {
        System.exit(0);
    }

    // Add data to the table
    @FXML
    private void addDataToTable() throws IOException {
        tableView.setPrefWidth((SettingsController.currentResolution[0]-40)/2);
        tableView.setPrefHeight(SettingsController.currentResolution[0]-62);
        username.setPrefWidth(tableView.getPrefWidth()/2);
        wins.setPrefWidth((tableView.getPrefWidth()/2)/3);
        losses.setPrefWidth((tableView.getPrefWidth()/2)/3);
        winLoss.setPrefWidth((tableView.getPrefWidth()/2)/3);        
        List<Object> leaderboard = CsvManeger.getLeaderboardList();
        ObservableList<LeaderboardData> data = FXCollections.observableArrayList();
        for (int i = 0; i < leaderboard.size(); i++) {
            data.add(new LeaderboardData(leaderboard.get(i).toString().split(", ")));
        }
    
        tableView.setItems(data);
        
        // Ads username, wins, losses, and win/loss ratio to the table
        username.setCellValueFactory(cellData -> {
            String name = cellData.getValue().usernameProperty().get();
            return name != null ?
                    new SimpleObjectProperty<>(name) :
                    null;
        });
    
        wins.setCellValueFactory(cellData -> {
            Integer wins = cellData.getValue().winsProperty().get();
            return wins != null ?
                    new SimpleObjectProperty<>(wins) :
                    null;
        });
    
        losses.setCellValueFactory(cellData -> {
            Integer losses = cellData.getValue().lossesProperty().get();
            return losses != null ?
                    new SimpleObjectProperty<>(losses) :
                    null;
        });

        winLoss.setCellValueFactory(cellData -> {
            Double winLoss = cellData.getValue().winLossProperty().get();
            return winLoss != null ?
                    new SimpleObjectProperty<>(winLoss) :
                    null;
        });
    }
}

// This class is used to create the data for the table
class LeaderboardData {
    private final SimpleObjectProperty<String> username;
    private final SimpleObjectProperty<Integer> wins;
    private final SimpleObjectProperty<Integer> losses;
    private final SimpleObjectProperty<Double> winLoss;

    public LeaderboardData(String[] data) {
        this.username = new SimpleObjectProperty<>(data[0]);
        this.wins = new SimpleObjectProperty<>(Integer.parseInt(data[1]));
        this.losses = new SimpleObjectProperty<>(Integer.parseInt(data[2]));
        this.winLoss = new SimpleObjectProperty<>(Double.parseDouble(data[3]));
        }

    public SimpleObjectProperty<String> usernameProperty() {
        return username;
    }

    public SimpleObjectProperty<Integer> winsProperty() {
        return wins;
    }

    public SimpleObjectProperty<Integer> lossesProperty() {
        return losses;
    }

    public SimpleObjectProperty<Double> winLossProperty() {
        return winLoss;
    }
}