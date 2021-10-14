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

public class AddRegisterController implements Initializable {

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

    private boolean validateForm(String name, String phone, String status, String description) {
        return !(name.trim().isEmpty() || phone.trim().isEmpty() || status.trim().isEmpty() || description.trim().isEmpty());
    }

    public void addCall() {
        String name = fieldName.getText();
        String phone = fieldPhone.getText();
        String status = fieldStatus.getValue();
        String description = fieldDescription.getText();

        if(validateForm(name, phone, status, description)) {
            if(Helper.validateInteger(phone)) {
                List<Call> calls = Connect.queryCallByPhone(phone);
                if (calls.size() > 0) {
                    DialogBoxes.registerAlreadyExists();
                }
                else {
                    Call call = new Call(name, phone, status, description);
                    if (Connect.addCall(call)) {
                        DialogBoxes.registrationCompleted();
                        close();
                    } else {
                        DialogBoxes.registrationError();
                    }
                }
            }
            else {
                DialogBoxes.onlyNumbers();
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
        fillValuesFieldStatus();

        Helper.addTextLimiter(fieldPhone, 20);
        Helper.addTextLimiter(fieldName, 80);
        Helper.addTextLimiter(fieldDescription, 255);

        fieldName.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER) {
                fieldPhone.requestFocus();
            }
        });

        fieldPhone.setOnKeyPressed(keyEvent -> {
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
                addCall();
            }
        });

        buttonRegister.setOnMouseClicked(click -> {
            addCall();
        });

        buttonClose.setOnMouseClicked(click -> {
            close();
        });
    }
}
