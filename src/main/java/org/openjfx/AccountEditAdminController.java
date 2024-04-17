package org.openjfx;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AccountEditAdminController {

    @FXML
    TableColumn<String, String> username, email, password;

    @SuppressWarnings("rawtypes")
    @FXML
    TableView table;

    @FXML
    private void initialize() throws IOException
    {
        addDataToTable();
    }

    @FXML
    public void goToGame() throws IOException {
        App.setRoot("Game");
    }

    @SuppressWarnings("unchecked")
    @FXML
    private void addDataToTable() throws IOException {
        List<String[]> allAccounts = CsvManeger.getAllAccountList();
        for (int i = 0; i < allAccounts.size(); i++) {
            // username.setItem(allAccounts.get(i)[0]);
            // email.setItem(allAccounts.get(i)[1]);
            // password.setItem(allAccounts.get(i)[2]);
            // ObservableList<String> data = FXCollections.observableArrayList(
            //         "John", "Doe",
            //         "Jane", "Doe");
            username.setCellValueFactory(new PropertyValueFactory<>("username"));
            // table.getColumns().add(allAccounts.get(i)[0]);
            // table.setItems(data);
            
        }
    }
}
