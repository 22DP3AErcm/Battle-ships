package org.openjfx;

import java.io.IOException;
import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class AccountEditAdminController {

    @FXML
    TableColumn<DataItem, String> username, email, password;
    @FXML
    TableColumn<DataItem, Button> remove;
    @FXML
    TableView<DataItem> table;
    @FXML
    Button refresh;
    @FXML
    VBox vbox;
    @FXML
    AnchorPane anchorpane;



    @FXML
    private void initialize() throws IOException
    {
        vbox.prefWidthProperty().bind(anchorpane.widthProperty());
        vbox.prefHeightProperty().bind(anchorpane.heightProperty());
        addDataToTable();
        username.setPrefWidth((SettingsController.currentResolution[0]-145)/3);
        email.setPrefWidth((SettingsController.currentResolution[0]-145)/3);
        password.setPrefWidth((SettingsController.currentResolution[0]-145)/3);
        remove.setPrefWidth(85);
    }

    GameController gameController = new GameController();

    @FXML
    public void goToGame() throws IOException {
        if (gameController.cameFrom.equals("MainMenu"))
            App.setRoot("MainMenu");
        else if (gameController.cameFrom.equals("Game")){
            App.setRoot("Game");
        }
    }

    @FXML
    private void addDataToTable() throws IOException {
        username.setPrefWidth((table.getWidth()-90)/3);
        email.setPrefWidth((table.getWidth()-90)/3);
        password.setPrefWidth((table.getWidth()-90)/3);
        remove.setPrefWidth(85);
        
        List<String> allAccounts = CsvManeger.getAllAccountList();
        ObservableList<DataItem> data = FXCollections.observableArrayList();
        for (int i = 0; i < allAccounts.size(); i++) {
            final int index = i;
            Button removeButton = new Button("Remove");
            removeButton.setId(""+index);
            removeButton.setOnMouseReleased(event -> {try {
                CsvManeger.RemoveAccount(allAccounts.get(index));
                addDataToTable();
            } catch (IOException e) {
                return;
            }});
            data.add(new DataItem(allAccounts.get(i).split(", "), removeButton));
        }
    
        table.setItems(data);
    
        username.setCellValueFactory(cellData -> {
            String name = cellData.getValue().nameProperty().get();
            return name != null ?
                    new SimpleObjectProperty<>(name) :
                    null;
        });
    
        email.setCellValueFactory(cellData -> {
            String email = cellData.getValue().emailProperty().get();
            return email != null ?
                    new SimpleObjectProperty<>(email) :
                    null;
        });
    
        password.setCellValueFactory(cellData -> {
            String password = cellData.getValue().passwordProperty().get();
            return password != null ?
                    new SimpleObjectProperty<>(password) :
                    null;
        });

        remove.setCellValueFactory(cellData -> {
            Button button = cellData.getValue().buttonProperty().get();
            button.getStyleClass().add("remove-btn");
            button.setCursor(Cursor.HAND);
            return button != null ?
                    new SimpleObjectProperty<>(button) :
                    null;
        });
    }
}


class DataItem {
    private final SimpleObjectProperty<String> name;
    private final SimpleObjectProperty<String> email;
    private final SimpleObjectProperty<String> password;
    private final SimpleObjectProperty<Button> button;

    public DataItem(String[] data, Button button) {
        this.name = new SimpleObjectProperty<>(data[0]);
        this.email = new SimpleObjectProperty<>(data[1]);
        this.password = new SimpleObjectProperty<>(data[2]);
        this.button = new SimpleObjectProperty<>(button);
        }

    public SimpleObjectProperty<String> nameProperty() {
        return name;
    }

    public SimpleObjectProperty<String> emailProperty() {
        return email;
    }

    public SimpleObjectProperty<String> passwordProperty() {
        return password;
    }

    public SimpleObjectProperty<Button> buttonProperty() {
        return button;
    }
}