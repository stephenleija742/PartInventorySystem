package sample.Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Stephen on 12/13/2017.
 */
public class IdentifierConcrete implements IdentfierInterface{

    private SimpleIntegerProperty id;
    private SimpleStringProperty partNum;
    private SimpleStringProperty prodDescription;

    public IdentifierConcrete() {
        this.id = new SimpleIntegerProperty();
        this.partNum = new SimpleStringProperty();
    }
    @Override
    public SimpleIntegerProperty idProperty(){return id;}
    @Override
    public SimpleStringProperty partNumProperty(){return partNum;}

    @Override
    final public void setID(int idValue) throws IllegalArgumentException{
        this.id.set(idValue);
    }
    @Override
    public final void setPartNum(String partNum) throws IllegalArgumentException{
        if(partNum.length() <= 20 && partNum.length() > 0) {
            this.partNum.set(partNum);
        } else {
            throw new IllegalArgumentException
                    ("Part Number must not be empty and must not be greater than 20 characters");
        }
    }

    @Override
    public int getID(){return this.id.get();}

    @Override
    public final String getPartNum(){return this.partNum.get();}
}
