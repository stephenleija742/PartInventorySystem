package sample.Parts;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Stephen on 6/5/2017.
 */
public class PartsTableCon implements Initializable{

    @FXML private TableView<PartModel> partsTable;
    @FXML private TableColumn<PartModel, String> idCol;
    @FXML private TableColumn<PartModel, String> partNumCol;
    @FXML private TableColumn<PartModel, String> partNameCol;
    @FXML private TableColumn<PartModel, String> vendorCol;
    @FXML private TableColumn<PartModel, String> exPartNumCol;
    @FXML private TableColumn<PartModel, String> uoqCol;

    private PartsList partsList;

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
    void populateTable(PartsList partsList){
        this.partsList = partsList;
        try {
            partsTable.setItems(partsList.getPartModelList());
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
/*
    //handle add buttons request
    void addToList(String[] partDetails){
        try {
            partsList.addToPartList(partDetails);
            partsTable.getSelectionModel().selectLast();
        } catch (IllegalArgumentException iae){
            Logger logger = Logger.getLogger(PartModel.class.getName());
            logger.log(Level.WARNING, iae.toString(), iae.getMessage());
        } catch (SQLException see){
            Logger logger = Logger.getLogger(PartsList.class.getName());
            logger.log(Level.WARNING, see.toString(), see.getMessage());
        }
    }

    void editList(String[] partDetails){
        try {
            partsList.editPartList(partDetails, partsTable.getSelectionModel().getSelectedIndex());
        } catch (IllegalArgumentException iee) {
            Logger logger = Logger.getLogger(PartsList.class.getName());
            logger.log(Level.WARNING, iee.toString(), iee.getMessage());
        } catch (SQLException see){
            Logger logger = Logger.getLogger(PartsList.class.getName());
            logger.log(Level.WARNING, see.toString(), see.getMessage());
        }
    }

    void deleteFromList(String id){
        try{
            // error here. need to get the id. not the table position
            partsList.deletePartFromList(partsTable.getSelectionModel().getSelectedIndex(),
                    Integer.parseInt(id));
        } catch (NoSuchElementException nee){
            Logger logger = Logger.getLogger(PartsList.class.getName());
            logger.log(Level.WARNING, nee.toString(), nee.getMessage());
        } catch (SQLException see){
            Logger logger = Logger.getLogger(PartsList.class.getName());
            logger.log(Level.WARNING, see.toString(), see.getMessage());
        }
    }*/

    ReadOnlyObjectProperty<PartModel> getPartProperty(){
        return partsTable.getSelectionModel().selectedItemProperty();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
