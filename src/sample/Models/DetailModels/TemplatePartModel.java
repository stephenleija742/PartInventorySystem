package sample.Models.DetailModels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Stephen on 2/1/2018.
 */
public class TemplatePartModel extends IdentfierInterface implements ItemModel {

    private SimpleStringProperty prodNum;
    private SimpleIntegerProperty quantity;

    public TemplatePartModel(){
        this.id = new SimpleIntegerProperty();
        this.partNum = new SimpleStringProperty();
        this.prodNum = new SimpleStringProperty();
        this.quantity = new SimpleIntegerProperty();
    }

    @Override
    public SimpleStringProperty dropDownSelectionProperty() {
        return null;
    }

    public SimpleIntegerProperty quantityProperty(){return this.quantity;}

    public SimpleStringProperty prodNumProperty(){return this.prodNum;}

    @Override
    public void setPartName(String partName) throws IllegalArgumentException {

    }

    @Override
    public void setVendor(String vendor) throws IllegalArgumentException {

    }

    @Override
    public void setExPartNum(String exPartNum) throws IllegalArgumentException {

    }

    @Override
    public void setDropDownSelection(String selectionValue) throws IllegalArgumentException {

    }

    @Override
    public void setQuantity(String quantityValue) throws IllegalArgumentException {
        int tempQuantity = Integer.parseInt(quantityValue);
        System.out.println(this.quantity.get());
        if(tempQuantity > 0 && this.quantity.get() <= 0) {
            this.quantity.set(tempQuantity);
            return;
        } else if(tempQuantity <= 0 && this.quantity.get() <=0){
            throw new IllegalArgumentException("Quantity must be greater than 0 when adding inventory");
        }
        if(tempQuantity >= 0){
            this.quantity.set(tempQuantity);
        } else {
            throw new IllegalArgumentException("Quantity must be greater than or equal to 0 when editing inventory");
        }
    }

    public void setProdNum(String prodNum)throws IllegalArgumentException{
        if(prodNum.length() <= 25 && prodNum.length() > 0) {
            this.prodNum.set(prodNum);
        } else{
            throw new IllegalArgumentException
                    ("Product Template Number must not be empty and must not be greater than 25 characters");
        }
    }

    @Override
    public void setQuantity(int quantityValue) throws IllegalArgumentException {
        if(quantityValue >= 0) {
            this.quantity.set(quantityValue);
        } else {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
    }

    public String getProdNum(){return this.prodNum.get();}

    @Override
    public String getDropDownSelection() {
        return null;
    }

    @Override
    public String getPartName() {
        return null;
    }

    @Override
    public String getVendor() {
        return null;
    }

    @Override
    public String getExPartNum() {
        return null;
    }

    @Override
    public int getQuantity() {
        return this.quantity.get();
    }
}
