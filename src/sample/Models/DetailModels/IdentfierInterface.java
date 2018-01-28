package sample.Models.DetailModels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Stephen on 12/13/2017.
 */
public abstract class IdentfierInterface {
    public SimpleIntegerProperty id;
    public SimpleStringProperty partNum;
    SimpleIntegerProperty idProperty(){
        return id;
    }
    SimpleStringProperty partNumProperty(){
        return partNum;
    }
    public void setID(int idValue) throws IllegalArgumentException{
        this.id.set(idValue);
    };
    public void setPartNum(String partNum) throws IllegalArgumentException{
        if(partNum.length() <= 20 && partNum.length() > 0) {
            this.partNum.set(partNum);
        } else {
            throw new IllegalArgumentException
                    ("Part Number must not be empty and must not be greater than 20 characters");
        }
    }
    public int getID(){
        return id.get();
    }
    public String getPartNum(){
        return partNum.get();
    }
}
