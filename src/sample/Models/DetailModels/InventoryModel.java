package sample.Models.DetailModels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Stephen on 8/30/2017.
 */
public class InventoryModel extends IdentfierInterface implements ItemModel {

    private SimpleStringProperty dropDownSelection;
    private SimpleIntegerProperty quantity;

    public InventoryModel(){
        this.id = new SimpleIntegerProperty();
        this.partNum = new SimpleStringProperty();
        this.dropDownSelection = new SimpleStringProperty();
        this.quantity = new SimpleIntegerProperty();
    }

   /* @Override
    public int getID() {
        return 0;
    }

    @Override
    public String getPartNum() {
        return null;
    }*/

    @Override
    public SimpleStringProperty dropDownSelectionProperty(){return this.dropDownSelection;}

    public SimpleIntegerProperty quantityProperty(){return this.quantity;}

    @Override
    public void setPartName(String partName) throws IllegalArgumentException {}

    @Override
    public void setVendor(String vendor) throws IllegalArgumentException {}

    @Override
    public void setExPartNum(String exPartNum) throws IllegalArgumentException {}

    @Override
    public final void setDropDownSelection(String selectionValue) throws IllegalArgumentException{
        if(selectionValue == null) {
            throw new NullPointerException("Please choose an option from dropdown");
        }
        if(selectionValue.equalsIgnoreCase("Unknown")){
            throw new IllegalArgumentException("Selection cannot be unknown");
        } else{
            this.dropDownSelection.set(selectionValue);
        }
    }

    @Override
    public final void setQuantity(String quantityValue) throws IllegalArgumentException{
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

    @Override
    public final void setQuantity(int quantityValue) throws IllegalArgumentException{
        if(quantityValue >= 0) {
            this.quantity.set(quantityValue);
        } else {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
    }

    @Override
    public final String getDropDownSelection(){return this.dropDownSelection.get();}

    @Override
    public String getPartName() {return null;}

    @Override
    public String getVendor() {return null;}

    @Override
    public String getExPartNum() {return null;}

    @Override
    public final int getQuantity(){return this.quantity.get();}
}
