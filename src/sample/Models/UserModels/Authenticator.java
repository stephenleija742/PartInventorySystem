package sample.Models.UserModels;

import sample.SQLGateways.UserTableGateway;

import java.sql.SQLException;

public abstract class Authenticator {

    public Session authenticateSession(String userName, String email) throws SQLException{
        UserTableGateway userTableGateway = UserTableGateway.getInstance();
        int role;
        Session session;
        role = userTableGateway.findRecord(userName, email);
        session = createSession(userName, email, role);
        session.setUsername(userName);
        session.setEmail(email);
        session.setRole(role);

        if(session == null){
            System.out.println("it's null");
        } else{
            System.out.println("it's not null");
        }
        return session;
    }

    protected abstract Session createSession(String userName, String email, int role);
}
