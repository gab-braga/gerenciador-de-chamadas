package view;

import controller.DialogBoxes;
import controller.EditRegisterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Call;

import java.io.IOException;

public class EditRegister extends Application {

    private Call call;

    public EditRegister(Call call) {
        this.call = call;
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/view/fxml/edit-register.fxml"));
            Parent root = fxml.load();
            EditRegisterController controller = fxml.getController();
            controller.fillDatas(call);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(Main.getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editar Registro");
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException ex) {
            DialogBoxes.error404();
        }
    }
    public static void main(String[] args) {
        launch();
    }
}
