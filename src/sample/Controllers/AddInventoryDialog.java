package sample.Controllers;


import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.jetbrains.annotations.Contract;
import sample.Models.ListModels.InventoryList;
import sample.Models.ListModels.TableListModel;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ConcurrentModificationException;
import java.util.ResourceBundle;

/**
 * Created by Stephen on 10/10/2017.
 */
public class AddInventoryDialog implements Initializable, ItemDialogInterface {
    @FXML private TextField partNumberField;
    @FXML private TextField quantityField;
    @FXML private ComboBox<String> locationSelection;
    @FXML private Label headerLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    private TableListModel inventoryList;
    private String previousPartNumber;
    private String previousLocation;
    private Timestamp lock = null;
    private int successfulUpdate;

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
    public void initDataAndListeners(TableListModel inventoryListReference, Boolean isEditable){
        successfulUpdate = 0;
        partNumberField.setEditable(isEditable);
        inventoryList = inventoryListReference;
        try{
             lock = inventoryList.getLock(getID());
        } catch (SQLException sqlEx){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Item");
            alert.setHeaderText("Inventory Constraint");
            alert.setContentText(sqlEx.getMessage());
            alert.show();
        }
        saveButton.setOnAction(event -> {
            try {
                if (headerLabel.getText().equals("Add Inventory")) {
                    String[] inventoryDetails = {getPartNumber(), getLocation(),getQuantity()};
                    inventoryList.addItemModelToList(inventoryDetails);
                } else {
                    String[] inventoryDetails = {getID(), getPartNumber(), getLocation(),getQuantity(),
                                                previousPartNumber, previousLocation};
                    inventoryList.editItemInList(inventoryDetails, lock);
                    //inventoryList
                    //partsList.editInventoryList(partDetails, 1);
                }
                saveButton.getScene().getWindow().hide();
            }  catch(IllegalArgumentException | SQLException | ConcurrentModificationException e){
                //inventoryList = inventoryListReference;
                try {
                    inventoryList.reloadView();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
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
