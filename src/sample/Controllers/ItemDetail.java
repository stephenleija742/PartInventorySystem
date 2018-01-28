package sample.Controllers;

import javafx.scene.control.Button;

/**
 * Created by Stephen on 11/27/2017.
 */
public abstract class ItemDetail {
    public void setStateAndActions(Button addBtn, Button editBtn, String action){
        addBtn.setOnAction(event -> initDialogParameters("Add " + action));
        editBtn.setOnAction(event -> initDialogParameters("Edit " + action));
    }

    protected abstract void initDialogParameters(String dialogAction);
}
