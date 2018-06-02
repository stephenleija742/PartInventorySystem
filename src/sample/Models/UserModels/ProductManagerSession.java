package sample.Models.UserModels;

public class ProductManagerSession extends Session {

    public ProductManagerSession(String userName, String email, int role){
        //super(userName, email, role);
        canViewProductTemplates = true;
        canAddProductTemplates = true;
        canDeleteProductTemplates = true;
        canCreateProducts = true;
        canViewInventory = true;
        canAddInventory  = false;
        canViewParts = true;
        canAddParts = false;
        canDeleteParts = false;
        canDeleteInventory = false;
    }

}
