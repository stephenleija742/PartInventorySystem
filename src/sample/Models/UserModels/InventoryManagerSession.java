package sample.Models.UserModels;

public class InventoryManagerSession extends Session{

    public InventoryManagerSession(String userName, String email, int role){
        //super(userName, email, role);
        canViewProductTemplates = false;
        canAddProductTemplates = false;
        canDeleteProductTemplates = false;
        canCreateProducts = false;
        canViewInventory = true;
        canAddInventory  = true;
        canViewParts = true;
        canAddParts = true;
        canDeleteParts = false;
        canDeleteInventory = false;
    }


}
