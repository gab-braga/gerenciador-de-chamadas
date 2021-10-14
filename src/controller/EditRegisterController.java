package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Call;
import model.Connect;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class EditRegisterController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Button buttonClose;

    @FXML
    private TextField fieldName;

    @FXML
    private TextField fieldDescription;

    @FXML
    private Button buttonRegister;

    @FXML
    private TextField fieldPhone;

    @FXML
    private ComboBox<String> fieldStatus;

    private Call callEdit = null;

    public void fillDatas(Call call) {
        this.callEdit = call;
        fieldPhone.setText(call.getPhone());
        fieldName.setText(call.getName());
        fieldStatus.setValue(call.getStatus());
        fieldDescription.setText(call.getDescription());
    }

    private boolean validateForm(String name, String status, String description) {
        return !(name.trim().isEmpty() || status.trim().isEmpty() || description.trim().isEmpty());
    }

    public void editCall() {
        String name = fieldName.getText();
        String status = fieldStatus.getValue();
        String description = fieldDescription.getText();

        if(validateForm(name, status, description)) {
            Call call = new Call(name, callEdit.getPhone(), status, description);
            if(Connect.updateCall(call)) {
                DialogBoxes.editionCompleted();
                close();
            }
            else {
                DialogBoxes.editionError();
            }
        }
        else {
            DialogBoxes.fillAllFields();
        }
    }

    private void fillValuesFieldStatus() {
        List<String> status = Arrays.asList(Helper.statusList);
        ObservableList<String> items = FXCollections.observableArrayList(status);
        fieldStatus.setItems(items);
    }

    private void close() {
        ((Stage) root.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fieldPhone.setDisable(true);
        fillValuesFieldStatus();

        Helper.addTextLimiter(fieldPhone, 20);
        Helper.addTextLimiter(fieldName, 80);
        Helper.addTextLimiter(fieldDescription, 255);

        fieldName.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER) {
                fieldStatus.requestFocus();
            }
        });

        fieldStatus.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER) {
                fieldDescription.requestFocus();
            }
        });

        fieldDescription.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER) {
                editCall();
            }
        });

        buttonRegister.setOnMouseClicked(click -> {
            editCall();
        });

        buttonClose.setOnMouseClicked(click -> {
            close();
        });
    }
}
