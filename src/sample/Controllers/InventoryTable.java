package sample.Controllers;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Models.DetailModels.ItemModel;
import sample.Models.ListModels.TableListModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Stephen on 8/29/2017.
 */
public class InventoryTable extends TableController implements Initializable {
    @FXML private TableView<ItemModel> table;
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
        setTable(table);
    }

    /*@SuppressWarnings("unchecked")
    void populateTable(TableListModel inventoryList){
        this.inventoryList = inventoryList;
        try {
            table.setItems(inventoryList.getModelList());
            table.getColumns().addAll();
        } catch (SQLException see){
            Logger logger = Logger.getLogger(InventoryModel.class.getName());
            logger.log(Level.WARNING, see.toString(), see.getMessage());
        }
    }*/

    /*void deletePartInMemory(String[] details){
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
            logger.log(Level.WARNING, see.toString(), see.getMessage());
        }
    }*/

}
