package sample.Models.UserModels;

public abstract class Session {

    //private static Session uniqueInstance;
    boolean canViewProductTemplates = false;
    boolean canAddProductTemplates = false;
    boolean canDeleteProductTemplates = false;
    boolean canCreateProducts = false;
    boolean canViewInventory = false;
    boolean canAddInventory  = false;
    boolean canViewParts = false;
    boolean canAddParts = false;
    boolean canDeleteParts = false;
    boolean canDeleteInventory = false;
    String username;
    String email;
    int role;

    public boolean isCanViewProductTemplates(){ return canViewProductTemplates; }

    public boolean isCanAddProductTemplates(){ return canAddProductTemplates; }

    public boolean isCanDeleteProductTemplates() { return canDeleteProductTemplates; }

    public boolean isCanCreateProducts() { return canCreateProducts; }

    public boolean isCanViewInventory() { return canViewInventory; }

    public boolean isCanAddInventory() { return canAddInventory; }

    public boolean isCanViewParts() { return canViewParts; }

    public boolean isCanAddParts() { return canAddParts; }

    public boolean isCanDeleteParts() { return canDeleteParts; }

    public boolean isCanDeleteInventory() { return canDeleteInventory; }

    public void setCanViewProductTemplates(boolean canViewProductTemplates){
        this.canViewProductTemplates = canViewProductTemplates;
    }

    public void setCanAddProductTemplates(boolean canAddProductTemplates){
        this.canAddProductTemplates = canAddProductTemplates;
    }

    public void setCanDeleteProductTemplates(boolean canDeleteProductTemplates){
        this.canDeleteProductTemplates = canDeleteProductTemplates;
    }

    public void setCanCreateProducts(boolean canCreateProducts){ this.canCreateProducts = canCreateProducts; }

    public void setCanViewInventory(boolean canViewInventory){ this.canViewInventory = canViewInventory; }

    public void setCanAddInventory(boolean canAddInventory){ this.canAddInventory = canAddInventory; }

    public void setCanViewParts(boolean canViewParts){ this.canViewParts = canViewParts; }

    public void setCanAddParts(boolean canAddParts){ this.canAddParts = canAddParts; }

    public void setCanDeleteParts(boolean canDeleteParts){ this.canDeleteParts = canDeleteParts; }

    public void setCanDeleteInventory(boolean canDeleteInventory){ this.canDeleteInventory = canDeleteInventory; }

    public void setUsername(String username){ this.username = username; }

    public void setEmail(String email){ this.email = email; }

    public void setRole(int role){ this.role = role; }

    public String getUsername(){ return this.username; }

    public String getEmail(){ return this.email; }

    public int getRole(){ return this.role; }






}
