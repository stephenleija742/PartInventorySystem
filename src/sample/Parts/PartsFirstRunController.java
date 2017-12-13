package sample.Parts;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.ItemModelPackage.ItemModel;
import sample.ItemModelPackage.PartModel;
import sample.TableListModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Stephen on 7/3/2017.
 */
public class PartsFirstRunController implements Initializable{

    @FXML private PartsTableCon partsTableConController;
    @FXML private ScrollPane partsTableCon;
    @FXML private PartDetailFields partDetailFieldsController;
    @FXML private GridPane partDetailFields;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button editButton;
    @FXML private GridPane grid;
    private Stage parentStage;
    TableListModel partsList;

    private String partNum, partName, vendor, dropDownSelection, externalPartNum;
    private int partID;

    public PartsFirstRunController(){
    }

    public void initialize(URL location, ResourceBundle resources) {
        partsList = new PartsList();
        partsTableConController.setColumnsFactories();
        partsTableConController.populateTable(partsList);
        partDetailFieldsController.setIDFieldUneditable();
        partDetailFieldsController.setUnitOfQuantityBoxUneditable();
        ReadOnlyObjectProperty<ItemModel> rowSelectionProperty = partsTableConController.getPartProperty();

        addButton.setOnAction(event -> initDialogParameters("Add Inventory"));

        editButton.setOnAction(event -> initDialogParameters("Edit Inventory"));

        deleteButton.setOnAction(event -> partsTableConController.deletePartInMemory(partNum));

        rowSelectionProperty.addListener((observable, oldValue, newValue) -> {
            ItemModel selectedPart = observable.getValue();
            partID = selectedPart.getID();
            partNum = selectedPart.getPartNum();
            partName = selectedPart.getPartName();
            vendor = selectedPart.getVendor();
            dropDownSelection = selectedPart.getDropDownSelection();
            externalPartNum = selectedPart.getExPartNum();
        });
    }

    private void initDialogParameters(String dialogAction){
        BorderPane childRoot;
        Scene scene;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPartDialog.fxml"));
            childRoot = loader.load();
            scene = new Scene(childRoot);
            Stage newStage = new Stage();
            newStage.setTitle(dialogAction);
            newStage.setScene(scene);
            AddPartDialog addPartDialog = loader.getController();
            addPartDialog.setHeaderLabel(dialogAction);
            if(dialogAction.equalsIgnoreCase("Edit Part")){
                addPartDialog.setIDParam(partID);
                addPartDialog.setPartNumberField(partNum);
                addPartDialog.setPartNameField(partName);
                addPartDialog.setVendorField(vendor);
                addPartDialog.setUnitOfQuantitySelection(dropDownSelection);
                addPartDialog.setExPartNumField(externalPartNum);
            }
            addPartDialog.initDataAndListeners(partsList);
            newStage.initOwner(parentStage);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.showAndWait();
        } catch (IOException e){
            e.getMessage();
            e.getCause();
            e.printStackTrace();
        }
    }

    public void setStage(Stage parentStageReference){
        parentStage = parentStageReference;
    }
}
