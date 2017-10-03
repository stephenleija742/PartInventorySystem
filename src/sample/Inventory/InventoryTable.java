package sample.Inventory;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    @FXML private TableView<InventoryModel> inventoryTable;
    @FXML private TableColumn<InventoryModel, Integer> idCol;
    @FXML private TableColumn<InventoryModel, String> partCol;
    @FXML private TableColumn<InventoryModel, String> locationCol;
    @FXML private TableColumn<InventoryModel, Integer> quantityCol;

    private InventoryList inventoryList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    void setColumnsFactories(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partCol.setCellValueFactory(new PropertyValueFactory<>("part"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    @SuppressWarnings("unchecked")
    void populateTable(InventoryList inventoryList){
        this.inventoryList = inventoryList;
        try {
            inventoryTable.setItems(inventoryList.getInventoryModelList());
            inventoryTable.getColumns().addAll();
        } catch (SQLException see){
            Logger logger = Logger.getLogger(InventoryModel.class.getName());
            logger.log(Level.WARNING, see.toString(), see.getMessage());
        }
    }

    void addToList(String[] inventoryDetails){
        try {
            // move the setting to partslist addToPartList method
            inventoryList.addToInventoryList(inventoryDetails);
            inventoryTable.getSelectionModel().selectLast();
        } catch (IllegalArgumentException iae){
            Logger logger = Logger.getLogger(InventoryModel.class.getName());
            logger.log(Level.WARNING, iae.toString(), iae.getMessage());
        } catch (SQLException see){
            Logger logger = Logger.getLogger(InventoryTableGateway.class.getName());
            logger.log(Level.WARNING, see.toString(), see.getMessage());
        }
    }

    void editList(String [] inventoryDetails){
        try{
            inventoryList.editInventoryList(inventoryDetails, inventoryTable.getSelectionModel().getSelectedIndex());
        } catch (IllegalArgumentException iae){
            Logger logger = Logger.getLogger(InventoryList.class.getName());
            logger.log(Level.WARNING, iae.toString(), iae.getMessage());
        } catch (SQLException see){
            Logger logger = Logger.getLogger(InventoryList.class.getName());
            logger.log(Level.WARNING, see.toString(), see.getMessage());
        }
    }

    void deleteFromList(String id){
        try{
            // error here. need to get the id. not the table position
            inventoryList.deleteFromInventoryList(inventoryTable.getSelectionModel().getSelectedIndex(),
                    Integer.parseInt(id));
        } catch (NoSuchElementException nee){
            Logger logger = Logger.getLogger(InventoryList.class.getName());
            logger.log(Level.WARNING, nee.toString(), nee.getMessage());
        } catch (SQLException see){
            Logger logger = Logger.getLogger(InventoryList.class.getName());
            logger.log(Level.WARNING, see.toString(), see.getMessage());
        }
    }

    ReadOnlyObjectProperty<InventoryModel> getInventoryProperty(){
        return inventoryTable.getSelectionModel().selectedItemProperty();
    }
}
