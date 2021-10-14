package view;

import controller.DialogBoxes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddRegister extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/view/fxml/add-register.fxml"));
            Parent root = fxml.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(Main.getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Adicionar Registro");
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
