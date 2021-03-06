package sample.Controllers;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Models.DetailModels.ItemModel;
import sample.Models.DetailModels.ProductTemplateModel;
import sample.Models.ListModels.ProductTemplateListModel;
import sample.Models.ListModels.TableListModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Stephen on 12/11/2017.
 */
public class ProductTemplateListController extends ItemDetail implements Initializable{

    @FXML private ScrollPane prodTableCon;
    @FXML private GridPane partDetailFields;
    @FXML private Button addTemplate;
    @FXML private Button deleteTemplate;
    @FXML private Button editTemplate;
    @FXML private GridPane grid;
    @FXML private ProdTemp prodTempController;
    @FXML ProdTemplatePartController prodTemplatePartController;
    private Stage parentStage;
    private TableListModel prodList;

    private String prodNum, prodDescription;
    private int prodID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prodList = new ProductTemplateListModel();
        prodTempController.setColumnsFactories();
        prodTempController.populateTable(prodList);
        ReadOnlyObjectProperty<ItemModel> rowSelectionProperty = prodTempController.getItemProperty();

        addTemplate.setOnAction(event -> initDialogParameters("Add Product Template"));

        editTemplate.setOnAction(event -> initDialogParameters("Edit Product Template"));

        deleteTemplate.setOnAction(event -> {
           String[] details = {Integer.toString(prodTempController.selectedIndex()), prodNum};
            prodTempController.deletePartInMemory(details);
        });

        rowSelectionProperty.addListener(((observable, oldValue, newValue) -> {
            ProductTemplateModel selectedItem = (ProductTemplateModel)observable.getValue();
            prodID = selectedItem.getID();
            prodNum = selectedItem.getProdNum();
            prodDescription = selectedItem.getProdDescription();
        }));
    }

    @Override
    protected void initDialogParameters(String dialogAction) {
        BorderPane childRoot;
        Scene scene;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/AddProductTemplateDialog.fxml"));
            childRoot = loader.load();
            scene = new Scene(childRoot);
            Stage newStage = new Stage();
            newStage.setTitle(dialogAction);
            newStage.setScene(scene);
            AddProductTemplateDialog addProductTemplateDialog = loader.getController();
            addProductTemplateDialog.setHeaderLabel(dialogAction);
            if (dialogAction.equalsIgnoreCase("Edit Product Template")){
                addProductTemplateDialog.setIDParam(prodID);
                addProductTemplateDialog.setProdNumberField(prodNum);
                addProductTemplateDialog.setDescriptionField(prodDescription);
                addProductTemplateDialog.initDataAndListeners(prodList, false);
            } else{
                addProductTemplateDialog.initDataAndListeners(prodList, true);
            }

            newStage.initOwner(parentStage);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.showAndWait();
        } catch (IOException e){
            e.getMessage();
            e.getCause();
            e.printStackTrace();
        }
    }
}
