package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    protected static String[] statusList = {"Já é paciente", "Atendeu, voltar a ligar", "Atendeu, mas não tem interesse", "Atendeu e agendou", "Chamou, mas desligou", "Chamou, mas não atendeu", "Não chamou / Não funciona", "Não chamou / Fora da área de cobertura"};

    protected static boolean validateInteger(String text) {
        try {
            Long.parseLong(text);
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected static String getTimestampString(Date timestamp) {
        SimpleDateFormat formatTimestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatTimestamp.format(timestamp);
    }

    protected static void addTextLimiter(final TextField textField, final int maxLength) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (textField.getText().length() > maxLength) {
                    String s = textField.getText().substring(0, maxLength);
                    textField.setText(s);
                }
            }
        });
    }
}
