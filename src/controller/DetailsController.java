package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Call;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Text textName;

    @FXML
    private Text textPhone;

    @FXML
    private Text textStatus;

    @FXML
    private Text textDescription;

    @FXML
    private Button buttonClose;

    public void fillDatas(Call call) {
        textPhone.setText(call.getPhone());
        textName.setText(call.getName());
        textStatus.setText(call.getStatus());
        textDescription.setText(call.getDescription());
    }

    private void close() {
        ((Stage) root.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonClose.setOnMouseClicked(click -> {
            close();
        });
    }
}
