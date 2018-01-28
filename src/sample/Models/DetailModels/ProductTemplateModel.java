package sample.Models.DetailModels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import sample.Models.DetailModels.IdentfierInterface;
import sample.Models.DetailModels.ItemModel;

/**
 * Created by Stephen on 12/11/2017.
 */
public class ProductTemplateModel extends IdentfierInterface implements ItemModel{
    private SimpleStringProperty prodDescription;
    private SimpleStringProperty prodNum;

    public ProductTemplateModel(){
        this.id = new SimpleIntegerProperty();
        this.prodNum = new SimpleStringProperty();
        this.prodDescription = new SimpleStringProperty();
    }

    public SimpleStringProperty prodDescriptionProperty(){return this.prodDescription;}

    public SimpleStringProperty prodNumProperty(){return this.prodNum;}

    public void setProdDescription(String description) throws IllegalArgumentException, NullPointerException{
        if(description == null){
            throw new NullPointerException("No Description exists");
        } else if (description.charAt(0) != 'A'){
            throw new IllegalArgumentException("First letter of description must begin with A");
        } else if(description.length() > 255){
            throw new IllegalArgumentException("Description must not be more than 255 characters");
        }
        this.prodDescription.set(description);

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
    public SimpleStringProperty dropDownSelectionProperty() {
        return null;
    }

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

    }

    @Override
    public void setQuantity(int quantityValue) throws IllegalArgumentException {

    }

    public String getProdDescription(){return this.prodDescription.get();}

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
        return 0;
    }
}
