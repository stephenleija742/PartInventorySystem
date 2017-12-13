package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.TableListModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Stephen on 12/11/2017.
 */
public class ProductTemplateListController implements Initializable{
    @FXML private ProdTempListTableController prodTempListTableController;
    @FXML private ScrollPane partsTableCon;
    @FXML private GridPane partDetailFields;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button editButton;
    @FXML private GridPane grid;
    private Stage parentStage;
    TableListModel partsList;

    private String partNum, partName, vendor, dropDownSelection, externalPartNum;
    private int partID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
