package sample.Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Stephen on 12/11/2017.
 */
public class ProductTemplateModel implements IdentfierInterface{
    private SimpleIntegerProperty id;
    private SimpleStringProperty partNum;
    private SimpleStringProperty prodDescription;

    public ProductTemplateModel(){
        this.id = new SimpleIntegerProperty();
        this.partNum = new SimpleStringProperty();
        this.prodDescription = new SimpleStringProperty();
    }

    @Override
    public SimpleIntegerProperty idProperty() {
        return null;
    }

    @Override
    public SimpleStringProperty partNumProperty() {
        return null;
    }

    @Override
    public void setID(int idValue) throws IllegalArgumentException {

    }

    @Override
    public void setPartNum(String partNum) throws IllegalArgumentException {

    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public String getPartNum() {
        return null;
    }
}
