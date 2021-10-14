package view;

import controller.DetailsController;
import controller.DialogBoxes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Call;

import java.io.IOException;

public class Details extends Application {

    private Call call;

    public Details(Call call) {
        this.call = call;
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/view/fxml/details.fxml"));
            Parent root = fxml.load();
            DetailsController controller = fxml.getController();
            controller.fillDatas(this.call);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(Main.getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Detalhes");
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
