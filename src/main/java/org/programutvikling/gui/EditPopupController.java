package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentTypes;
import org.programutvikling.gui.customTextField.PriceField;
import org.programutvikling.gui.utility.TemporaryComponent;

import java.net.URL;
import java.util.ResourceBundle;

public class EditPopupController extends TabComponentsController implements Initializable {

    @FXML
    private GridPane componentEditNode;

    @FXML
    private Label lblComponentMsg;

    @FXML
    private TextField txtPopupProductName;

    @FXML
    private TextArea txtPopupProductDescription;

    @FXML
    private PriceField txtPopupProductPrice;

    @FXML
    private ChoiceBox<String> cbType;


    Stage stage;


    ComponentTypes componentTypes = new ComponentTypes();

    @FXML
    void btnEditComponent(ActionEvent event) {
        //delete selected person from tableview in controller


        //legg til component i ContextModel
        double price = Double.parseDouble(txtPopupProductPrice.getText());

        Component component = new Component(cbType.getValue(), txtPopupProductName.getText(),
                txtPopupProductDescription.getText(),
                price
        );
        TemporaryComponent.INSTANCE.setEdited(true);
        TemporaryComponent.INSTANCE.storeTempComponent(component);
        stage.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbType.setItems(componentTypes.getObservableTypeListName());

    }

    void initData(Component c, Stage stage) {
        this.stage = stage;
        //.lookup().setText(customer.getName());
        System.out.println(componentEditNode);
        //System.out.println(componentEditNode.lookup("#popupProductName"));
        cbType.setValue(c.getProductType());
        txtPopupProductName.setText(c.getProductName());
        txtPopupProductDescription.setText(c.getProductDescription());
        txtPopupProductPrice.setText(Double.toString(c.getProductPrice()));
        //((TextField) componentEditNode.lookup("#popupProductName")).setText(c.getProductName());
    }
}


