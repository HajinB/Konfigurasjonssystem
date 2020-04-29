package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentTypes;
import org.programutvikling.gui.customTextField.PriceField;
import org.programutvikling.gui.utility.TemporaryComponent;

import java.net.URL;
import java.util.List;
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

    void initData(Component c, Stage stage, int columnIndex) {
        //tar inn stage for å kunne lukke når brukeren trykker endre
        RegistryComponentLogic registryComponentLogic = new RegistryComponentLogic(componentEditNode);
        registryComponentLogic.setTextAreaListener(componentEditNode);
        this.stage = stage;
        System.out.println(componentEditNode);
        cbType.setValue(c.getProductType());
        txtPopupProductName.setText(c.getProductName());
        txtPopupProductDescription.setText(c.getProductDescription());
        txtPopupProductPrice.setText(Double.toString(c.getProductPrice()));
        setFocusOnField(columnIndex, c);
    }

    private void setFocusOnField(int columnIndex, Component c) {
        System.out.println("Kolonne index er: " +columnIndex);

        if(columnIndex ==0){
            cbType.requestFocus();
            ComponentTypes componentTypes = new ComponentTypes();
            List<String> s = componentTypes.getObservableTypeListName();
        }
        if(columnIndex ==1){
            txtPopupProductName.requestFocus();
            txtPopupProductName.selectAll();
        }
        if(columnIndex==2){
            txtPopupProductDescription.requestFocus();
            txtPopupProductDescription.selectAll();
        }
        if(columnIndex==3){
            txtPopupProductPrice.requestFocus();
            txtPopupProductPrice.selectAll();
        }
    }
}


