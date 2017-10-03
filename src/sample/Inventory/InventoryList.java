package sample.Inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.CabinetronGateway;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * Created by Stephen on 8/30/2017.
 */
 class InventoryList {

    private ObservableList<InventoryModel> inventoryModelList;
    private CabinetronGateway idg;

    InventoryList(){
        this.inventoryModelList = FXCollections.observableArrayList();
        idg = new InventoryTableGateway();
    }

    ObservableList<InventoryModel> getInventoryModelList() throws SQLException, IllegalArgumentException{
        CachedRowSet rowSet = idg.findAllRecords();
        while(rowSet.next()){
            InventoryModel inventoryModel = new InventoryModel();
            inventoryModel.setID(rowSet.getInt("Inventory_ID"));
            inventoryModel.setPart(rowSet.getString("Part"));
            inventoryModel.setLocation(rowSet.getString("Location"));
            inventoryModel.setQuantity(rowSet.getInt("Quantity"));
            inventoryModelList.add(inventoryModel);
        }
        rowSet.close();
        return inventoryModelList;
    }

    void addToInventoryList(String[] inventoryDetails) throws IllegalArgumentException, SQLException{
        for(InventoryModel i : inventoryModelList){
            if(i.getPart().equalsIgnoreCase(inventoryDetails[0])){
                if(i.getLocation().equalsIgnoreCase(inventoryDetails[1])) {
                    throw new IllegalArgumentException("Part already exists at that Location");
                }
            }
        }
        InventoryModel inventoryModel = new InventoryModel();
        inventoryModel.setPart(inventoryDetails[0]);
        inventoryModel.setLocation(inventoryDetails[1]);
        inventoryModel.setQuantity(inventoryDetails[2]);
        inventoryModel.setID(idg.insertRecord(inventoryDetails));
        inventoryModelList.add(inventoryModel);
    }

    void editInventoryList(String[] selectedInventory, int indexOfInventory) throws IllegalArgumentException,
            SQLException{
        for(int i = 0; i < inventoryModelList.size(); i++){
            if(inventoryModelList.get(i).getPart().equalsIgnoreCase(selectedInventory[0]) && i != indexOfInventory){
                if(inventoryModelList.get(i).getLocation().equalsIgnoreCase(selectedInventory[1])){
                    throw new IllegalArgumentException("Part already exists at that Location");
                }
            }
        }
        idg.updateRecord(selectedInventory, inventoryModelList.get(indexOfInventory).getID());
        inventoryModelList.get(indexOfInventory).setPart(selectedInventory[0]);
        inventoryModelList.get(indexOfInventory).setLocation(selectedInventory[1]);
        inventoryModelList.get(indexOfInventory).setQuantity(selectedInventory[2]);
    }

    void deleteFromInventoryList(int deletionIndex, int id) throws NoSuchElementException, SQLException{
        if(deletionIndex == -1){
            throw new NoSuchElementException("No element selected for deletion");
        }
        if(inventoryModelList == null || inventoryModelList.isEmpty()){
            throw new NoSuchElementException("List is empty");
        }
        if(inventoryModelList.get(deletionIndex) != null) {
            for(InventoryModel i : inventoryModelList){
                if(i.getID() == id){
                    if(i.getQuantity() == 0){
                        inventoryModelList.remove(i);
                        break;
                    } else{
                        throw new NoSuchElementException("Quantity must be 0 to delete inventory item");
                    }

                }
            }
            idg.deleteRecord(id);
            //inventoryModelList.remove(deletionIndex);
        } else {
            throw new NoSuchElementException("Element does not exist");
        }
    }
}
