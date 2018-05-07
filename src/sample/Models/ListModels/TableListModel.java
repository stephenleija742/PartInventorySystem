package sample.Models.ListModels;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.NoSuchElementException;

/**
 * Created by Stephen on 10/10/2017.
 */
public interface TableListModel {
    ObservableList getModelList() throws SQLException;
    void addItemModelToList(String[] itemDetails) throws IllegalArgumentException, SQLException;
    void editItemInList(String[] itemDetails) throws IllegalArgumentException, SQLException;
    int editItemInList(String[] selectedItem, Timestamp lock) throws IllegalArgumentException, SQLException;
    void reloadView() throws SQLException;
    //change to enum
    void deleteItemFromList(String[] itemDetails) throws NoSuchElementException, SQLException;
    Timestamp getLock(String id) throws SQLException;
}
