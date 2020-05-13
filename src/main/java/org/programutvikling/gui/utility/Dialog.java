package org.programutvikling.gui.utility;


import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class Dialog {

    public static void showErrorDialog(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Ugyldig operasjon");
        alert.setContentText(msg);
        alert.getDialogPane().setPrefHeight(200);
        alert.getDialogPane().setPrefWidth(400);
        alert.resizableProperty().setValue(true);
        alert.showAndWait();
    }

    public static void showInformationDialog(String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        TextArea area = new TextArea(msg);
        area.setEditable(false);
        area.setWrapText(true);
        alert.setTitle("Informasjon");
        alert.setHeaderText("");
        alert.getDialogPane().setContent(area);
        alert.resizableProperty().setValue(true);
        alert.showAndWait();
    }

    public static void showSuccessDialog(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        TextArea area = new TextArea(msg);
        area.setEditable(false);
        area.setWrapText(true);
        alert.setTitle("");
        alert.setHeaderText("Operasjon vellykket");
        alert.getDialogPane().setContent(area);
        alert.resizableProperty().setValue(true);
        alert.showAndWait();
    }

    public static Alert getConfirmationAlert(String title, String header, String alertText, String selection) {
        ButtonType btnJa = new ButtonType("Ja", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnNei = new ButtonType("Nei", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType btnAvbryt = new ButtonType("Avbryt", ButtonBar.ButtonData.BACK_PREVIOUS);
        Alert alert = new Alert(AlertType.CONFIRMATION, alertText + selection, btnJa, btnNei, btnAvbryt);
        alert.getDialogPane().setPrefHeight(200);
        alert.getDialogPane().setPrefWidth(400);
        alert.resizableProperty().setValue(true);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.resizableProperty().setValue(true);
        return alert;
    }

    public static Alert getOpenOption(String title, String header, String alertText, String selection) {
        ButtonType btnLeggTil = new ButtonType("Legg til", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnOverskriv = new ButtonType("Overskriv", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType btnAvbryt = new ButtonType("Avbryt", ButtonBar.ButtonData.BACK_PREVIOUS);
        Alert alert = new Alert(AlertType.CONFIRMATION, alertText + selection, btnLeggTil, btnOverskriv, btnAvbryt);
        alert.getDialogPane().setPrefHeight(200);
        alert.getDialogPane().setPrefWidth(400);
        alert.resizableProperty().setValue(true);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.resizableProperty().setValue(true);
        return alert;
    }

    public static Alert getConfirmationAlert(String title, String header) {
        ButtonType btnJa = new ButtonType("Ja", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnNei = new ButtonType("Nei", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(AlertType.CONFIRMATION, title, btnJa, btnNei);
        alert.getDialogPane().setPrefHeight(200);
        alert.getDialogPane().setPrefWidth(400);
        alert.resizableProperty().setValue(true);
        alert.setHeaderText(header);
        alert.resizableProperty().setValue(true);
        return alert;
    }

    public static Alert getLoadingDialog() {
        Alert alert = new Alert(AlertType.NONE);
        alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
        alert.setHeaderText("Laster inn..");
        ProgressBar pbs = new ProgressBar();
        pbs.setPrefWidth(100);
        pbs.setProgress(-1.0f);
        alert.getDialogPane().setPrefWidth(300);
        alert.getDialogPane().setPrefHeight(120);
        alert.getDialogPane().setContent(pbs);
        return alert;
    }
}
/*


 */


