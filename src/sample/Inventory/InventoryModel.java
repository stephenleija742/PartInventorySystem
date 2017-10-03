package sample.Inventory;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Stephen on 8/30/2017.
 */
public class InventoryModel {

    private SimpleIntegerProperty id;
    private SimpleStringProperty part;
    private SimpleStringProperty location;
    private SimpleIntegerProperty quantity;

    public InventoryModel(){
        this.id = new SimpleIntegerProperty();
        this.part = new SimpleStringProperty();
        this.location = new SimpleStringProperty();
        this.quantity = new SimpleIntegerProperty();
    }

    public SimpleIntegerProperty idProperty(){return this.id;}

    public SimpleStringProperty partProperty(){return this.part;}

    public SimpleStringProperty locationProperty(){return this.location;}

    public SimpleIntegerProperty quantityProperty(){return this.quantity;}

    final void setID(int idValue){
        this.id.set(idValue);
    }

    final void setPart(String partValue) throws IllegalArgumentException{
        //int tempPart = Integer.parseInt(partValue);
        this.part.set(partValue);
    }

    /*final void setPart(int partValue){
        this.part.set(partValue);
    }*/

    final void setLocation(String locationValue) throws IllegalArgumentException{
        if(locationValue.equalsIgnoreCase("Unknown")){
            throw new IllegalArgumentException("Location cannot be set as Unknown");
        } else{
            this.location.set(locationValue);
        }
    }

    final void setQuantity(String quantityValue) throws IllegalArgumentException{
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

    final void setQuantity(int quantityValue) throws IllegalArgumentException{
        if(quantityValue >= 0) {
            this.quantity.set(quantityValue);
        } else {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
    }

    final int getID(){return this.id.get();}

    final String getPart(){return this.part.get();}

    final String getLocation(){return this.location.get();}

    final int getQuantity(){return this.quantity.get();}
}
