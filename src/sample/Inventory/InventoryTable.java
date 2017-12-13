package sample.Inventory;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.ItemModelPackage.InventoryModel;
import sample.ItemModelPackage.ItemModel;
import sample.TableListModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Stephen on 8/29/2017.
 */
public class InventoryTable implements Initializable {

    @FXML private TableView<ItemModel> inventoryTable;
    @FXML private TableColumn<ItemModel, Integer> idCol;
    @FXML private TableColumn<ItemModel, String> partCol;
    @FXML private TableColumn<ItemModel, String> locationCol;
    @FXML private TableColumn<ItemModel, Integer> quantityCol;

    private TableListModel inventoryList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    void setColumnsFactories(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partCol.setCellValueFactory(new PropertyValueFactory<>("partNum"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("dropDownSelection"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    @SuppressWarnings("unchecked")
    void populateTable(TableListModel inventoryList){
        this.inventoryList = inventoryList;
        try {
            inventoryTable.setItems(inventoryList.getModelList());
            inventoryTable.getColumns().addAll();
        } catch (SQLException see){
            Logger logger = Logger.getLogger(InventoryModel.class.getName());
            logger.log(Level.WARNING, see.toString(), see.getMessage());
        }
    }

    void deletePartInMemory(String[] details){
        try{
            inventoryList.deleteItemFromList(details);
        } catch (SQLException | IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Item");
            alert.setHeaderText("Inventory Constraint");
            alert.setContentText(e.getMessage());
            alert.show();
            /*
            Logger logger = Logger.getLogger(InventoryModel.class.getName());
            logger.log(Level.WARNING, see.toString(), see.getMessage());*/
        }
    }

    ReadOnlyObjectProperty<ItemModel> getInventoryProperty(){
        return inventoryTable.getSelectionModel().selectedItemProperty();
    }
}
