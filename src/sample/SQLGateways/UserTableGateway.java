package sample.SQLGateways;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;

public class UserTableGateway implements CabinetronGateway{

    private static UserTableGateway uniqueInstance;
    private ComboPooledDataSource uds;

    private static final String findRole = "SELECT * FROM user WHERE FullName = ? and Email = ?";

    public static UserTableGateway getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new UserTableGateway();
        }
        return uniqueInstance;
    }

    private UserTableGateway(){
        uds = new ComboPooledDataSource();
        uds.setJdbcUrl("jdbc:mysql://localhost:8081/mydatabase?verifyServerCertificate=false&useSSL=true");
        uds.setUser("root");
        uds.setPassword("root");
    }

    public int findRecord(String userName, String email) throws SQLException{
        ResultSet rs;
        int role;
        try(Connection conn = uds.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(findRole)){
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, email);
            rs = preparedStatement.executeQuery();
            if (rs.next()){
                role = rs.getInt("Role");
            } else {
                throw new SQLException("No Row Found");
            }
        }
        return role;
    }

    @Override
    public CachedRowSet findAllRecords() throws SQLException {
        return null;
    }

    @Override
    public int insertRecord(String[] recordDetail) throws SQLException {
        return 0;
    }

    @Override
    public int updateRecord(String[] recordDetail, Timestamp lastModified) throws SQLException {
        return 0;
    }

    @Override
    public void updateRecord(String[] recordDetail) throws SQLException {

    }

    @Override
    public void deleteRecord(int idToDelete) throws SQLException {

    }

    @Override
    public Timestamp getDateTime(int id) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection conn, String[] recordDetails) throws SQLException {
        return null;
    }
}
