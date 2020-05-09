package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.component.ComponentTypes;
import org.programutvikling.domain.utility.Clickable;
import org.programutvikling.gui.customTextField.PriceField;
import org.programutvikling.gui.utility.IController;
import org.programutvikling.model.TemporaryComponent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditPopupController extends TabComponentsController implements Initializable, IController {

    @FXML
    private GridPane componentEditNode;

    @FXML
    private Label lblEditPopupMessage;

    @FXML
    private Button btnEditComponent;

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

        editComponent();
    }

    private void editComponent() {
        if(areFieldsEmpty()){
            lblEditPopupMessage.setText("Fyll inn alle felt");
            return;
        }
        System.out.println(txtPopupProductPrice);
        double price = Double.parseDouble(txtPopupProductPrice.getText());
        Component component = new Component(cbType.getValue(), txtPopupProductName.getText(),
                txtPopupProductDescription.getText(),
                price);
        System.out.println(txtPopupProductPrice);

        //validering her ?, det propegerer ikke ned til konstruktøren (?) -altså det throwes ikke noe her.
        TemporaryComponent.INSTANCE.setEdited(true);
        TemporaryComponent.INSTANCE.storeTempComponent(component);
        stage.close();
    }

    private boolean areFieldsEmpty() {
        return(txtPopupProductPrice.getText().isEmpty() || txtPopupProductDescription.getText().isBlank() || txtPopupProductName.getText().isEmpty());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbType.setItems(componentTypes.getObservableTypeListName());
        btnEditComponent.setDefaultButton(true);
        setTextAreaListener(componentEditNode);
    }

    public void setTextAreaListener(GridPane gridPane) {
        TextArea textArea = ((TextArea) gridPane.lookup("#productDescription"));
        textArea.setWrapText(true);

        //overrider ENTER keyEvent for å unngå lineshift ( brukes for å lese fra txtfil)
        textArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                   editComponent();
                }
            }
        });
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

    @Override
    public void initData(Clickable c, Stage stage, int columnIndex) {

        Component component = (Component) c;
        RegistryComponentLogic registryComponentLogic = new RegistryComponentLogic(componentEditNode);
        registryComponentLogic.setTextAreaListener(componentEditNode);
        this.stage = stage;
        System.out.println(componentEditNode);
        cbType.setValue(component.getProductType());
        txtPopupProductName.setText(component.getProductName());
        txtPopupProductDescription.setText(component.getProductDescription());
        System.out.println(component.getProductPrice());
        txtPopupProductPrice.setText(Double.toString(component.getProductPrice()));
        setFocusOnField(columnIndex, component);

    }

}


