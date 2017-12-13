package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.ItemModelPackage.ItemModel;

/**
 * Created by Stephen on 12/11/2017.
 */
public class ProdTempListTableController {
    @FXML
    private TableView<ItemModel> prodTable;
    @FXML private TableColumn<ItemModel, String> prodNumCol;
    @FXML private TableColumn<ItemModel, String> prodDescription;

}
