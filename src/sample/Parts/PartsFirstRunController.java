package sample.Parts;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Stephen on 7/3/2017.
 */
public class PartsFirstRunController implements Initializable{

    //static int uuid = 0;

    @FXML private PartsTableCon partsTableConController;
    @FXML private ScrollPane partsTableCon;
    @FXML private PartDetailFields partDetailFieldsController;
    @FXML private GridPane partDetailFields;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button editButton;
    @FXML private GridPane grid;
    private Stage parentStage;

    private String partNum, partName, vendor, unitOfQuantity, externalPartNum;
    private int partID;

    public PartsFirstRunController(){
    }

    public void initialize(URL location, ResourceBundle resources) {
        //grid.prefWidthProperty().bind();
        PartsList partsList = new PartsList();
        partsTableConController.setColumnsFactories();
        partsTableConController.populateTable(partsList);
        partDetailFieldsController.setIDFieldUneditable();
        partDetailFieldsController.setUnitOfQuantityBoxUneditable();
        ReadOnlyObjectProperty<PartModel> rowSelectionProperty = partsTableConController.getPartProperty();

        addButton.setOnAction(event -> {
            BorderPane childRoot;
            Scene scene = null;
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPartDialog.fxml"));
                childRoot = loader.load();
                scene = new Scene(childRoot);

                Stage newStage = new Stage();
                newStage.setTitle("Add Part");
                newStage.setScene(scene);
                AddPartDialog addPartDialog = loader.getController();
                addPartDialog.initDataAndListeners(partsList);
                newStage.initOwner(parentStage);
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.showAndWait();
            } catch (IOException e) {
                e.getMessage();
                e.getCause();
                e.printStackTrace();
            }

        });

        editButton.setOnAction(event ->{
            /*String[] partdetails = {partDetailFieldsController.getPartNumField()
                    ,partDetailFieldsController.getPartNameField(), partDetailFieldsController.getVendorField()
                    ,partDetailFieldsController.getComboBoxValue()
                    ,partDetailFieldsController.getExternalPartNumField()};
            partsTableConController.editList(partdetails);*/

            BorderPane childRoot;
            Scene scene = null;
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPartDialog.fxml"));
                childRoot = loader.load();
                scene = new Scene(childRoot);

                Stage newStage = new Stage();
                newStage.setTitle("Edit Part");
                newStage.setScene(scene);
                AddPartDialog addPartDialog = loader.getController();
                addPartDialog.setHeaderLabel("Edit Part");
                //addPartDialog.setRowIndex(partsTableConController.selectedIndex());
                addPartDialog.setIDParam(partID);
                addPartDialog.setPartNumberField(partNum);
                addPartDialog.setPartNameField(partName);
                addPartDialog.setVendorField(vendor);
                addPartDialog.setUnitOfQuantitySelection(unitOfQuantity);
                addPartDialog.setExPartNumField(externalPartNum);
                addPartDialog.initDataAndListeners(partsList);
                newStage.initOwner(parentStage);
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.showAndWait();
            } catch (IOException e) {
                e.getMessage();
                e.getCause();
                e.printStackTrace();
            }

        });
        /*
        deleteButton.setOnAction(event ->{
            partsTableConController.deleteFromList(partDetailFields.getId());
        });*/

        rowSelectionProperty.addListener((observable, oldValue, newValue) -> {
            PartModel selectedPart = observable.getValue();
            partID = selectedPart.getID();
            partNum = selectedPart.getPartNum();
            partName = selectedPart.getPartName();
            vendor = selectedPart.getVendor();
            unitOfQuantity = selectedPart.getUnitOfQuantity();
            externalPartNum = selectedPart.getExPartNum();
            /*partDetailFieldsController.setIDField(Integer.toString(selectedPart.getID()));
            partDetailFieldsController.setPartNumField(selectedPart.getPartNum());
            partDetailFieldsController.setPartNameField(selectedPart.getPartName());
            partDetailFieldsController.setVendorFie
            ld(selectedPart.getVendor());
            partDetailFieldsController.setExternalPartNumField(selectedPart.getExPartNum());
            partDetailFieldsController.setUnitOfQuantityField(selectedPart.getUnitOfQuantity());*/
        });
    }

    public void setStage(Stage parentStageReference){
        parentStage = parentStageReference;
    }
}
