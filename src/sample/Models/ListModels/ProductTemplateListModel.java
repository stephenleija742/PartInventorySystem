package sample.Models.ListModels;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import sample.Models.DetailModels.ProductTemplateModel;
import sample.SQLGateways.CabinetronGateway;
import sample.Models.DetailModels.ItemModel;
import sample.SQLGateways.ProductTemplateTableGateway;
import sample.SQLGateways.TemplatePartGateway;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * Created by Stephen on 1/21/2018.
 */
public class ProductTemplateListModel implements TableListModel {

    private ObservableList prodModelList;
    private ObservableMap<String, ProductTemplateModel> observableMap;
    private TemplatePartListSingleton templatePartListSingleton;
    private CabinetronGateway gtwy;

    public ProductTemplateListModel(){
        TreeMap<String, ProductTemplateModel> templateModelMap = new TreeMap<>();
        observableMap = FXCollections.observableMap(templateModelMap);
        prodModelList = FXCollections.observableArrayList(observableMap.values());
        gtwy = ProductTemplateTableGateway.getInstance();
        observableMap.addListener((MapChangeListener.Change<? extends String, ? extends ItemModel> c) ->{
            if(c.wasAdded()){
                prodModelList.add(c.getValueAdded());
            } else if (c.wasRemoved()){
                prodModelList.remove(c.getValueRemoved());
            }
        });
    }

    @Override
    public ObservableList<ItemModel> getModelList() throws SQLException {
        CachedRowSet rowSet = gtwy.findAllRecords();
        while(rowSet.next()){
            ProductTemplateModel templateModel = new ProductTemplateModel();
            templateModel.setID(rowSet.getInt("ProductTemplateID"));
            templateModel.setProdNum(rowSet.getString("ProductNum"));
            templateModel.setProdDescription(rowSet.getString("ProductDescription"));
            observableMap.put(templateModel.getProdNum(), templateModel);
        }
        rowSet.close();
        return prodModelList;
    }

    @Override
    public void addItemModelToList(String[] templateDetails) throws IllegalArgumentException, SQLException {
        if(observableMap.containsKey(templateDetails[0])){
            throw new IllegalArgumentException("Product Number already exists");
        }
        int id;
        ProductTemplateModel templateModel = new ProductTemplateModel();
        templateModel.setProdNum(templateDetails[0]); //partnum
        templateModel.setProdDescription(templateDetails[1]); //partname
        id = gtwy.insertRecord(templateDetails);
        templateModel.setID(id);
        observableMap.put(templateModel.getProdNum(), templateModel);
    }

    @Override
    public void editItemInList(String[] selectedProduct) throws IllegalArgumentException, SQLException {
        int checkID = observableMap.get(selectedProduct[1]).getID();
        if(observableMap.containsKey(selectedProduct[1]) && checkID != Integer.parseInt(selectedProduct[0])){ // partnum
            throw new IllegalArgumentException("Product Template already exists");
        }
        observableMap.get(selectedProduct[1]).setProdNum(selectedProduct[1]);
        observableMap.get(selectedProduct[1]).setProdDescription(selectedProduct[2]);
        gtwy.updateRecord(selectedProduct);
    }

    @Override
    public int editItemInList(String[] selectedItem, Timestamp lock) throws IllegalArgumentException, SQLException {
        return 1;
    }

    @Override
    public void reloadView() {

    }

    @Override
    public void deleteItemFromList(String[] itemDetails) throws NoSuchElementException, SQLException {
        int id;
        templatePartListSingleton = TemplatePartListSingleton.getInstance(null);
        TemplatePartGateway templatePartGateway = TemplatePartGateway.getInstance();
        int deletionIndex = Integer.parseInt(itemDetails[0]);
        Pair compositeKey;
        String prodNum = itemDetails[1];
        if(deletionIndex == -1){
            throw new NoSuchElementException("No element selected for deletion");
        }
        if(prodModelList == null || prodModelList.isEmpty()){
            throw new NoSuchElementException("List is empty");
        }

        //templatePart.next();
        if(templatePartListSingleton.getTemplatePartList() == null ||
                templatePartListSingleton.getTemplatePartList().isEmpty()){
            throw new NoSuchElementException("List is empty");
        }
        CachedRowSet templatePart = templatePartGateway.findRecordOnProductNum(itemDetails[1]);
        while (templatePart.next()) {
            compositeKey = new Pair(templatePart.getString("Part"), templatePart.getString("Template"));
            ItemModel itemToBeDeleted = templatePartListSingleton.getTemplatePartMap().get(compositeKey);
            if (itemToBeDeleted != null) {
                if (itemToBeDeleted.getQuantity() == 0) {
                    templatePartListSingleton.getTemplatePartMap().remove(compositeKey);
                    templatePartGateway.deleteRecord(itemToBeDeleted.getID());
                } else {
                    throw new IllegalArgumentException("Quantity must be greater than 0 to delete item");
                }
            } else {
                throw new IllegalArgumentException("Product Template does not exist");
            }
        }

        if(prodModelList.get(deletionIndex) != null) {
            id = observableMap.get(prodNum).getID();
            observableMap.remove(prodNum);
            gtwy.deleteRecord(id);
        } else{
            throw new NoSuchElementException("Element does not exist");
        }
    }

    @Override
    public Timestamp getLock(String id) throws SQLException {
        return null;
    }
}
