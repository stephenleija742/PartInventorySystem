package sample.Controllers;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Models.DetailModels.ItemModel;
import sample.Models.ListModels.TableListModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Stephen on 6/5/2017.
 */
public class PartsTableCon extends TableController implements Initializable{

    @FXML private TableView<ItemModel> table;
    @FXML private TableColumn<ItemModel, String> idCol;
    @FXML private TableColumn<ItemModel, String> partNumCol;
    @FXML private TableColumn<ItemModel, String> partNameCol;
    @FXML private TableColumn<ItemModel, String> vendorCol;
    @FXML private TableColumn<ItemModel, String> exPartNumCol;
    @FXML private TableColumn<ItemModel, String> uoqCol;

    private TableListModel partsList;

    int selectedIndex(){
        return table.getSelectionModel().getSelectedIndex();
    }

    void setColumnsFactories(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNumCol.setCellValueFactory(new PropertyValueFactory<>("partNum"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        vendorCol.setCellValueFactory(new PropertyValueFactory<>("vendor"));
        exPartNumCol.setCellValueFactory(new PropertyValueFactory<>("exPartNum"));
        uoqCol.setCellValueFactory(new PropertyValueFactory<>("dropDownSelection"));
        setTable(table);
    }

   /* @SuppressWarnings("unchecked")
    void populateTable(TableListModel partsList){
        this.partsList = partsList;
        try {
           table.setItems(partsList.getModelList());
            table.getColumns().addAll();
            //partsTable.getColumns().addAll();
        } catch (SQLException se){
            se.printStackTrace();
            se.getMessage();
        } catch (IllegalArgumentException iae){
            Logger logger = Logger.getLogger(PartModel.class.getName());
            logger.log(Level.WARNING, iae.toString(), iae.getMessage());
        }
    }*/

    /*
    void deletePartInMemory(String partNum){
        try {
            //System.out.println("in deletepartinmemory: " + partNum);
            String index = Integer.toString(table.getSelectionModel().getSelectedIndex());
            String[] details = {index, partNum};
            partsList.deleteItemFromList(details);
        } catch (SQLException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
