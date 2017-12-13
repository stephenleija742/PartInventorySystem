package sample.Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Stephen on 12/13/2017.
 */
public interface IdentfierInterface {
    SimpleIntegerProperty idProperty();
    SimpleStringProperty partNumProperty();
    void setID(int idValue) throws IllegalArgumentException;
    void setPartNum(String partNum) throws IllegalArgumentException;
    int getID();
    String getPartNum();
}
