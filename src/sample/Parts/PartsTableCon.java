package sample.Parts;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.ItemModelPackage.ItemModel;
import sample.ItemModelPackage.PartModel;
import sample.TableListModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Stephen on 6/5/2017.
 */
public class PartsTableCon implements Initializable{

    @FXML private TableView<ItemModel> partsTable;
    @FXML private TableColumn<ItemModel, String> idCol;
    @FXML private TableColumn<ItemModel, String> partNumCol;
    @FXML private TableColumn<ItemModel, String> partNameCol;
    @FXML private TableColumn<ItemModel, String> vendorCol;
    @FXML private TableColumn<ItemModel, String> exPartNumCol;
    @FXML private TableColumn<ItemModel, String> uoqCol;

    private TableListModel partsList;

    public int selectedIndex(){
        return partsTable.getSelectionModel().getSelectedIndex();
    }

    void setColumnsFactories(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNumCol.setCellValueFactory(new PropertyValueFactory<>("partNum"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        vendorCol.setCellValueFactory(new PropertyValueFactory<>("vendor"));
        exPartNumCol.setCellValueFactory(new PropertyValueFactory<>("exPartNum"));
        uoqCol.setCellValueFactory(new PropertyValueFactory<>("unitOfQuantity"));
    }

    @SuppressWarnings("unchecked")
    void populateTable(TableListModel partsList){
        this.partsList = partsList;
        try {
            partsTable.setItems(partsList.getModelList());
            partsTable.getColumns().addAll();
            //partsTable.getColumns().addAll();
        } catch (SQLException se){
            se.printStackTrace();
            se.getMessage();
        } catch (IllegalArgumentException iae){
            Logger logger = Logger.getLogger(PartModel.class.getName());
            logger.log(Level.WARNING, iae.toString(), iae.getMessage());
        }
    }

    void deletePartInMemory(String partNum){
        try {
            System.out.println("in deletepartinmemory: " + partNum);
            String index = Integer.toString(partsTable.getSelectionModel().getSelectedIndex());
            String[] details = {index, partNum};
            partsList.deleteItemFromList(details);
        } catch (SQLException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    ReadOnlyObjectProperty<ItemModel> getPartProperty(){
        return partsTable.getSelectionModel().selectedItemProperty();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
