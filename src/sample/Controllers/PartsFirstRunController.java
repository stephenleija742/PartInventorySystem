package sample.Controllers;

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
import sample.Models.DetailModels.ItemModel;
import sample.Models.ListModels.PartsList;
import sample.Models.ListModels.TableListModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Stephen on 7/3/2017.
 */
public class PartsFirstRunController extends ItemDetail implements Initializable{

    @FXML private PartsTableCon partsTableConController;
    @FXML private ScrollPane partsTableCon;
    @FXML private PartDetailFields partDetailFieldsController;
    @FXML private GridPane partDetailFields;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button editButton;
    @FXML private GridPane grid;
    private Stage parentStage;
    private TableListModel partsList;

    private String partNum, partName, vendor, dropDownSelection, externalPartNum;
    private int partID;

    public PartsFirstRunController(){
    }

    public void initialize(URL location, ResourceBundle resources) {
        partsList = new PartsList();
        //partsTableConController = new PartsTableCon();
        partsTableConController.setColumnsFactories();
        partsTableConController.populateTable(partsList);
        partDetailFieldsController.setIDFieldUneditable();
        partDetailFieldsController.setUnitOfQuantityBoxUneditable();
        ReadOnlyObjectProperty<ItemModel> rowSelectionProperty = partsTableConController.getItemProperty();

        addButton.setOnAction(event -> initDialogParameters("Add Part"));

        editButton.setOnAction(event -> initDialogParameters("Edit Part"));

        deleteButton.setOnAction(event -> {
            String[] details = {Integer.toString(partsTableConController.selectedIndex()), partNum};
            partsTableConController.deletePartInMemory(details);
        });

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

    protected void initDialogParameters(String dialogAction){
        BorderPane childRoot;
        Scene scene;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/AddPartDialog.fxml"));
            childRoot = loader.load();
            scene = new Scene(childRoot);
            Stage newStage = new Stage();
            newStage.setTitle(dialogAction);
            newStage.setScene(scene);
            AddPartDialog addPartDialog = loader.getController();
            addPartDialog.setHeaderLabel(dialogAction);
            if(dialogAction.equalsIgnoreCase("Edit Part")){
                addPartDialog.setIDParam(partID);
                addPartDialog.setInitialPartNumber(partNum);
                addPartDialog.setPartNumberField(partNum);
                addPartDialog.setPartNameField(partName);
                addPartDialog.setVendorField(vendor);
                addPartDialog.setUnitOfQuantitySelection(dropDownSelection);
                addPartDialog.setExPartNumField(externalPartNum);
                addPartDialog.initDataAndListeners(partsList, false);
            } else{
                addPartDialog.initDataAndListeners(partsList, true);
            }

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
