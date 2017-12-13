package sample.Parts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import sample.CabinetronGateway;
import javafx.collections.MapChangeListener.Change;
import sample.ItemModelPackage.ItemModel;
import sample.ItemModelPackage.PartModel;
import sample.TableListModel;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * Created by Stephen on 8/6/2017.
 */
class PartsList implements TableListModel{

    private ObservableList<ItemModel> partModelList;
    private ObservableMap<String, ItemModel> observableMap;
    private CabinetronGateway pdg;

    PartsList(){
        TreeMap<String, ItemModel> partModelMap = new TreeMap<>();
        observableMap = FXCollections.observableMap(partModelMap);
        partModelList = FXCollections.observableArrayList(observableMap.values());
        pdg = PartsTableGateway.getInstance();
        //change to partnum?
        observableMap.addListener((Change<? extends String, ? extends ItemModel> c) ->{
            if(c.wasAdded()){
                partModelList.add(c.getValueAdded());
            } else if (c.wasRemoved()){
                partModelList.remove(c.getValueRemoved());
            }
        });
    }

    // might be able to make this an anon inner class and put into partmodellist and construction
    @Override
    public ObservableList<ItemModel> getModelList() throws SQLException{
        CachedRowSet rowSet = pdg.findAllRecords();
        while(rowSet.next()){
            ItemModel partModel = new PartModel();
            partModel.setID(rowSet.getInt("Part_ID"));
            partModel.setPartNum(rowSet.getString("PartNum"));
            partModel.setPartName(rowSet.getString("PartName"));
            partModel.setVendor(rowSet.getString("Vendor"));
            partModel.setDropDownSelection(rowSet.getString("UnitOfQuantity"));
            partModel.setExPartNum(rowSet.getString("ExternalPartNum"));
            observableMap.put(partModel.getPartNum(), partModel);
        }
        rowSet.close();
        return partModelList;
    }

    @Override
    public void addItemModelToList(String[] partDetails) throws IllegalArgumentException, SQLException{
        if(observableMap.containsKey(partDetails[0])){
            throw new IllegalArgumentException("Part Number already exists");
        }
        int id;
        PartModel partModel = new PartModel();
        partModel.setPartNum(partDetails[0]); //partnum
        partModel.setPartName(partDetails[1]); //partname
        partModel.setVendor(partDetails[2]); //vendor
        partModel.setDropDownSelection(partDetails[3]); //unitofQuantity
        partModel.setExPartNum(partDetails[4]); //expartnum
        id = pdg.insertRecord(partDetails);
        partModel.setID(id);
        observableMap.put(partModel.getPartNum(), partModel);
    }

    @Override
    public void editItemInList(String[] selectedPart) throws IllegalArgumentException, SQLException{
        // remove either the get or contains key
        int checkID = observableMap.get(selectedPart[1]).getID();
        if(observableMap.containsKey(selectedPart[1]) && checkID != Integer.parseInt(selectedPart[0])){ // partnum
            throw new IllegalArgumentException("PartNumber already exists");
        }
        observableMap.get(selectedPart[1]).setPartNum(selectedPart[1]);
        observableMap.get(selectedPart[1]).setPartName(selectedPart[2]);
        observableMap.get(selectedPart[1]).setVendor(selectedPart[3]);
        observableMap.get(selectedPart[1]).setDropDownSelection(selectedPart[4]);
        observableMap.get(selectedPart[1]).setExPartNum(selectedPart[5]);
        pdg.updateRecord(selectedPart);
    }

    @Override
    public void deleteItemFromList(String[] itemDetails) throws NoSuchElementException, SQLException{
        int deletionIndex = Integer.parseInt(itemDetails[0]);
        String partNum = itemDetails[1];
        if(deletionIndex == -1){
            throw new NoSuchElementException("No element selected for deletion");
        }
        if(partModelList == null || partModelList.isEmpty()){
            throw new NoSuchElementException("List is empty");
        }
        int id;
        if(partModelList.get(deletionIndex) != null) {
            id = observableMap.get(partNum).getID();
            observableMap.remove(partNum);
            pdg.deleteRecord(id);
        } else{
            throw new NoSuchElementException("Element does not exist");
        }
    }

}
