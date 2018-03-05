package sample.Controllers;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import sample.Models.ListModels.TableListModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Stephen on 2/15/2018.
 */
public class AddProductTemplatePartDialog implements Initializable, ItemDialogInterface {

    @FXML private TextField partField;
    @FXML private TextField prodField;
    @FXML private TextField quantityField;
    @FXML private Label headerLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    private String previousPartNumber;
    private String previousProdNumber;
    private TableListModel prodTemplatePartList;
    public int id;

    public AddProductTemplatePartDialog(){}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Contract(pure = true)
    private StringProperty partProperty(){return partField.textProperty();}

    @Contract(pure = true)
    private StringProperty prodProperty(){return prodField.textProperty();}

    @Contract(pure = true)
    private StringProperty quantityProperty(){return quantityField.textProperty();}

    private String getPartNumber(){return partProperty().get();}

    private String getprodNumber(){return prodProperty().get();}

    private String getQuantity(){return quantityProperty().get();}

    @NotNull
    @Contract(pure = true)
    private String getID(){return Integer.toString(id);}

    void setIDParam(int idRef){id = idRef;}

    void setHeaderLabel(String headerLabelReference){
        headerLabel.setText(headerLabelReference);
    }

    void setPartNumberField(String partNumberFieldRef){
        previousPartNumber = partNumberFieldRef;
        partField.setText(partNumberFieldRef);
    }

    void setProdField(String prodFieldRef){
        previousProdNumber = prodFieldRef;
        prodField.setText(prodFieldRef);
    }

    void setQuantityField(String quantityFieldRef){quantityField.setText(quantityFieldRef);}


    @Override
    public void initDataAndListeners(TableListModel listModelReference, Boolean isEditable) {
        //prodField.setEditable(isEditable);
        prodTemplatePartList = listModelReference;
        saveButton.setOnAction(event -> {
            try{
                if(headerLabel.getText().equals("Add Product Template Part")){
                    String[] templatePartDetails = {getPartNumber(), getprodNumber(),getQuantity()};
                    prodTemplatePartList.addItemModelToList(templatePartDetails);
                } else{
                    String[] templatePartDetails = {getID(), getPartNumber(), getprodNumber(), getQuantity(),
                            previousPartNumber, previousProdNumber};
                    prodTemplatePartList.editItemInList(templatePartDetails);
                }
                saveButton.getScene().getWindow().hide();
            } catch (IllegalArgumentException | SQLException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Template Part");
                alert.setHeaderText("Template Part Constraint");
                alert.setContentText(e.getMessage());
                alert.show();
            }
        });
        cancelButton.setOnAction(event -> cancelButton.getScene().getWindow().hide());
    }
}
