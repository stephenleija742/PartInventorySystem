package sample.Controllers;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Models.DetailModels.ItemModel;
import sample.Models.DetailModels.TemplatePartModel;
import sample.Models.ListModels.ProductTemplateListModel;
import sample.Models.ListModels.TableListModel;
import sample.Models.ListModels.TemplatePartList;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Stephen on 2/19/2018.
 */
public class ProdTemplatePartController extends ItemDetail implements Initializable {

    @FXML private Button addProdTemplatePart;
    @FXML private Button editProdTemplatePart;
    @FXML private Button deleteProdTemplatePart;
    @FXML private ScrollPane prodTemplatePartTable;
    @FXML private TemplatePartTable templatePartTableController;
    private TableListModel prodTemplatePartList;
    private Stage parentStage;

    private String partNum, prodNum;
    private int quantity, templatePartID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prodTemplatePartList = new TemplatePartList();
        templatePartTableController.setColumnFactories();
        templatePartTableController.populateTable(prodTemplatePartList);
        ReadOnlyObjectProperty<ItemModel> rowSelectProperty = templatePartTableController.getItemProperty();

        addProdTemplatePart.setOnAction(event -> initDialogParameters("Add Product Template Part"));

        editProdTemplatePart.setOnAction(event -> initDialogParameters("Edit Product Template Part"));

        deleteProdTemplatePart.setOnAction(event -> {
            String itemDetails[] = {Integer.toString(templatePartID), partNum, prodNum};
            templatePartTableController.deletePartInMemory(itemDetails);
        });

        rowSelectProperty.addListener((observable, oldValue, newValue) -> {
            TemplatePartModel templatePartModel = (TemplatePartModel)observable.getValue();
            templatePartID = templatePartModel.getID();
            quantity = templatePartModel.getQuantity();
            prodNum = templatePartModel.getProdNum();
            partNum = templatePartModel.getPartNum();
        });
    }

    @Override
    protected void initDialogParameters(String dialogAction) {
        BorderPane childRoot;
        Scene scene;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/AddTemplatePartDialog.fxml"));
            childRoot = loader.load();
            scene = new Scene(childRoot);
            Stage newStage = new Stage();
            newStage.setTitle(dialogAction);
            newStage.setScene(scene);
            AddProductTemplatePartDialog addProductTemplatePartDialog = loader.getController();
            addProductTemplatePartDialog.setHeaderLabel(dialogAction);
            if (dialogAction.equalsIgnoreCase("Edit Product Template Part")){
                addProductTemplatePartDialog.setIDParam(templatePartID);
                addProductTemplatePartDialog.setProdField(prodNum);
                addProductTemplatePartDialog.setPartNumberField(partNum);
                addProductTemplatePartDialog.setQuantityField(Integer.toString(quantity));
                addProductTemplatePartDialog.initDataAndListeners(prodTemplatePartList, false);
            } else{
                addProductTemplatePartDialog.initDataAndListeners(prodTemplatePartList, true);
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
