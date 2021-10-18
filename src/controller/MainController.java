package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Call;
import model.Connect;
import view.AddRegister;
import view.Details;
import view.EditRegister;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Button buttonAddRegister;

    @FXML
    private Button buttonSearch;

    @FXML
    private Button buttonLogout;

    @FXML
    private TableView<Call> tableCalls;

    @FXML
    private TableColumn<Call, String> columnName;

    @FXML
    private TableColumn<Call, String> columnPhone;

    @FXML
    private TableColumn<Call, String> columnStatus;

    @FXML
    private TableColumn<Call, String> columnDescription;

    @FXML
    private TableColumn<Call, String> columnLastUpdate;

    @FXML
    private MenuItem itemRefresh;

    @FXML
    private MenuItem itemDetails;

    @FXML
    private MenuItem itemEdit;

    @FXML
    private MenuItem itemDelete;

    @FXML
    private MenuItem itemHelp;

    private boolean isFilter = false;

    private String phoneFilter;

    private void fillTable() {
        List<Call> calls = Connect.queryAllCalls();
        if(isFilter) {
            calls = Connect.queryCallsFiltered(this.phoneFilter);
        }
        if(calls.size() > 0) {
            columnName.setCellValueFactory(new PropertyValueFactory<Call, String>("name"));
            columnPhone.setCellValueFactory(new PropertyValueFactory<Call, String>("phone"));
            columnStatus.setCellValueFactory(new PropertyValueFactory<Call, String>("status"));
            columnDescription.setCellValueFactory(new PropertyValueFactory<Call, String>("description"));
            columnLastUpdate.setCellValueFactory(data -> new SimpleStringProperty(Helper.getTimestampString(data.getValue().getLastUpdate())));
            ObservableList<Call> items = FXCollections.observableArrayList(calls);
            tableCalls.setItems(items);
            tableCalls.refresh();
            isFilter = false;
        }
        else{
            if(isFilter) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Esta chamada ainda não está registrada.");
                alert.setHeaderText(null);
                alert.setTitle("ATENÇÃO");
                alert.showAndWait();
                isFilter = false;
            }
        }
    }

    private void search(String phone) {
        if(phone == null || phone.isEmpty()) {
            isFilter = false;
            fillTable();
        }
        else {
            isFilter = true;
            phoneFilter = phone;
            fillTable();
        }
    }

    private void addRegister() {
        AddRegister addRegister = new AddRegister();
        addRegister.start(new Stage());
    }

    private void searchRegister() {
        TextInputDialog search = new TextInputDialog();
        search.setTitle("Pesquisar");
        search.setHeaderText(null);
        search.setContentText("Digite o telefone do contato:");
        search.showAndWait().ifPresent(this::search);
    }

    private void editRegister() {
        Call call = tableCalls.getSelectionModel().getSelectedItem();
        if(call == null) {
            DialogBoxes.selectARecord();
        }
        else {
            EditRegister editRegister = new EditRegister(call);
            editRegister.start(new Stage());
        }
    }

    private void detailsRegister() {
        Call call = tableCalls.getSelectionModel().getSelectedItem();
        if(call == null) {
            DialogBoxes.selectARecord();
        }
        else {
            Details details = new Details(call);
            details.start(new Stage());
        }
    }

    private void deleteRegister() {
        if (DialogBoxes.confirmationDelete()) {
            Call call = tableCalls.getSelectionModel().getSelectedItem();
            if(call == null) {
                DialogBoxes.selectARecord();
            } else {
                if (Connect.removeCall(call.getPhone())) {
                    DialogBoxes.deleteCompleted();
                    fillTable();
                } else {
                    DialogBoxes.deleteError();
                }
            }
        }
    }

    private void help() {
        String url = "https://github.com/F-Gabriel-Braga/gerenciador-de-chamadas/blob/master/README.md";
        try {
            URI link = new URI(url);
            Desktop.getDesktop().browse(link);
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            DialogBoxes.errorLink(url);
        }
    }

    private void close() {
        ((Stage) root.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillTable();

        buttonAddRegister.setOnMouseClicked(click -> {
            addRegister();
        });

        buttonSearch.setOnMouseClicked(click -> {
            searchRegister();
        });

        buttonLogout.setOnMouseClicked(click -> {
            close();
        });

        itemRefresh.setOnAction(action -> {
            fillTable();
        });

        itemEdit.setOnAction(action -> {
            editRegister();
        });

        itemDetails.setOnAction(action -> {
            detailsRegister();
        });

        itemDelete.setOnAction(action -> {
            deleteRegister();
        });

        itemHelp.setOnAction(action -> {
            help();
        });
    }
}
