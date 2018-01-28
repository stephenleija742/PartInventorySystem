package sample.Controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import sample.Models.DetailModels.ItemModel;
import sample.Models.ListModels.TableListModel;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Stephen on 1/13/2018.
 */
 abstract class TableController {

    private TableView<ItemModel> table;
    private TableListModel listModel;

    void setTable(TableView table){
        this.table = table;
    }

    @SuppressWarnings("unchecked")
    void populateTable(TableListModel listModel){
        this.listModel = listModel;
        try{
            table.setItems(listModel.getModelList());
            table.getColumns().addAll();
        } catch (SQLException | IllegalArgumentException e){
            Logger logger = Logger.getLogger(ItemModel.class.getName());
            logger.log(Level.WARNING, e.toString(), e.getMessage());
        }
    }


    void deletePartInMemory(String [] details){
        try{
            listModel.deleteItemFromList(details);
        } catch (SQLException | IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Deletion Error");
            alert.setHeaderText("Error deleting record");
            alert.setContentText(e.getMessage());
            alert.show();
            Logger logger = Logger.getLogger(ItemModel.class.getName());
            logger.log(Level.WARNING, e.toString(), e.getMessage());
            e.printStackTrace();
        }
    }
}
