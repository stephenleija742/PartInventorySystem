package sample.Controllers;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.Contract;
import sample.Controllers.ItemDialogInterface;
import sample.Models.ListModels.TableListModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Stephen on 12/29/2017.
 */
public class AddProductTemplateDialog implements Initializable, ItemDialogInterface{

    @FXML private TextField prodNumberField;
    @FXML private TextField descriptionField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Label headerLabel;
    private int id;

    private TableListModel prodTemplateList;

    public AddProductTemplateDialog(){}

    @Contract (pure = true)
    private StringProperty prodNumberProperty(){ return prodNumberField.textProperty(); }

    void setHeaderLabel(String headerLabelReference){
        headerLabel.setText(headerLabelReference);
    }

    void setIDParam(int idRef){id = idRef;}

    void setProdNumberField(String prodNumberField) { this.prodNumberField.setText(prodNumberField); }

    void setDescriptionField (String descriptionField) { this.descriptionField.setText(descriptionField);}

    @Contract (pure = true)
    private StringProperty descriptionProperty(){ return descriptionField.textProperty(); }

    private String getProdNumber(){ return prodNumberProperty().get(); }

    private String getDescription(){ return descriptionProperty().get(); }

    private String getId(){ return Integer.toString(id); }

    @Override
    public void initialize(URL location, ResourceBundle resources) {  }

    @Override
    public void initDataAndListeners(TableListModel listModelReference) {
        prodTemplateList = listModelReference;
        saveButton.setOnAction(event ->{
            try {
                if (headerLabel.getText().equals("Add Product")) {
                    String[] prodDetails = {getProdNumber(), getDescription()};
                    prodTemplateList.addItemModelToList(prodDetails);
                } else {
                    String[] prodDetails = {getId(), getProdNumber(), getDescription()};
                    prodTemplateList.editItemInList(prodDetails);
                }
                saveButton.getScene().getWindow().hide();
            } catch(IllegalArgumentException | NullPointerException | SQLException e){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Invalid Product");
                    alert.setHeaderText("Product Constraint");
                    e.printStackTrace();
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            });
        cancelButton.setOnAction(event -> cancelButton.getScene().getWindow().hide());
    }
}
