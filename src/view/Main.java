package view;

import controller.DialogBoxes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage window;

    private static void setWindow(Stage window) {
        window = window;
    }

    public static Stage getWindow() {
        return window;
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/view/fxml/main-panel.fxml"));
            Parent root = fxml.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Painel Principal");
            stage.setResizable(false);
            setWindow(stage);
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
