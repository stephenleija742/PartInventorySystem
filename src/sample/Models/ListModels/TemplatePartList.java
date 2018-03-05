package sample.Models.ListModels;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import sample.Models.DetailModels.ItemModel;
import sample.Models.DetailModels.TemplatePartModel;
import sample.SQLGateways.CabinetronGateway;
import sample.SQLGateways.InventoryTableGateway;
import sample.SQLGateways.TemplatePartGateway;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

/**
 * Created by Stephen on 2/1/2018.
 */
public class TemplatePartList implements TableListModel {

    private ObservableList<ItemModel> templatePartModelList;
    private ObservableMap<Pair, TemplatePartModel> observableMap;
    private CabinetronGateway gtwy;

    public TemplatePartList(){
        LinkedHashMap<Pair, TemplatePartModel> itemModelMap = new LinkedHashMap<>();
        observableMap = FXCollections.observableMap(itemModelMap);
        this.templatePartModelList = FXCollections.observableArrayList(observableMap.values());
        gtwy = TemplatePartGateway.getInstance();
        observableMap.addListener((MapChangeListener.Change<? extends Pair, ? extends TemplatePartModel> c) ->{
            if(c.wasAdded()){
                templatePartModelList.add(c.getValueAdded());
            } else if (c.wasRemoved()){
                templatePartModelList.remove(c.getValueRemoved());
            }
        });
    }

    @Override
    public ObservableList<ItemModel> getModelList() throws SQLException {
        CachedRowSet rowSet = gtwy.findAllRecords();
        while (rowSet.next()){
            //System.out.println("Quantity: " + rowSet.getInt("Quantity"));
            int quantity = rowSet.getInt("Quantity");
            TemplatePartModel templatePartModel = new TemplatePartModel();
            templatePartModel.setID(rowSet.getInt("TemplatePartID"));
            templatePartModel.setPartNum(rowSet.getString("Part"));
            templatePartModel.setProdNum(rowSet.getString("Template"));
            templatePartModel.setQuantity(quantity);
            Pair<Integer, String, String> pairKey = new Pair<>(templatePartModel.getID(), templatePartModel.getPartNum(),
                    templatePartModel.getProdNum());
            observableMap.put(pairKey, templatePartModel);
        }
        rowSet.close();
        return templatePartModelList;
    }

    @Override
    public void addItemModelToList(String[] itemDetails) throws IllegalArgumentException, SQLException {
        String key = itemDetails[0];
        String value = itemDetails[1];
        Pair<Integer, String, String> pair = new Pair<>(key, value);
        if(observableMap.containsKey(pair)){
            throw new IllegalArgumentException("Product is already associated with this Part");
        }
        TemplatePartModel templatePartModel = new TemplatePartModel();
        int id = gtwy.insertRecord(itemDetails);
        if(id == 0){
            throw new IllegalArgumentException("Invalid Part and/or Product Number given");
        }
        templatePartModel.setPartNum(itemDetails[0]);
        templatePartModel.setProdNum(itemDetails[1]);
        templatePartModel.setQuantity(itemDetails[2]);
        templatePartModel.setID(id);
        pair.setId(id);
        observableMap.put(pair, templatePartModel);
    }

    @Override
    public void editItemInList(String[] itemDetails) throws IllegalArgumentException, SQLException {
        String key = itemDetails[1];
        String val = itemDetails[2];
        String currKey = itemDetails[4];
        String currVal = itemDetails[5];
        Integer quantity = Integer.parseInt(itemDetails[3]);
        Integer id = Integer.parseInt(itemDetails[0]);
        Pair<Integer,String, String> compositeKey = new Pair<>(id, key, val);
        TemplatePartModel currentModel;
        TemplatePartModel tempModel = observableMap.get(compositeKey);
        if(tempModel != null && tempModel.getID() != id){
            // inventory item exists and is not the currently selected item
            throw new IllegalArgumentException("Product Template Part already exists");
        } else{
            // get the current key mapped for this id mapped in list
            Pair<Integer, String, String> currCompositeKey = new Pair<>(id, currKey, currVal);
            currentModel = observableMap.get(currCompositeKey);
            System.out.println("error");
            if(currentModel != null){
                // update db and map with new values
                gtwy.updateRecord(itemDetails);
                currentModel.setPartNum(key);
                currentModel.setProdNum(val);
                currentModel.setQuantity(quantity);
                observableMap.remove(currCompositeKey);
                observableMap.put(compositeKey, currentModel);
                Comparator<ItemModel> comparator = Comparator.comparing(ItemModel::getPartNum);
                templatePartModelList.sort(comparator);
            } else {
                // item does not exist at all. this path should not occur
                throw new IllegalArgumentException("Product Template Part does not exist");
            }
        }
    }

    @Override
    public void deleteItemFromList(String[] itemDetails) throws NoSuchElementException, SQLException {
        int deletionIndex = Integer.parseInt(itemDetails[0]);
        Pair compositeKey = new Pair(itemDetails[1], itemDetails[2]);
        if(deletionIndex == -1){
            throw new NoSuchElementException("No element selected for deletion");
        }
        if(templatePartModelList == null || templatePartModelList.isEmpty()){
            throw new NoSuchElementException("List is empty");
        }
        ItemModel itemToBeDeleted = observableMap.get(compositeKey);
        if(itemToBeDeleted != null){
            if(itemToBeDeleted.getQuantity() == 0){
                observableMap.remove(compositeKey);
                gtwy.deleteRecord(itemToBeDeleted.getID());
            } else {
                throw new IllegalArgumentException("Quantity must be greater than 0 to delete item");
            }
        } else{
            throw new IllegalArgumentException("Product Template does not exist");
        }
    }
}
