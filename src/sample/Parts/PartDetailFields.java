package sample.Parts;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Stephen on 6/28/2017.
 */
public class PartDetailFields implements Initializable{

    @FXML private ComboBox<String> unitOfQuantityBox;

    @FXML private TextField idField;
    @FXML private TextField partNumField;

    @FXML private TextField partNameField, vendorField, quantityField, externalPartNumField;

    void setIDFieldUneditable(){idField.setEditable(false);}

    void setUnitOfQuantityBoxUneditable(){unitOfQuantityBox.setEditable(false);}

    void setIDField(String idFieldValue){
        idField.setText(idFieldValue);
    }

    void setPartNumField(String partNumValue){
        partNumField.setText(partNumValue);
    }

    void setPartNameField(String partNameValue){
        partNameField.setText(partNameValue);
    }

    void setVendorField(String vendorValue){
        vendorField.setText(vendorValue);
    }

    void setExternalPartNumField(String externalPartNumValue){
        externalPartNumField.setText(externalPartNumValue);
    }

    void setUnitOfQuantityField(String unitOfQuantityValue){
        unitOfQuantityBox.getSelectionModel().select(unitOfQuantityValue);
    }

    private StringProperty numProperty(){
        return partNumField.textProperty();
    }

    String getPartNumField(){return numProperty().get();    }

    private StringProperty nameProperty(){return partNameField.textProperty();}

    String getPartNameField(){return nameProperty().get();}

    private StringProperty vendorProperty(){return vendorField.textProperty();}

    String getVendorField(){return vendorProperty().get();}

    private StringProperty exPartNumProperty(){return externalPartNumField.textProperty();}

    String getExternalPartNumField(){return exPartNumProperty().get();}

    String getComboBoxValue(){return unitOfQuantityBox.getSelectionModel().getSelectedItem();}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
