package sample.Inventory;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.*;
import sample.ItemModelPackage.ItemModel;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * Created by Stephen on 8/29/2017.
 */
public class InventoryController extends ItemDetail implements Initializable {

    @FXML private Button addInventory;
    @FXML private Button editInventory;
    @FXML private Button deleteInventory;
    @FXML private ScrollPane inventoryTable;
    @FXML private InventoryTable inventoryTableController;
    @FXML private GridPane inventoryDetail;
    @FXML private InventoryDetail inventoryDetailController;

    private Stage parentStage;

    private TableListModel inventoryList;
    private int id, quantity;
    private String partNum, invLocation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inventoryList = new InventoryList();
        inventoryTableController.setColumnsFactories();
        inventoryTableController.populateTable(inventoryList);
        inventoryDetailController.setIDFieldUneditable();
        inventoryDetailController.setLocationFieldUneditable();
        ReadOnlyObjectProperty<ItemModel> rowSelectionProperty = inventoryTableController.getInventoryProperty();

        setStateAndActions(addInventory, editInventory, "Inventory");

        deleteInventory.setOnAction(event ->{
            String[] itemDetails = {Integer.toString(id), partNum, invLocation};
            inventoryTableController.deletePartInMemory(itemDetails);
        });

        rowSelectionProperty.addListener((observable, oldValue, newValue) -> {
            ItemModel selectedPart = observable.getValue();
            id = selectedPart.getID();
            partNum = selectedPart.getPartNum();
            invLocation = selectedPart.getDropDownSelection();
            quantity = selectedPart.getQuantity();
        });
    }

    protected void initDialogParameters(String dialogAction){
        BorderPane childRoot;
        Scene scene;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddInventoryDialog.fxml"));
            childRoot = loader.load();
            scene = new Scene(childRoot);
            Stage newStage = new Stage();
            newStage.setTitle(dialogAction);
            newStage.setScene(scene);
            AddInventoryDialog addInventoryDialog = loader.getController();
            addInventoryDialog.setHeaderLabel(dialogAction);
            if(dialogAction.equalsIgnoreCase("Edit Inventory")){
                addInventoryDialog.setIDParam(id);
                addInventoryDialog.setPartNumberField(partNum);
                addInventoryDialog.setLocationSelection(invLocation);
                addInventoryDialog.setQuantityField(Integer.toString(quantity));
            }
            addInventoryDialog.initDataAndListeners(inventoryList);
            newStage.initOwner(parentStage);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.showAndWait();
        } catch (IOException e){
            e.getMessage();
            e.getCause();
            e.printStackTrace();
        }
    }

}
