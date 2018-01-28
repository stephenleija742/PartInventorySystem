package sample.Models.ListModels;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import sample.SQLGateways.CabinetronGateway;
import sample.Models.DetailModels.InventoryModel;
import sample.Models.DetailModels.ItemModel;
import sample.SQLGateways.InventoryTableGateway;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Stephen on 8/30/2017.
 */
 public class InventoryList implements TableListModel{

    private ObservableList<ItemModel> inventoryModelList;
    private ObservableMap<Pair, InventoryModel> observableMap;
    private CabinetronGateway idg;

    public InventoryList(){
        LinkedHashMap<Pair, InventoryModel> itemModelMap = new LinkedHashMap<>();
        observableMap = FXCollections.observableMap(itemModelMap);
        this.inventoryModelList = FXCollections.observableArrayList(observableMap.values());
        idg = InventoryTableGateway.getInstance();
        observableMap.addListener((MapChangeListener.Change<? extends Pair, ? extends InventoryModel> c) ->{
            if(c.wasAdded()){
                inventoryModelList.add(c.getValueAdded());
            } else if (c.wasRemoved()){
                inventoryModelList.remove(c.getValueRemoved());
            }
        });
    }

    public ObservableList<ItemModel> getModelList() throws SQLException, IllegalArgumentException{
        CachedRowSet rowSet = idg.findAllRecords();
        while(rowSet.next()){
            InventoryModel inventoryModel = new InventoryModel();
            inventoryModel.setID(rowSet.getInt("Inventory_ID"));
            inventoryModel.setPartNum(rowSet.getString("Part"));
            inventoryModel.setDropDownSelection(rowSet.getString("Location"));
            inventoryModel.setQuantity(rowSet.getInt("Quantity"));
            // convert the key, pair to uppercase only for map
            //Integer key = inventoryModel.getID();
            Pair<Integer, String, String> pairKey = new Pair<>(inventoryModel.getID(), inventoryModel.getPartNum(),
                    inventoryModel.getDropDownSelection());
            observableMap.put(pairKey, inventoryModel);
        }
        rowSet.close();
        return inventoryModelList;
    }

    @Override
    public void addItemModelToList(String[] inventoryDetails) throws IllegalArgumentException, SQLException{
        //if there is a value with partnumber and location at same index throw exception
        String key = inventoryDetails[0];
        String value = inventoryDetails[1];
        Pair<Integer, String, String> pair = new Pair<>(key, value);
        if(observableMap.containsKey(pair)){
            throw new IllegalArgumentException("Part Number already exists at that Location");
        }
        InventoryModel inventoryModel = new InventoryModel();
        inventoryModel.setPartNum(inventoryDetails[0]);
        inventoryModel.setDropDownSelection(inventoryDetails[1]);
        inventoryModel.setQuantity(inventoryDetails[2]);
        int id = idg.insertRecord(inventoryDetails);
        inventoryModel.setID(id);
        pair.setId(id);
        observableMap.put(pair, inventoryModel);
    }

    @Override
    public void editItemInList(String[] selectedItem) throws IllegalArgumentException, SQLException{
        String key = selectedItem[1];
        String val = selectedItem[2];
        String currKey = selectedItem[4];
        String currVal = selectedItem[5];
        Integer quantity = Integer.parseInt(selectedItem[3]);
        Integer id = Integer.parseInt(selectedItem[0]);
        Pair<Integer,String, String> compositeKey = new Pair<>(id, key, val);
        InventoryModel currentModel;
        InventoryModel tempModel = observableMap.get(compositeKey);
        if(tempModel != null && tempModel.getID() != id){
            // inventory item exists and is not the currently selected item
            throw new IllegalArgumentException("Iventory Item already exists");
        } else{
            // get the current key mapped for this id mapped in list
            Pair<Integer, String, String> currCompositeKey = new Pair<>(id, currKey, currVal);
            currentModel = observableMap.get(currCompositeKey);
            if(currentModel != null){
                // update db and map with new values
                idg.updateRecord(selectedItem);
                currentModel.setPartNum(key);
                currentModel.setDropDownSelection(val);
                currentModel.setQuantity(quantity);
                observableMap.remove(currCompositeKey);
                observableMap.put(compositeKey, currentModel);
                Comparator<ItemModel> comparator = Comparator.comparing(ItemModel::getPartNum);
                inventoryModelList.sort(comparator);
            } else {
                // item does not exist at all. this path should not occur
                throw new IllegalArgumentException("Inventory Item does not exist");
            }
        }
    }

    @Override public void deleteItemFromList(String[] itemDetails) throws NoSuchElementException, SQLException{
        int deletionIndex = Integer.parseInt(itemDetails[0]);
        Pair compositeKey = new Pair(itemDetails[1], itemDetails[2]);
        if(deletionIndex == -1){
            throw new NoSuchElementException("No element selected for deletion");
        }
        if(inventoryModelList == null || inventoryModelList.isEmpty()){
            throw new NoSuchElementException("List is empty");
        }
        ItemModel invToBeDeleted = observableMap.get(compositeKey);
        if(invToBeDeleted != null){
            if(invToBeDeleted.getQuantity() == 0){
                observableMap.remove(compositeKey);
                idg.deleteRecord(invToBeDeleted.getID());
            } else{
                throw new IllegalArgumentException("Quantity must be greater than 0 to delete item");
            }
        } else{
            throw new IllegalArgumentException("Inventory Item does not exist");
        }

    }
}
