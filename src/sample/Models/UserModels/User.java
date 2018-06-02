package sample.Models.UserModels;

public class User {

    private String fullName;
    private String email;
    private String role = null;

    public User(String fullName, String email){
        this.fullName = fullName;
        this.email = email;
    }

    public String getFullName(){
        return this.fullName;
    }

    public String getEmail(){
        return this.email;
    }

    public String getRole() { return this.role; }

    public void setFullName(String fullName){ this.fullName = fullName; }

    public void setEmail(String email){ this.email = email; }

    public void setRole(String role){ this.role = role; }

}
