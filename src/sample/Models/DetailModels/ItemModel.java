package sample.Models.DetailModels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Stephen on 10/8/2017.
 */
public interface ItemModel {


    /* maybe take out the fields that keep changing and put them in interface. make partmodel,
     inventory concrete classes for methods unique to them. Make this class abstract. concrete classes that extend
     this class will have setter methods that call the aformentioned interface - concrete classes
     */
    int getID();
    String getPartNum();
    SimpleStringProperty dropDownSelectionProperty();
    void setPartName(String partName) throws IllegalArgumentException;
    void setVendor(String vendor) throws IllegalArgumentException;
    void setExPartNum(String exPartNum) throws IllegalArgumentException;
    void setDropDownSelection(String selectionValue) throws IllegalArgumentException;
    void setQuantity(String quantityValue) throws IllegalArgumentException;
    void setQuantity(int quantityValue) throws IllegalArgumentException;
    String getDropDownSelection();
    String getPartName();
    String getVendor();
    String getExPartNum();
    int getQuantity();
}
