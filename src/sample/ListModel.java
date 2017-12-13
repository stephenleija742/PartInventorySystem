package sample;

import javafx.collections.ObservableList;
import sample.ItemModelPackage.ItemModel;

import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * Created by Stephen on 10/8/2017.
 */
public interface ListModel {

    ObservableList<ItemModel> getItemModelFromList() throws SQLException;
    void addItemToList(String[] itemDetails) throws IllegalArgumentException, SQLException;
    void editListItem(String[] selectedItem) throws IllegalArgumentException, SQLException;
    void deleteItemFromList(int deletionIndex, String partNum) throws NoSuchElementException, SQLException;
}
