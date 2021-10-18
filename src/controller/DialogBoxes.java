package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public abstract class DialogBoxes {
    public static void registrationCompleted() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AVISO");
        alert.setHeaderText(null);
        alert.setContentText("Registro concluido.");
        alert.showAndWait();
    }

    public static void registrationError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRO");
        alert.setHeaderText(null);
        alert.setContentText("Erro ao efetuar registro.");
        alert.showAndWait();
    }

    public static void editionCompleted() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AVISO");
        alert.setHeaderText(null);
        alert.setContentText("Editado com sucesso.");
        alert.showAndWait();
    }

    public static void editionError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRO");
        alert.setHeaderText(null);
        alert.setContentText("Erro ao efetuar edição.");
        alert.showAndWait();
    }

    public static void deleteCompleted() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AVISO");
        alert.setHeaderText(null);
        alert.setContentText("Excluido com sucesso.");
        alert.showAndWait();
    }

    public static void deleteError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRO");
        alert.setHeaderText(null);
        alert.setContentText("Erro ao efetuar exclusão.");
        alert.showAndWait();
    }

    public static void error404() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRO");
        alert.setHeaderText(null);
        alert.setContentText("Erro no programa.");
        alert.showAndWait();
    }

    public static void fillAllFields() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ATENÇÃO");
        alert.setHeaderText(null);
        alert.setContentText("Preencha todos os campos obrigatórios!");
        alert.showAndWait();
    }

    public static void onlyNumbers() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ATENÇÃO");
        alert.setHeaderText(null);
        alert.setContentText("Digite apenas números nos campos numéricos!");
        alert.showAndWait();
    }

    public static void registerAlreadyExists() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ATENÇÃO");
        alert.setHeaderText(null);
        alert.setContentText("Este registro já existe!");
        alert.showAndWait();
    }

    public static void selectARecord() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ATENÇÃO");
        alert.setHeaderText(null);
        alert.setContentText("Selecione um registro!");
        alert.showAndWait();
    }

    public static void errorLink(String link) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRO");
        alert.setHeaderText(null);
        alert.setContentText(String.format("Não foi possível localizar o navegador!\nLink: %s", link));
        alert.showAndWait();
    }

    public static boolean confirmationDelete() {
        boolean flag = false;
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setHeaderText(null);
        confirmation.setContentText("Cofirma exclusão?");
        confirmation.setTitle("CONFIRMAÇÃO");
        Optional<ButtonType> response = confirmation.showAndWait();
        if (response.get() == ButtonType.OK) {
            flag = true;
        }
        return flag;
    }
}
