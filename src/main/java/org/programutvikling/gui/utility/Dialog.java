package org.programutvikling.gui.utility;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionModel;

public class Dialog {

    public static void showErrorDialog(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Feil!");
        alert.setHeaderText("Ugyldig data!");
        alert.setContentText(msg);

        alert.showAndWait();
    }

    public static void showSuccessDialog(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("DatamaskincomponentRegister");
        alert.setHeaderText("Operasjon vellykket");
        alert.setContentText(msg);

        alert.showAndWait();
    }

    public  static Alert getConfirmationAlert(String title, String header, String alertText, String selection) {
        ButtonType btnJa = new ButtonType("Ja", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnNei = new ButtonType("Nei", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(AlertType.CONFIRMATION, alertText + selection  , btnJa, btnNei);
        alert.setTitle(title);
        alert.setHeaderText(header);
        return alert;
    }

    public  static Alert getConfirmationAlert(String title, String header) {
        ButtonType btnJa = new ButtonType("Ja", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnNei = new ButtonType("Nei", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(AlertType.CONFIRMATION, title, btnJa, btnNei);
        alert.setHeaderText(header);
        return alert;
    }
}
/*


*/

