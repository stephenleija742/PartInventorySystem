package sample.Models.ListModels;

import javafx.collections.ObservableList;
import sample.Models.DetailModels.ItemModel;

import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * Created by Stephen on 10/10/2017.
 */
public interface TableListModel {
    ObservableList<ItemModel> getModelList() throws SQLException;
    void addItemModelToList(String[] itemDetails) throws IllegalArgumentException, SQLException;
    void editItemInList(String[] itemDetails) throws IllegalArgumentException, SQLException;

    //change to enum
    void deleteItemFromList(String[] itemDetails) throws NoSuchElementException, SQLException;
}
