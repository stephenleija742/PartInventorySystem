package sample.Parts;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


/**
 * Created by Stephen on 7/5/2017.
 */
public class PartModel {
    private SimpleIntegerProperty id;
    private SimpleStringProperty partNum;
    private SimpleStringProperty partName;
    private SimpleStringProperty vendor;
    private SimpleStringProperty exPartNum;
    private SimpleStringProperty unitOfQuantity;

    PartModel(){
        this.id = new SimpleIntegerProperty();
        this.partNum = new SimpleStringProperty();
        this.partName = new SimpleStringProperty();
        this.vendor = new SimpleStringProperty();
        this.exPartNum = new SimpleStringProperty();
        this.unitOfQuantity = new SimpleStringProperty();
    }
    //used by observer in table view to populate rows
    @SuppressWarnings("unused")
    public SimpleIntegerProperty idProperty(){return id;
    }

    @SuppressWarnings("unused")
    public SimpleStringProperty partNumProperty(){return partNum;}

    @SuppressWarnings("unused")
    public SimpleStringProperty partNameProperty(){return partName;}

    @SuppressWarnings("unused")
    public SimpleStringProperty vendorProperty(){return vendor;}

    @SuppressWarnings("unused")
    public SimpleStringProperty exPartNumProperty(){return exPartNum;}

    @SuppressWarnings("unused")
    public SimpleStringProperty unitOfQuantityProperty(){return unitOfQuantity;}

    final void setId(int idValue) throws IllegalArgumentException{
        this.id.set(idValue);
    }

    final void setPartNum(String partNum) throws IllegalArgumentException{
        if(partNum.length() <= 20 && partNum.length() > 0) {
            this.partNum.set(partNum);
        } else {
            throw new IllegalArgumentException
                    ("Part Number must not be empty and must not be greater than 20 characters");
        }
    }

    final void setPartName(String partName) throws IllegalArgumentException{
        if(partName.length() <= 255 && partName.length() > 0) {
            this.partName.set(partName);
        } else{
            throw new IllegalArgumentException
                    ("Part Name must not be empty and must not be greater than 255 characters");
        }
    }

    final void setVendor(String vendor) throws IllegalArgumentException {
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

    final void setExPartNum(String exPartNum) throws IllegalArgumentException{
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

    final void setUnitOfQuantity(String unitOfQuantityValue) throws IllegalArgumentException, NullPointerException {
       if(unitOfQuantityValue == null) {
           throw new NullPointerException("Unit Of Quantity must be chosen");
       }
       if(unitOfQuantityValue.equalsIgnoreCase("Unknown")){
           throw new IllegalArgumentException("Unit of Quantity cannot be Unknown");
       } else {
           this.unitOfQuantity.set(unitOfQuantityValue);
       }
    }

    final int getID() {
        return id.get();
    }

    final String getPartNum() {
        return partNum.get();
    }

    final String getPartName() {
        return partName.get();
    }

    final String getVendor() {
        return vendor.get();
    }

    final String getExPartNum(){
        return exPartNum.get();
    }

    final String getUnitOfQuantity(){
        return unitOfQuantity.get();
    }
}
