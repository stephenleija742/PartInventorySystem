package sample.Inventory;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.sun.rowset.CachedRowSetImpl;
import sample.CabinetronGateway;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;

/**
 * Created by Stephen on 9/3/2017.
 */
class InventoryTableGateway implements CabinetronGateway{

    private MysqlDataSource ds;
    private static final String allInventory = "SELECT * FROM inventory";
    private static final String inventoryInsertStr =
            "INSERT INTO inventory (Part, Location, Quantity) " +
            "SELECT PartNum, ?, ? from mydatabase.part where PartNum = ?";
    private static final String inventoryEditStr =
            "UPDATE inventory SET Part = ?, Location = ?, Quantity = ? " +
            "WHERE Inventory_ID = ?";
    private static final String inventoryDeleteStr = "DELETE FROM inventory where Inventory_ID = ?";

    InventoryTableGateway(){
        ds = new MysqlDataSource();
        ds.setURL("jdbc:mysql://localhost:8081/mydatabase");
        ds.setUser("root");
        ds.setPassword("root");
    }

    @Override
    public CachedRowSet findAllRecords() throws SQLException {
        CachedRowSet rowset = new CachedRowSetImpl();
        try (Connection conn = ds.getConnection();
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
        try (Connection conn = ds.getConnection();
            PreparedStatement preparedStatement = createPreparedStatement(conn, recordDetail);
            ResultSet rs = preparedStatement.getGeneratedKeys()){

            //insertSuccess = preparedStatement.executeUpdate();
            //rs = preparedStatement.getGeneratedKeys();
            if(rs.next()) { // to get resultset
                dbID = rs.getInt(1);
            }
        } catch (SQLException se){
            se.printStackTrace();
            se.getMessage();
        }
        return dbID;
    }

    // take out 2nd parameter
    @Override
    public void updateRecord(String[] inventoryDetails, int indexToUpdate) throws SQLException {
        try(Connection conn = ds.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(inventoryEditStr)){
            preparedStatement.setString(1, inventoryDetails[0]); //part
            preparedStatement.setString(2, inventoryDetails[1]); //location
            preparedStatement.setInt(3, Integer.parseInt(inventoryDetails[2])); //quantity
            preparedStatement.setInt(4, Integer.parseInt(inventoryDetails[3])); //id
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteRecord(int idToDelete) throws SQLException {
        try (Connection conn = ds.getConnection();
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
