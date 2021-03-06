package sample.Models.DetailModels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


/**
 * Created by Stephen on 7/5/2017.
 */
 public class PartModel extends IdentfierInterface implements ItemModel{

    private SimpleStringProperty dropDownSelection;
    private SimpleStringProperty partName;
    private SimpleStringProperty vendor;
    private SimpleStringProperty exPartNum;

    public PartModel(){
        this.id = new SimpleIntegerProperty();
        this.partNum = new SimpleStringProperty();
        this.partName = new SimpleStringProperty();
        this.vendor = new SimpleStringProperty();
        this.exPartNum = new SimpleStringProperty();
        this.dropDownSelection = new SimpleStringProperty();
    }
    //used by observer in table view to populate rows
    @Override
    public SimpleStringProperty dropDownSelectionProperty(){return this.dropDownSelection;}
    @SuppressWarnings("unused")
    public SimpleStringProperty partNameProperty(){return partName;}
    @SuppressWarnings("unused")
    public SimpleStringProperty vendorProperty(){return vendor;}
    @SuppressWarnings("unused")
    public SimpleStringProperty exPartNumProperty(){return exPartNum;}

    public final void setPartName(String partName) throws IllegalArgumentException{
        if(partName.length() <= 255 && partName.length() > 0) {
            this.partName.set(partName);
        } else{
            throw new IllegalArgumentException
                    ("Part Name must not be empty and must not be greater than 255 characters");
        }
    }

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
    public void setQuantity(String quantityValue) throws IllegalArgumentException {}

    @Override
    public void setQuantity(int quantityValue) throws IllegalArgumentException {}

    public final void setVendor(String vendor) throws IllegalArgumentException {
        if(vendor.equals("")){
            this.vendor.set(vendor);
            return;
        }
        if(vendor.length() <= 255 && vendor.length() > 0) {
            this.vendor.set(vendor);
        } else{
            throw new IllegalArgumentException("Invalid Vendor");
        }
    }

    public final void setExPartNum(String exPartNum) throws IllegalArgumentException{
        if(exPartNum.equals("")){
            this.exPartNum.set(exPartNum);
            return;
        }
        if(exPartNum.length() <= 50 && exPartNum.length() > 0) {
            this.exPartNum.set(exPartNum);
        } else{
            throw new IllegalArgumentException("Invalid External Part Number");
        }
    }

    public final String getPartName(){return this.partName.get();}

    @Override
    public final String getDropDownSelection(){return dropDownSelection.get();}

    public final String getVendor() {
        return vendor.get();
    }

    public final String getExPartNum(){
        return exPartNum.get();
    }

    //not to be used - maybe throw exception
    @Override
    public int getQuantity() {return 0;}

}
