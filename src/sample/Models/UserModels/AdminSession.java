package sample.Models.UserModels;

public class AdminSession extends Session {

    private static AdminSession uniqueInstance;

    public static AdminSession getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new AdminSession();
        }
        return uniqueInstance;
    }

    public AdminSession(){
        //super(userName, email, role);
        canViewProductTemplates = true;
        canAddProductTemplates = true;
        canDeleteProductTemplates = true;
        canCreateProducts = true;
        canViewInventory = true;
        canAddInventory  = true;
        canViewParts = true;
        canAddParts = true;
        canDeleteParts = true;
        canDeleteInventory = true;
    }



}
