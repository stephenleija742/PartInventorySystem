package sample.Parts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import sample.CabinetronGateway;
import javafx.collections.MapChangeListener.Change;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * Created by Stephen on 8/6/2017.
 */
class PartsList {

    private ObservableList<PartModel> partModelList;
    private TreeMap<String, PartModel> partModelMap;
    private ObservableMap<String, PartModel> observableMap;
    private CabinetronGateway pdg;

    PartsList(){
        partModelMap = new TreeMap<>();
        observableMap = FXCollections.observableMap(partModelMap);
        partModelList = FXCollections.observableArrayList(observableMap.values());
        pdg = new PartsTableGateway();
        //change to partnum?
        observableMap.addListener((Change<? extends String, ? extends PartModel> c) ->{
            if(c.wasAdded()){
                partModelList.add(c.getValueAdded());
            } else if (c.wasRemoved()){
                partModelList.remove(c.getValueRemoved());
            }
        });
    }

    // might be able to make this an anon inner class and put into partmodellist and construction
    ObservableList<PartModel> getPartModelList() throws SQLException{
        CachedRowSet rowSet = pdg.findAllRecords();
        while(rowSet.next()){
            PartModel partModel = new PartModel();
            partModel.setId(rowSet.getInt("Part_ID"));
            partModel.setPartNum(rowSet.getString("PartNum"));
            partModel.setPartName(rowSet.getString("PartName"));
            partModel.setVendor(rowSet.getString("Vendor"));
            partModel.setUnitOfQuantity(rowSet.getString("UnitOfQuantity"));
            partModel.setExPartNum(rowSet.getString("ExternalPartNum"));
            observableMap.put(partModel.getPartNum(), partModel);
        }
        rowSet.close();
        return partModelList;
    }


    void addToPartList(String[] partDetails) throws IllegalArgumentException, SQLException{
        if(observableMap.containsKey(partDetails[0])){
            throw new IllegalArgumentException("Part Number already exists");
        }
        int id;
        PartModel partModel = new PartModel();
        partModel.setPartNum(partDetails[0]); //partnum
        partModel.setPartName(partDetails[1]); //partname
        partModel.setVendor(partDetails[2]); //vendor
        partModel.setUnitOfQuantity(partDetails[3]); //unitofQuantity
        partModel.setExPartNum(partDetails[4]); //expartnum
        id = pdg.insertRecord(partDetails);
        partModel.setId(id);
        observableMap.put(partModel.getPartNum(), partModel);
    }

    /*void editPartList(String[] selectedPart) throws IllegalArgumentException, SQLException{
        for(int i = 0; i < partModelList.size(); i++){
            if(partModelList.get(i).getPartNum().equals(selectedPart[0]) && i != indexOfPart){
                throw new IllegalArgumentException("Part Number already exists");
            }
        }
        partModelList.get(indexOfPart).setPartNum(selectedPart[0]);
        partModelList.get(indexOfPart).setPartName(selectedPart[1]);
        partModelList.get(indexOfPart).setVendor(selectedPart[2]);
        partModelList.get(indexOfPart).setUnitOfQuantity(selectedPart[3]);
        partModelList.get(indexOfPart).setExPartNum(selectedPart[4]);
        pdg.updateRecord(selectedPart, partModelList.get(indexOfPart).getID());
    }*/

    void deleteFromList(int deletionIndex, int id) throws NoSuchElementException, SQLException{
        if(deletionIndex == -1){
            throw new NoSuchElementException("No element selected for deletion");
        }
        if(partModelList == null || partModelList.isEmpty()){
            throw new NoSuchElementException("List is empty");
        }
        if(partModelList.get(deletionIndex) != null) {
            for(PartModel p : partModelList){
                if(p.getID() == id){
                    partModelList.remove(p);
                    break;
                }
            }
            pdg.deleteRecord(id);
        } else{
            throw new NoSuchElementException("Element does not exist");
        }
    }

}
