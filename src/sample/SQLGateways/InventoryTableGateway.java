package sample.SQLGateways;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.sun.rowset.CachedRowSetImpl;
import org.omg.CORBA.SystemException;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;
import java.util.Date;
import java.util.*;

/**
 * Created by Stephen on 9/3/2017.
 * TODO: Make class a singleton and use a connection pool
 */
public class InventoryTableGateway implements CabinetronGateway{

    private static InventoryTableGateway uniqueInstance;

    private ComboPooledDataSource pds;
    private MysqlDataSource ds;
    private static final String allInventory = "SELECT * FROM inventory";
    private static final String inventoryInsertStr =
            "INSERT INTO inventory (Part, Location, Quantity) " +
            "SELECT PartNum, ?, ? from mydatabase.part where PartNum = ?";
    private static final String inventoryEditStr =
            "UPDATE inventory SET Part = ?, Location = ?, Quantity = ? " +
            "WHERE Inventory_ID = ?";
    private static final String inventoryDeleteStr = "DELETE FROM inventory where Inventory_ID = ?";
    private static final String inventoryUpdateStr = "UPDATE inventory SET Part = ?, Location = ?, Quantity = ? " +
            "WHERE Inventory_ID = ? and last_modified = ?";
    private static final String findInventoryByID = "SELECT last_modified FROM inventory WHERE Inventory_ID = ?";
    private static final String findVersionByID = "SELECT * FROM inventory WHERE Inventory_ID = ?";

    public static InventoryTableGateway getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new InventoryTableGateway();
        }
        return uniqueInstance;
    }

    private InventoryTableGateway(){
        pds = new ComboPooledDataSource();
        pds.setJdbcUrl("jdbc:mysql://localhost:8081/mydatabase");
        pds.setUser("root");
        pds.setPassword("root");
    }

    @Override
    public CachedRowSet findAllRecords() throws SQLException {
        CachedRowSet rowset = new CachedRowSetImpl();
        try (Connection conn = pds.getConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(allInventory)){
            rowset.populate(rs);
        }
        return rowset;
    }

    @Override
    public int insertRecord(String[] recordDetail) throws SQLException {
        int dbID = 0;
        //int insertSuccess = 0;
        try (Connection conn = pds.getConnection();
            PreparedStatement preparedStatement = createPreparedStatement(conn, recordDetail);
            ResultSet rs = preparedStatement.getGeneratedKeys()){

            //insertSuccess = preparedStatement.executeUpdate();
            //rs = preparedStatement.getGeneratedKeys();
            if(rs.next()) { // to get resultset
                dbID = rs.getInt(1);
            }
        } /*catch (SQLException se){
            se.printStackTrace();
            se.getMessage();
        }*/
        return dbID;
    }

    @Override
    public Timestamp getDateTime(int id) throws SQLException{
        ResultSet rs;
        Timestamp lastModified;
        try(Connection conn = pds.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(findInventoryByID)){
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            if(rs.next()){
                lastModified = rs.getTimestamp("last_modified");
            } else{
                throw new SQLException("ID does not exist");
            }
        }
        return lastModified;
    }

    // take out 2nd parameter
    @Override
    public int updateRecord(String[] inventoryDetails, Timestamp lastModified) throws SQLException {
        int returnValue = 1;
        int quantity = Integer.parseInt(inventoryDetails[3]);
        int id = Integer.parseInt(inventoryDetails[0]);
        try(Connection conn = pds.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(inventoryUpdateStr)){
            //lastModified = getDateTime(Integer.parseInt(inventoryDetails[0]));
            preparedStatement.setString(1, inventoryDetails[1]); //part
            preparedStatement.setString(2, inventoryDetails[2]); //location
            preparedStatement.setInt(3, quantity); //quantity
            preparedStatement.setInt(4, id); //id
            preparedStatement.setTimestamp(5, lastModified);
            if((preparedStatement.executeUpdate()) == 0){
                concurrencyException(id, lastModified);
            }
        } catch (SQLException sqlEx){
            throw new SQLException("Unexpected error deleting inventory");
        }
        return returnValue;
    }

    //@Override
    private void concurrencyException(int id, Timestamp lastModifiedLocal) throws SQLException{
        ResultSet rs;
        try(Connection conn = pds.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(findVersionByID)){
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            if(rs.next()){
                String part = rs.getString("Part");
                String location = rs.getString("Location");
                Timestamp timestampVersionInDB = rs.getTimestamp("last_modified");
                if(timestampVersionInDB.after(lastModifiedLocal)){
                    throw new ConcurrentModificationException("Part: " + part + " at " + location + " was modified at "
                            + timestampVersionInDB);
                } else{
                    throw new RuntimeException("Error checking timestamp");
                }
            } else {
                throw new ConcurrentModificationException("Inventory does not exist in database");
            }
        }
    }

    @Override
    public void updateRecord(String[] recordDetail) throws SQLException {
        try(Connection conn = pds.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(inventoryEditStr)){
            preparedStatement.setString(1, recordDetail[1]); //part
            preparedStatement.setString(2, recordDetail[2]); //location
            preparedStatement.setInt(3, Integer.parseInt(recordDetail[3])); //quantity
            preparedStatement.setInt(4, Integer.parseInt(recordDetail[0])); //id
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteRecord(int idToDelete) throws SQLException {
        try (Connection conn = pds.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(inventoryDeleteStr)){
            preparedStatement.setInt(1, idToDelete);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection conn, String[] inventoryDetails) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(inventoryInsertStr,
                Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, (inventoryDetails[1]));  //location
        preparedStatement.setInt(2, Integer.parseInt(inventoryDetails[2])); //quantity
        preparedStatement.setString(3, inventoryDetails[0]); //part
        preparedStatement.executeUpdate();
        return preparedStatement;
    }
}
