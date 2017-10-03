package sample.Inventory;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by Stephen on 8/29/2017.
 */
public class InventoryController implements Initializable {

    @FXML private Button addInventory;
    @FXML private Button editInventory;
    @FXML private Button deleteInventory;
    @FXML private ScrollPane inventoryTable;
    @FXML private InventoryTable inventoryTableController;
    @FXML private GridPane inventoryDetail;
    @FXML private InventoryDetail inventoryDetailController;

    private InventoryList inventoryList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inventoryList = new InventoryList();
        inventoryTableController.setColumnsFactories();
        inventoryTableController.populateTable(inventoryList);
        inventoryDetailController.setIDFieldUneditable();
        inventoryDetailController.setLocationFieldUneditable();
        ReadOnlyObjectProperty<InventoryModel> rowSelectionProperty = inventoryTableController.getInventoryProperty();

        addInventory.setOnAction(event -> {
            String[] inventoryDetails = {inventoryDetailController.getPartField()
                    ,inventoryDetailController.getLocationField(), inventoryDetailController.getQuantityField()};
            inventoryTableController.addToList(inventoryDetails);
        });

        editInventory.setOnAction(event ->{
            String[] inventoryDetails = {inventoryDetailController.getPartField()
                    ,inventoryDetailController.getLocationField(), inventoryDetailController.getQuantityField()
                    ,inventoryDetailController.getIDField()};
            inventoryTableController.editList(inventoryDetails);
        });

        deleteInventory.setOnAction(event ->{
            inventoryTableController.deleteFromList(inventoryDetailController.getIDField());
        });

        rowSelectionProperty.addListener((observable, oldValue, newValue) -> {
            InventoryModel selectedPart = observable.getValue();
            inventoryDetailController.setIDField(Integer.toString(selectedPart.getID()));
            inventoryDetailController.setPartField(selectedPart.getPart());
            inventoryDetailController.setLocationField(selectedPart.getLocation());
            inventoryDetailController.setQuantityField(Integer.toString(selectedPart.getQuantity()));
        });
    }
}
