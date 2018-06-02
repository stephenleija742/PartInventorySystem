package sample.Models;

import sample.Models.UserModels.*;

public class CabinetronAuthenticator extends Authenticator {
    @Override
    protected Session createSession(String username, String email, int role) {
        switch (role) {
            case 3:
                return new AdminSession();
            case 2:
                return new ProductManagerSession(username, email, role);
            case 1:
                return new InventoryManagerSession(username, email, role);
        }
        return null;
    }
}
