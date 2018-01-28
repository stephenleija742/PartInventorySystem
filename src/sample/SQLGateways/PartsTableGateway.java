package sample.SQLGateways;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;

public class PartsTableGateway implements CabinetronGateway{
    private ComboPooledDataSource ds;
    private static PartsTableGateway uniqueInstance;
    private static final String allParts = "SELECT * FROM part";
    private static final String tableInsertStr =
            "INSERT INTO part (PartNum,PartName,Vendor,UnitofQuantity,ExternalPartNum) VALUES (?,?,?,?,?)";
    private static final String tableEditStr = "UPDATE part " +
            "SET PartNum = ?, PartName = ?, Vendor = ?, UnitofQuantity = ?, ExternalPartNum = ?" +
            " WHERE Part_ID = ?";
    private static final String delPartStr = "DELETE FROM part WHERE Part_ID=?";
    private static final String findDupPartNum = "SELECT PartNum FROM part" +
                                                 "WHERE Part_ID != ? and PartNum = ?";
    //private final MysqlDataSource ds;

    public static PartsTableGateway getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new PartsTableGateway();
        }
        return uniqueInstance;
    }

    private PartsTableGateway() {
        ds = new ComboPooledDataSource();
        ds.setJdbcUrl("jdbc:mysql://localhost:8081/mydatabase");
        ds.setUser("root");
        ds.setPassword("root");
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection conn, String[] partDetails) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(tableInsertStr, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, partDetails[0]);  //partnum
        preparedStatement.setString(2, partDetails[1]); //partname
        preparedStatement.setString(3, partDetails[2]); //vendor
        preparedStatement.setString(4, partDetails[3]); //unitofquantity
        preparedStatement.setString(5, partDetails[4]); //expartnum
        preparedStatement.executeUpdate();
        return preparedStatement;
    }

    @Override
    public CachedRowSet findAllRecords() throws SQLException {
        CachedRowSet rowSet = new CachedRowSetImpl();
        try (Connection conn = ds.getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(allParts)) {
            rowSet.populate(rs);
        } /*catch (SQLException se) {
            se.printStackTrace();
            se.getMessage();
        }*/
        return rowSet;
    }

    @Override
    public int insertRecord(String[] partDetails) throws SQLException {
        int dbID = 0;
        try (Connection conn = ds.getConnection();
             PreparedStatement preparedStatement = createPreparedStatement(conn, partDetails);
             ResultSet rs = preparedStatement.getGeneratedKeys()) {
            if (rs.next()) { // to get resultset
                dbID = rs.getInt(1);
            }
        }
        return dbID;
    }

    @Override
    public void updateRecord(String[] recordDetail, int indexToUpdate) throws SQLException {

    }

    @Override
    public void updateRecord(String[] partDetails) throws SQLException {
        try (Connection conn = ds.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(tableEditStr)) {
            preparedStatement.setString(1, partDetails[1]);
            preparedStatement.setString(2, partDetails[2]);
            preparedStatement.setString(3, partDetails[3]);
            preparedStatement.setString(4, partDetails[4]);
            preparedStatement.setString(5, partDetails[5]);
            preparedStatement.setInt(6, Integer.parseInt(partDetails[0]));
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteRecord(int idToDelete) throws SQLException {
        try (Connection conn = ds.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(delPartStr)) {
            preparedStatement.setInt(1, idToDelete);
            preparedStatement.executeUpdate();
        }
    }

    public void findByPartNum(String[] partNum) throws SQLException{
        try (Connection conn = ds.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(delPartStr)) {
            preparedStatement.setString(1, partNum[0]);
            preparedStatement.setInt(2, Integer.parseInt(partNum[1]));
            preparedStatement.executeUpdate();
        }
    }
}
