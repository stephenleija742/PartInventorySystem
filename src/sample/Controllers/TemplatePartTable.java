package sample.Controllers;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Models.DetailModels.ItemModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Stephen on 2/13/2018.
 */
public class TemplatePartTable extends TableController implements Initializable {

    @FXML private TableView<ItemModel> table;
    @FXML private TableColumn<ItemModel, String> partCol;
    @FXML private TableColumn<ItemModel, String> prodCol;
    @FXML private TableColumn<ItemModel, Integer> quantityCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //int selectedIndex() {return table.getSelectionModel().getSelectedIndex();}

    void setColumnFactories(){
        partCol.setCellValueFactory(new PropertyValueFactory<>("partNum"));
        prodCol.setCellValueFactory(new PropertyValueFactory<>("prodNum"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        setTable(table);
    }

}

