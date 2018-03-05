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
 * Created by Stephen on 12/11/2017.
 */
public class ProdTemp extends TableController implements Initializable{
    @FXML private TableView<ItemModel> table;
    @FXML private TableColumn<ItemModel, String> prodNumCol;
    @FXML private TableColumn<ItemModel, String> prodDescriptionCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    int selectedIndex() {return table.getSelectionModel().getSelectedIndex();}

    void setColumnsFactories(){
        prodNumCol.setCellValueFactory(new PropertyValueFactory<>("prodNum"));
        prodDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("prodDescription"));
        setTable(table);
    }

}
