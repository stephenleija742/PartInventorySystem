package sample.Inventory;


import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.jetbrains.annotations.Contract;
import sample.TableListModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Stephen on 10/10/2017.
 */
public class AddInventoryDialog implements Initializable, ItemDialogInterface{
    @FXML private TextField partNumberField;
    @FXML private TextField quantityField;
    @FXML private ComboBox<String> locationSelection;
    @FXML private Label headerLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    private TableListModel inventoryList;
    private String previousPartNumber;
    private String previousLocation;

    public int id;

    public AddInventoryDialog(){}

    @Override
    public void initialize(URL location, ResourceBundle resources) {    }

    @Contract(pure = true)
    private StringProperty partNumberProperty(){return partNumberField.textProperty();}

    @Contract(pure = true)
    private StringProperty quantityProperty(){return quantityField.textProperty();}

    private String getPartNumber(){return partNumberProperty().get();}

    private String getQuantity(){return quantityProperty().get();}

    private String getLocation(){return locationSelection.getSelectionModel().getSelectedItem();}

    private String getID(){return Integer.toString(id);}

    void setIDParam(int idRef){id = idRef;}

    void setHeaderLabel(String headerLabelReference){
        headerLabel.setText(headerLabelReference);
    }

    void setPartNumberField(String partNumberFieldRef){
        previousPartNumber = partNumberFieldRef;
        partNumberField.setText(partNumberFieldRef);
    }

    void setQuantityField(String quantityFieldRef){quantityField.setText(quantityFieldRef);}

    void setLocationSelection(String unitOfQuantitySelectionRef){
        previousLocation = unitOfQuantitySelectionRef;
        locationSelection.getSelectionModel().select(unitOfQuantitySelectionRef);
    }

    @Override
    public void initDataAndListeners(TableListModel inventoryListReference){
        inventoryList = inventoryListReference;
        saveButton.setOnAction(event -> {
            try {
                if (headerLabel.getText().equals("Add Inventory")) {
                    String[] inventoryDetails = {getPartNumber(), getLocation(),getQuantity()};
                    inventoryList.addItemModelToList(inventoryDetails);
                } else {
                    String[] inventoryDetails = {getID(), getPartNumber(), getLocation(),getQuantity(),
                                                previousPartNumber, previousLocation};
                    inventoryList.editItemInList(inventoryDetails);
                    //inventoryList
                    //partsList.editInventoryList(partDetails, 1);
                }
                saveButton.getScene().getWindow().hide();
            }  catch(IllegalArgumentException | SQLException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Item");
                alert.setHeaderText("Inventory Constraint");
                alert.setContentText(e.getMessage());
                alert.show();
            }
        });

        cancelButton.setOnAction(event -> cancelButton.getScene().getWindow().hide());
    }
}
