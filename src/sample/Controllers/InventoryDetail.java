package sample.Controllers;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Stephen on 8/29/2017.
 *
 * DELETE THIS CLASS ONCE RE-FACTORING IS COMPLETE!
 */
public class InventoryDetail implements Initializable {

    @FXML
    private TextField idField;

    @FXML
    private TextField partField;

    @FXML
    private TextField quantityField;

    @FXML
    private ComboBox<String> locationField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setIDFieldUneditable(){idField.setEditable(false);}

    public void setLocationFieldUneditable(){locationField.setEditable(false);}

    void setIDField(String idFieldValue){
        idField.setText(idFieldValue);
    }

    private StringProperty partProperty(){return partField.textProperty();}

    private StringProperty quantityProperty(){return quantityField.textProperty();}

    private StringProperty idProperty(){return idField.textProperty();}

    void setPartField(String partValue){partField.setText(partValue);}

    void setQuantityField(String quantityValue){quantityField.setText(quantityValue);}

    void setLocationField(String locationValue){locationField.getSelectionModel().select(locationValue);}

    String getPartField(){return partProperty().get();}

    String getQuantityField(){return quantityProperty().get();}

    String getLocationField(){return locationField.getSelectionModel().getSelectedItem();}

    String getIDField(){return idProperty().get();}
}
