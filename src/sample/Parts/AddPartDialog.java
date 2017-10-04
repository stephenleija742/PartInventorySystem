package sample.Parts;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.jetbrains.annotations.Contract;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Stephen on 9/11/2017.
 */
public class AddPartDialog implements Initializable{
    @FXML private TextField partNumberField;
    @FXML private TextField partNameField;
    @FXML private TextField vendorField;
    @FXML private ComboBox<String> uoqSelection;
    @FXML private TextField exPartNumField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Label headerLabel;
    private PartsList partsList;

    public int rowIndex;
    public int id;


    public AddPartDialog(){   }

    @Override
    public void initialize(URL location, ResourceBundle resources) {    }

    @Contract(pure = true)
    private StringProperty partNumberProperty(){return partNumberField.textProperty();}

    @Contract(pure = true)
    private StringProperty partNameProperty(){return partNameField.textProperty();}

    @Contract(pure = true)
    private StringProperty vendorProperty(){return vendorField.textProperty();}

    @Contract(pure = true)
    private StringProperty exPartNumProperty(){return exPartNumField.textProperty();}

    private String getPartNumber(){return partNumberProperty().get();}

    private String getPartName(){return partNameProperty().get();}

    private String getVendor(){return vendorProperty().get();}

    private String getExPartNum(){return exPartNumProperty().get();}

    private String getUnitOfQuantity(){return uoqSelection.getSelectionModel().getSelectedItem();}

    private String getID(){return Integer.toString(id);}

    public void setHeaderLabel(String headerLabelReference){
        headerLabel.setText(headerLabelReference);
    }

    void setPartNumberField(String partNumberFieldRef){partNumberField.setText(partNumberFieldRef);}

    void setPartNameField(String partNameFieldRef){partNameField.setText(partNameFieldRef);}

    void setVendorField(String vendorFieldRef){vendorField.setText(vendorFieldRef);}

    void setUnitOfQuantitySelection(String unitOfQuantitySelectionRef){
        uoqSelection.getSelectionModel().select(unitOfQuantitySelectionRef);
    }

    void setIDParam(int idRef){id = idRef;}

    void setExPartNumField(String exPartNumFieldRef){exPartNumField.setText(exPartNumFieldRef);}

    void setRowIndex(int indexOfPart){rowIndex = indexOfPart;}

    void initDataAndListeners(PartsList partsListReference){
        partsList = partsListReference;
        saveButton.setOnAction(event -> {
            //String[] partDetails = {getPartNumber(), getPartName(),getVendor(), getUnitOfQuantity(), getExPartNum()};
            try {
                if (headerLabel.getText().equals("Add Part")) {
                    String[] partDetails = {getPartNumber(), getPartName(),getVendor(),
                            getUnitOfQuantity(), getExPartNum()};
                    partsList.addToPartList(partDetails);
                } else {
                    String[] partDetails = {getID(), getPartNumber(), getPartName(),getVendor(),
                            getUnitOfQuantity(), getExPartNum()};
                    //partsList.editPartList(partDetails);
                }
                saveButton.getScene().getWindow().hide();
            }  catch(IllegalArgumentException | NullPointerException | SQLException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Part");
                alert.setHeaderText("Part Constraint");
                alert.setContentText(e.getMessage());
                alert.show();
            }
        });
        cancelButton.setOnAction(event -> {
            cancelButton.getScene().getWindow().hide();
        });
    }
}
